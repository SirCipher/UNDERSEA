package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionData;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.exception.ServiceNotRegisteredException;
import com.type2labs.undersea.utilities.executor.ScheduledThrowableExecutor;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.ThreadSafe;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BooleanSupplier;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 * <p>
 * TODO: AgentServices should have a prerequisites method that starts all services before the service itself and
 * checks for circular dependencies
 */
@ThreadSafe
public class ServiceManager {

    /**
     * The default time (in milliseconds) that the service manager should wait for a service to start before assuming
     * that something has gone wrong. {@link AgentService}s can override {@link AgentService#transitionTimeout()} if
     * more time is required.
     */
    static final long DEFAULT_TRANSITION_TIMEOUT = 2000;
    private static final Logger logger = LogManager.getLogger(ServiceManager.class);

    private final Map<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> services =
            new ConcurrentHashMap<>();
    private final Map<Class<? extends AgentService>, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private final Map<Class<? extends AgentService>, ServiceState> serviceStates = new ConcurrentHashMap<>();

    private ScheduledExecutorService serviceExecutor;
    private ScheduledExecutorService scheduledExecutor;
    private ThrowableExecutor serviceInitialiser = ThrowableExecutor.newSingleThreadExecutor(logger);
    private Agent agent;

    private volatile boolean started = false;
    private volatile boolean starting = false;
    private ServiceManagerTransactionService serviceManagerTransactionService;

    public ServiceManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownServices));
        scheduledExecutor = ScheduledThrowableExecutor.newSingleThreadExecutor(logger);
    }

    public boolean isStarting() {
        return starting;
    }

    private void transitionService(Class<? extends AgentService> service, ServiceState status) {
        serviceStates.put(service, status);

        if (status == ServiceState.FAILED) {
            notifyServicesOfFailure(service);
        }
    }

    /**
     * Sends a {@link Transaction} to all {@link AgentService}s to notify them that a service has transitioned to
     * {@link ServiceState#FAILED}.
     * <p>
     * {@link AgentService}s should always internally handle errors in case of an error. If an {@link AgentService}
     * encounters an error, then the error is caught by the {@link ServiceManager} and this method is invoked and all
     * registered and running {@link AgentService}s are notified that a particular service has failed, ensuring that
     * other services can perform the required operations before the system possibly exits.
     *
     * @param service that has failed
     */
    private void notifyServicesOfFailure(Class<? extends AgentService> service) {
        // This does not currently take care of notifying the target services as to why/how the service failed
        Transaction transaction = new Transaction.Builder(agent)
                .forAllRunningServices()
                .withStatus(LifecycleEvent.SERVICE_FAILED)
                .withPrimaryData(TransactionData.from(service))
                .usingExecutorService(MoreExecutors.newDirectExecutorService()).forAllServices()
                .invokedBy(serviceManagerTransactionService)
                .build();

        Set<ListenableFuture<?>> futures = commitTransaction(transaction);
        futures.forEach(e -> {
            try {
                e.get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            } catch (NotSupportedException ignored) {
            }
        });
    }

    /**
     * Waits for an {@link AgentService} to transition from it's current state to the supplier's new state. If the
     * {@link AgentService#transitionTimeout()} if exceeded, then the {@link AgentService} is transitioned to
     * {@link ServiceState#FAILED} and all registered services are notified of the failure.
     * <p>
     *
     * @param supplier      to poll on
     * @param service       to wait to transition
     * @param serviceFuture associated with the {@link AgentService}
     * @param starting      {@link ServiceState}
     * @param successful    {@link ServiceState} to set if the service transitions successfully
     * @return true if the service transitioned successfully. False only if the thread throws an exception
     */
    private synchronized boolean _waitForTransition(BooleanSupplier supplier, AgentService service,
                                                    ScheduledFuture<?> serviceFuture, ServiceState starting,
                                                    ServiceState successful) {
        long startupTimeout = service.transitionTimeout();
        transitionService(service.getClass(), starting);

        Future<Boolean> future = serviceInitialiser.submit(() -> {
            long start = System.currentTimeMillis();

            while (!supplier.getAsBoolean()) {
                if (System.currentTimeMillis() - start > startupTimeout) {
                    transitionService(service.getClass(), ServiceState.FAILED);
                    serviceFuture.cancel(true);

                    String message = String.format(agent.name() + ": service "
                            + service.getClass().getSimpleName() + " did not transition in the allocated time" +
                            " %s ms", startupTimeout) + ". Attempted to go from " + starting + " to " + successful;
                    logger.error(message, agent);

                    if (service.isCritical()) {
                        throw new ServiceManagerException(message);
                    }
                }
            }

            logger.info("Agent " + agent.name() + " started service: " + service.getClass().getSimpleName(),
                    agent);

            transitionService(service.getClass(), successful);

            return true;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public Set<ListenableFuture<?>> commitTransaction(Transaction transaction) {
        Collection<Class<? extends AgentService>> destinationServices = transaction.getDestinationServices();
        Set<ListenableFuture<?>> futures = new HashSet<>(destinationServices.size());

        for (Class<? extends AgentService> service : destinationServices) {
            AgentService _registeredService = checkServiceAndThrow(service);
            ListenableFuture<?> future = _registeredService.executeTransaction(transaction);

            futures.add(future);
        }

        return Collections.unmodifiableSet(futures);
    }

    /**
     * Retrieves a services and checks if it is registered. If it isn't, then a {@link NullPointerException} is thrown
     *
     * @param service to fetch
     * @return the instance
     */
    private AgentService checkServiceAndThrow(Class<? extends AgentService> service) {
        return Objects.requireNonNull(getService(service), "Service not registered: " + service.getSimpleName());
    }

    private ScheduledFuture<?> getScheduledFuture(Class<? extends AgentService> service) {
        for (Map.Entry<Class<? extends AgentService>, ScheduledFuture<?>> e : scheduledFutures.entrySet()) {
            if (service.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        return null;
    }

    public <T extends AgentService> T getService(Class<T> s, boolean required) {
        AgentService agentService = getService(s);

        if (required && agentService == null) {
            throw new NullPointerException("Required service " + s.getSimpleName() + " is missing");
        }

        return (T) agentService;
    }

    public <T extends AgentService> T getService(Class<T> s) {
        Objects.requireNonNull(s);

        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                services.entrySet()) {
            if (s.isAssignableFrom(e.getKey())) {
                return (T) e.getValue().getKey();
            }
        }

        return null;
    }

    public Collection<Class<? extends AgentService>> getServiceClasses() {
        return services.keySet();
    }

    /**
     * Returns all {@link AgentService} that are in a {@link ServiceState#RUNNING} state
     *
     * @return a filtered collection
     */
    public Collection<Class<? extends AgentService>> getRunningServiceClasses() {
        return services.keySet();
    }

    public synchronized Collection<AgentService> getServices() {
        List<AgentService> s = new ArrayList<>(services.size());

        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                services.entrySet()) {
            s.add(e.getValue().getKey());
        }

        return s;
    }

    /**
     * Sorts the registered services by {@link ServiceExecutionPriority} descending
     *
     * @return the sorted list
     */
    private synchronized List<Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>>> prioritySorted() {
        List<Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>>> sorted =
                new LinkedList<>(services.entrySet());
        sorted.sort(Comparator.comparingInt(o -> o.getValue().getValue().priority));
        Collections.reverse(sorted);

        return sorted;
    }

    public synchronized void registerService(AgentService service) {
        if (services.containsKey(service.getClass())) {
            throw new IllegalArgumentException("Service already exists");
        }

        registerService(service, ServiceExecutionPriority.MEDIUM);
    }

    public synchronized void registerService(AgentService service, ServiceExecutionPriority priority) {
        if (services.containsKey(service.getClass())) {
            throw new IllegalArgumentException("Service already exists");
        }

        services.put(service.getClass(), Pair.of(service, priority));
        serviceStates.put(service.getClass(), ServiceState.STOPPED);
    }

    /**
     * The {@link ServiceManager} is healthy if all {@link AgentService}s are in a state of
     * {@link ServiceState#RUNNING}. I.e, no services are currently starting or have previously encountered an error.
     * <p>
     * If the service manager is unhealthy, then the global state of an {@link Agent} is now uncertain. Design of
     * {@link AgentService}s should have this taken in to consideration.
     *
     * @return if the {@link ServiceManager} is healthy
     */
    public boolean isHealthy() {
        return serviceStates.values().stream().filter(e -> e == ServiceState.RUNNING).count() == serviceStates.size();
    }

    public void registerServices(Set<AgentService> services) {
        services.forEach(this::registerService);
    }

    public synchronized boolean serviceRunning(Class<? extends AgentService> service) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture == null) {
            return false;
        } else {
            return !scheduledFuture.isDone();
        }
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
        this.serviceManagerTransactionService = new ServiceManagerTransactionService();
        serviceManagerTransactionService.initialise(agent);

        logger.info(agent.name() + ": service manager assigned", agent);
    }

    public synchronized void shutdownService(Class<? extends AgentService> service, AgentService agentService) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFutures.remove(service);
            serviceStates.put(service, ServiceState.STOPPED);
        }

        if (agentService != null) {
            agentService.shutdown();
        }

        logger.info(agent.name() + ": shutting down service: " + service.getSimpleName(), agent);
    }

    public void shutdownServices() {
        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                prioritySorted()) {
            shutdownService(e.getKey(), e.getValue().getKey());
        }

        if (serviceExecutor != null) {
            serviceExecutor.shutdownNow();
        }

        scheduledExecutor.shutdownNow();
        serviceInitialiser.shutdownNow();

        started = false;
    }

    /**
     * Starts a repeating service for a given period. Repeating services will not return for the
     * {@link ServiceManager#getService} method
     *
     * @param service to execute
     * @param period  the period between successive executions
     */
    public synchronized void startRepeatingService(AgentService service, long period) {
        ScheduledFuture<?> scheduledFuture = scheduledExecutor.scheduleAtFixedRate(wrapAgentService(service), 1, period,
                TimeUnit.MILLISECONDS);
        scheduledFutures.put(service.getClass(), scheduledFuture);

        logger.info(agent.name() + ": started repeating service: " + service.getClass().getSimpleName() + " at " +
                "period: " + period, agent);
    }

    private synchronized void startService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        agentService.initialise(agent);

        logger.info(agent.name() + ": starting service: " + agentService.getClass().getSimpleName(), agent);

        ScheduledFuture<?> future = serviceExecutor.schedule(wrapAgentService(agentService), 1, TimeUnit.NANOSECONDS);

        //noinspection StatementWithEmptyBody
        while (!_waitForTransition(agentService::started, agentService, future, ServiceState.STARTING,
                ServiceState.RUNNING)) {
        }

        scheduledFutures.put(service, future);
    }

    /**
     * Wraps an {@link AgentService} such that any exception that is thrown by the service will be caught and the
     * service's {@link ServiceState} can be set to {@link ServiceState#FAILED}
     *
     * @param agentService to wrap
     * @return a wrapped {@link AgentService}
     */
    private Runnable wrapAgentService(final AgentService agentService) {
        return () -> {
            try {
                agentService.run();
            } catch (final Throwable t) {
                t.printStackTrace();
                transitionService(agentService.getClass(), ServiceState.FAILED);
                throw t;
            }
        };
    }

    public synchronized void startServices() {
        if (started) {
            throw new IllegalStateException("Service manager already started, cannot start again");
        }

        processRequiredServices();

        starting = true;

        serviceExecutor = ScheduledThrowableExecutor.newExecutor(services.size(), logger);

        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                prioritySorted()) {
            startService(e.getKey());
        }

        starting = false;
        started = true;

        updateVisualiser();
    }

    private synchronized void processRequiredServices() {
        Set<Class<? extends AgentService>> missingServices = new HashSet<>();

        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                services.entrySet()) {
            Collection<Class<? extends AgentService>> requirements = e.getValue().getKey().requiredServices();

            for (Class<? extends AgentService> required : requirements) {
                if (getService(required) == null) {
                    missingServices.add(required);
                }
            }
        }

        if (missingServices.size() > 0) {
            StringBuilder msg = new StringBuilder(agent.name() + ": " + missingServices.size() + " missing services." +
                    " Missing:\n");

            for (Class<? extends AgentService> clazz : missingServices) {
                msg.append(clazz.getSimpleName());
            }

            throw new ServiceNotRegisteredException(msg.toString());
        } else {
            logger.info(agent.name() + ": all required services are registered", agent);
        }
    }

    public void updateVisualiser() {
        Monitor monitor = getService(Monitor.class);

        if (monitor != null) {
            monitor.update();
        }
    }

    /**
     * Type defines a {@link AgentService}'s current status
     */
    public enum ServiceState {
        /**
         * Service is currently starting up
         */
        STARTING,

        /**
         * Service is up and running successfully
         */
        RUNNING,

        /**
         * Service has stopped successfully
         */
        STOPPED,

        /**
         * Service failed to start/stop successfully or abnormally terminated
         */
        FAILED,
    }

    /**
     * Specifies the order that services should start/stop in
     */
    public enum ServiceExecutionPriority {

        /**
         * Indicates that a service should start last
         */
        LOW(0),

        /**
         * Indicates that a service should start after high priority services but before low priority
         */
        MEDIUM(3),

        /**
         * Indicates that a service should start first
         */
        HIGH(5);

        private int priority;

        ServiceExecutionPriority(int priority) {
            this.priority = priority;
        }

    }

    private static class ServiceManagerException extends RuntimeException {
        private static final long serialVersionUID = -8357603298883501831L;

        ServiceManagerException(String s) {
            super(s);
        }
    }

    private static class ServiceManagerTransactionService implements AgentService {

        private Agent agent;

        @Override
        public void shutdown() {

        }

        @Override
        public void initialise(Agent parentAgent) {
            this.agent = parentAgent;
        }

        @Override
        public Agent parent() {
            return agent;
        }

        @Override
        public void run() {

        }
    }

}

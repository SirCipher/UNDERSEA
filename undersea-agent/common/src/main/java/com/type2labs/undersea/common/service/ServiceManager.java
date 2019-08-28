package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.executor.ScheduledThrowableExecutor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class ServiceManager {

    private static final Logger logger = LogManager.getLogger(ServiceManager.class);

    private final Map<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> services =
            new ConcurrentHashMap<>();
    private final Map<Class<? extends AgentService>, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    private ScheduledExecutorService serviceExecutor;
    private ScheduledExecutorService scheduledExecutor;
    private Agent agent;
    private boolean started = false;

    public ServiceManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownServices));

        scheduledExecutor = ScheduledThrowableExecutor.newSingleThreadExecutor(logger);
    }

    public Set<ListenableFuture<?>> commitTransaction(Transaction transaction) {
        Collection<Class<? extends AgentService>> destinationServices = transaction.getDestinationServices();
        Set<ListenableFuture<?>> futures = new HashSet<>(destinationServices.size());

        for (Class<? extends AgentService> service : destinationServices) {
            AgentService _registeredService = Objects.requireNonNull(getService(service),
                    "Service not registered: " + service.getSimpleName());
            ListenableFuture<?> future = _registeredService.executeTransaction(transaction);
            futures.add(future);
        }

        return Collections.unmodifiableSet(futures);
    }

    private ScheduledFuture<?> getScheduledFuture(Class<? extends AgentService> service) {
        for (Map.Entry<Class<? extends AgentService>, ScheduledFuture<?>> e : scheduledFutures.entrySet()) {
            if (service.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        return null;
    }

    public synchronized <T extends AgentService> T getService(Class<T> s) {
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
    }

    public void registerServices(Set<AgentService> services) {
        for (AgentService as : services) {
            registerService(as);
        }
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
        logger.info("Agent " + agent.name() + " service manager assigned", agent);
    }

    public synchronized void shutdownService(Class<? extends AgentService> service, AgentService key) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFutures.remove(service);
        }

        if (key != null) {
            key.shutdown();
        }

        logger.info("Agent: " + agent.name() + " shutting down service: " + service.getSimpleName(), agent);
    }

    public void shutdownServices() {
        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                prioritySorted()) {
            shutdownService(e.getKey(), e.getValue().getKey());
        }

        scheduledExecutor.shutdownNow();

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
        ScheduledFuture<?> scheduledFuture = scheduledExecutor.scheduleAtFixedRate(service, 1, period,
                TimeUnit.MILLISECONDS);
        scheduledFutures.put(service.getClass(), scheduledFuture);

        logger.info("Agent: " + agent.name() + ", started repeating service: " + service.getClass().getSimpleName() + " at period: " + period, agent);
    }

    private synchronized void startService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        agentService.initialise(agent);

        logger.info("Agent " + agent.name() + " starting service: " + agentService.getClass().getSimpleName(), agent);

        ScheduledFuture<?> future = serviceExecutor.schedule(agentService, 1, TimeUnit.MILLISECONDS);
        scheduledFutures.put(service, future);

        logger.info("Agent " + agent.name() + " started service: " + agentService.getClass().getSimpleName(), agent);
    }

    public synchronized void startServices() {
        if (started) {
            throw new IllegalStateException("Service manager already started, cannot start again");
        }

        serviceExecutor = ScheduledThrowableExecutor.newExecutor(services.size(), logger);

        for (Map.Entry<Class<? extends AgentService>, Pair<AgentService, ServiceExecutionPriority>> e :
                prioritySorted()) {
            startService(e.getKey());
        }

        started = true;
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

}

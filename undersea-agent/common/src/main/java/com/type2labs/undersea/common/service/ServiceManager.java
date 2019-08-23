package com.type2labs.undersea.common.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.utilities.executor.ScheduledThrowableExecutor;
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

    private final Map<Class<? extends AgentService>, AgentService> services = new ConcurrentHashMap<>();
    private final Map<Class<? extends AgentService>, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutor;

    private Agent agent;
    private boolean autoLogTransactions;

    public ServiceManager() {
        this.scheduledExecutor = ScheduledThrowableExecutor.newSingleThreadExecutor(logger);
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
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            if (s.isAssignableFrom(e.getKey())) {
                return (T) e.getValue();
            }
        }

        return null;
    }

    public Collection<Class<? extends AgentService>> getServiceClasses() {
        return services.keySet();
    }

    public synchronized Collection<AgentService> getServices() {
        return services.values();
    }

    public synchronized void registerService(AgentService service) {
        if (services.containsKey(service.getClass())) {
            throw new IllegalArgumentException("Service already exists");
        }

        services.put(service.getClass(), service);
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
        this.autoLogTransactions = agent.config().autoLogTransactions();
        logger.info("Agent " + agent.name() + " service manager assigned", agent);
    }

    public void shutdownService(Class<? extends AgentService> service) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFutures.remove(service);
        }
    }

    public void shutdownServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            shutdownService(e.getKey());
        }
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

    private void startService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        agentService.initialise(agent);

        logger.info("Agent " + agent.name() + " starting service: " + agentService.getClass().getSimpleName(), agent);

        ScheduledFuture<?> future = scheduledExecutor.schedule(agentService, 1, TimeUnit.MILLISECONDS);
        scheduledFutures.put(service, future);

        logger.info("Agent " + agent.name() + " started service: " + agentService.getClass().getSimpleName(), agent);
    }

    public void startServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            startService(e.getKey());
        }
    }
}

package com.type2labs.undersea.common.service;

import com.type2labs.undersea.common.agent.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class ServiceManager {

    private static final Logger logger = LogManager.getLogger(ServiceManager.class);

    private final Map<Class<? extends AgentService>, AgentService> services = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private final Map<Class<? extends AgentService>, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    private Agent agent;
    private boolean autoLogTransactions;

    public void setAgent(Agent agent) {
        this.agent = agent;
        this.autoLogTransactions = agent.config().autoLogTransactions();
        logger.info("Agent " + agent.name() + " service manager assigned", agent);
    }

    public synchronized AgentService getService(Class<? extends AgentService> s) {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            if (s.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        return null;
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

    Collection<Class<? extends AgentService>> getServiceClasses() {
        return services.keySet();
    }

    public Set<ScheduledFuture<?>> commitTransaction(Transaction transaction) {
        Collection<Class<? extends AgentService>> destinationServices = transaction.getDestinationServices();
        Set<ScheduledFuture<?>> futures = new HashSet<>(destinationServices.size());

        for (Class<? extends AgentService> service : destinationServices) {
            AgentService _registeredService = Objects.requireNonNull(getService(service),
                    "Service not registered: " + service.getSimpleName());
            ScheduledFuture<?> future = _registeredService.executeTransaction(transaction);
            futures.add(future);
        }

        return Collections.unmodifiableSet(futures);
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

    public void shutdownServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            shutdownService(e.getKey());
        }
    }

    public void startServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            startService(e.getKey());
        }
    }

    private ScheduledFuture<?> getScheduledFuture(Class<? extends AgentService> service) {
        for (Map.Entry<Class<? extends AgentService>, ScheduledFuture<?>> e : scheduledFutures.entrySet()) {
            if (service.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        return null;
    }

    public synchronized boolean serviceRunning(Class<? extends AgentService> service) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture == null) {
            return false;
        } else {
            return !scheduledFuture.isDone();
        }
    }

    private void startService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        logger.info("Agent " + agent.name() + " starting service: " + agentService.getClass().getSimpleName(), agent);

        ScheduledFuture<?> future = scheduledExecutor.schedule(agentService, 1, TimeUnit.MILLISECONDS);
        scheduledFutures.put(service, future);

        logger.info("Agent " + agent.name() + " started service: " + agentService.getClass().getSimpleName(), agent);
    }

    public void shutdownService(Class<? extends AgentService> service) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFutures.remove(service);
        }
    }

}

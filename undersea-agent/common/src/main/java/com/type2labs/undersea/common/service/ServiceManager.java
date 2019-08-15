package com.type2labs.undersea.common.service;

import com.type2labs.undersea.common.agent.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;
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

    public void setAgent(Agent agent) {
        this.agent = agent;
        logger.info("Agent " + agent.name() + " service manager assigned", agent);
    }

    public AgentService getService(Class<? extends AgentService> s) {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            if (s.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        throw new IllegalArgumentException(s.getName() + " is not registered");
    }

    public Collection<AgentService> getServices() {
        return services.values();
    }

    public void registerService(AgentService service) {
        if (services.containsKey(service.getClass())) {
            throw new IllegalArgumentException("Service already exists");
        }

        services.put(service.getClass(), service);
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

    public boolean serviceRunning(Class<? extends AgentService> service) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(service);

        if (scheduledFuture == null || !scheduledFuture.isDone()) {
            return true;
        } else {
            return !scheduledFuture.isDone();
        }
    }

    public void startService(Class<? extends AgentService> service) {
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
        }
    }

}

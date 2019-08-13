package com.type2labs.undersea.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class ServiceManager {

    private static final Logger logger = LogManager.getLogger(ServiceManager.class);
    private final Map<Class<? extends AgentService>, AgentService> services = new ConcurrentHashMap<>();

    public boolean available() {
        return services.entrySet().stream().filter(s -> s.getValue().isAvailable()).count() == services.size();
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

    public Map<Class<? extends AgentService>, AgentService> getServicesByClass() {
        return services;
    }

    public void registerService(AgentService service) {
        if (services.containsKey(service.getClass())) {
            throw new IllegalArgumentException("Service already exists");
        }

        services.put(service.getClass(), service);
    }

    public void shutdownServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            e.getValue().shutdown();
        }
    }

    public void startServices() {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            e.getValue().start();
        }
    }

    public void startService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        agentService.start();
    }

    public void shutdownService(Class<? extends AgentService> service) {
        AgentService agentService = getService(service);
        agentService.shutdown();
    }

}

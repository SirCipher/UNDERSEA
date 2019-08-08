package com.type2labs.undersea.models;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class AgentServices {

    private Map<Class<? extends AgentService>, AgentService> services = new ConcurrentHashMap<>();

    public boolean available() {
        return services.entrySet().stream().filter(s -> s.getValue().isAvailable()).count() == services.size();
    }

    public AgentService getService(Class<? extends AgentService> s) {
        for (Map.Entry<Class<? extends AgentService>, AgentService> e : services.entrySet()) {
            if (s.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }

        return null;
    }

    public Collection<AgentService> getServices() {
        return services.values();
    }

    public Map<Class<? extends AgentService>, AgentService> getServicesByClass() {
        return services;
    }

    public void registerService(AgentService service) {
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

}

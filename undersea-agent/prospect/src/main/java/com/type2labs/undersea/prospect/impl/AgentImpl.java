package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// TODO: Replace with com.type2labs.undersea.models.impl.AgentImpl
public class AgentImpl implements Agent {

    private static final long serialVersionUID = 5509688757665347200L;
    private final ServiceManager serviceManager;
    private double speed;
    private double remainingBattery;
    private double range;
    private double accuracy;
    private String name;

    public AgentImpl(String name, ServiceManager serviceManager) {
        this.name = name;
        this.serviceManager = serviceManager;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        speed = random.nextDouble(100);
        remainingBattery = random.nextDouble(100);
        range = random.nextDouble(100);
        accuracy = random.nextDouble(100);
    }

    public ServiceManager serviceManager() {
        return serviceManager;
    }

    @Override
    public ServiceManager services() {
        return serviceManager;
    }


    @Override
    public List<Pair<String, String>> status() {
        List<Pair<String, String>> status = new ArrayList<>();
        status.add(Pair.of("speed", Double.toString(speed)));
        status.add(Pair.of("remainingBattery", Double.toString(remainingBattery)));
        status.add(Pair.of("range", Double.toString(range)));
        status.add(Pair.of("accuracy", Double.toString(accuracy)));

        return status;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public void schedule(Runnable task) {

    }

    @Override
    public UnderseaRuntimeConfig config() {
        return null;
    }


    @Override
    public void shutdown() {

    }

    @Override
    public String toString() {
        return "AgentImpl{" +
                "speed=" + speed +
                ", remainingBattery=" + remainingBattery +
                ", range=" + range +
                ", accuracy=" + accuracy +
                '}';
    }
}

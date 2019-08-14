package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.*;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.networking.Endpoint;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.visualiser.NullVisualiser;
import com.type2labs.undersea.common.visualiser.VisualiserClient;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// TODO: Replace with com.type2labs.undersea.models.impl.AgentImpl
public class AgentImpl implements Agent {

    private double speed;
    private double remainingBattery;
    private double range;
    private double accuracy;

    public AgentImpl() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        speed = random.nextDouble(100);
        remainingBattery = random.nextDouble(100);
        range = random.nextDouble(100);
        accuracy = random.nextDouble(100);
    }

    @Override
    public ServiceManager services() {
        return null;
    }

    @Override
    public AgentService getService() {
        return null;
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
    public VisualiserClient visualiser() {
        return new NullVisualiser();
    }

    @Override
    public Endpoint endpoint() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void schedule(Runnable task) {

    }

    @Override
    public void setVisualiser(VisualiserClient visualiser) {

    }

    @Override
    public UnderseaRuntimeConfig config() {
        return null;
    }


    @Override
    public void start() {

    }

    @Override
    public boolean isAvailable() {
        return false;
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

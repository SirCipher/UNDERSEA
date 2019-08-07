package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.models.Agent;

import java.util.concurrent.ThreadLocalRandom;

public class AgentImpl implements Agent {

    private int noSensors;
    private double remainingBattery;
    private double range;

    public AgentImpl() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        noSensors = random.nextInt(100);
        remainingBattery = random.nextDouble(100);
        range = random.nextDouble(100);
    }

    @Override
    public String toString() {
        return "AgentImpl{" +
                "noSensors=" + noSensors +
                ", remainingBattery=" + remainingBattery +
                ", range=" + range +
                '}';
    }

}

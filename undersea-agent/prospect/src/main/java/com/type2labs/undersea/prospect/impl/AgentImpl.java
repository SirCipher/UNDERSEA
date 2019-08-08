package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.models.*;
import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
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
    public String toString() {
        return "AgentImpl{" +
                "speed=" + speed +
                ", remainingBattery=" + remainingBattery +
                ", range=" + range +
                ", accuracy=" + accuracy +
                '}';
    }

    @Override
    public ConsensusAlgorithm consensusAlgorithm() {
        return null;
    }

    @Override
    public BlockchainNetwork blockchain() {
        return null;
    }

    @Override
    public Controller controller() {
        return null;
    }

    @Override
    public MissionPlanner missionPlanner() {
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
}

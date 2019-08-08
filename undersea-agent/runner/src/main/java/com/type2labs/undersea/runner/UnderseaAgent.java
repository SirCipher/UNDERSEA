package com.type2labs.undersea.runner;

import com.type2labs.undersea.models.*;
import com.type2labs.undersea.models.blockchain.Blockchain;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class UnderseaAgent implements Agent {

    private final ConsensusAlgorithm consensusAlgorithm;
    private final Blockchain blockchain;
    private final Controller controller;
    private final MissionPlanner missionPlanner;

    public UnderseaAgent(ConsensusAlgorithm consensusAlgorithm, Blockchain blockchain, Controller controller,
                         MissionPlanner missionPlanner) {
        this.consensusAlgorithm = consensusAlgorithm;
        this.blockchain = blockchain;
        this.controller = controller;
        this.missionPlanner = missionPlanner;
    }

    @Override
    public ConsensusAlgorithm consensusAlgorithm() {
        return null;
    }

    @Override
    public Blockchain blockchain() {
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
        return null;
    }

}

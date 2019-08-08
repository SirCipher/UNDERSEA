package com.type2labs.undersea.runner;

import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class UnderseaAgent implements Agent {

    /*
        Not final as this may change based on mission requirements
     */
    private MissionPlanner missionPlanner;

    private final ConsensusAlgorithm consensusAlgorithm;
    private final BlockchainNetwork blockchainNetwork;
    private final Controller controller;

    public UnderseaAgent(ConsensusAlgorithm consensusAlgorithm, BlockchainNetwork blockchainNetwork,
                         Controller controller,
                         MissionPlanner missionPlanner) {
        this.consensusAlgorithm = consensusAlgorithm;
        this.blockchainNetwork = blockchainNetwork;
        this.controller = controller;
        this.missionPlanner = missionPlanner;
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
        return null;
    }

}

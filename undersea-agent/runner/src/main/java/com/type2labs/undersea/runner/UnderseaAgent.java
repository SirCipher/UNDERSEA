package com.type2labs.undersea.runner;

import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.models.AgentStatus;
import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class UnderseaAgent implements Agent {

    private static final Logger logger = LogManager.getLogger(UnderseaAgent.class);
    private final ScheduledExecutorService internalExecutor = new ScheduledThreadPoolExecutor(4);
    private final ConsensusAlgorithm consensusAlgorithm;
    private final BlockchainNetwork blockchainNetwork;
    private final Controller controller;
    private final AgentStatus agentStatus;
    /*
        Not final as this may change based on mission requirements
     */
    private MissionPlanner missionPlanner;

    public UnderseaAgent(ConsensusAlgorithm consensusAlgorithm, BlockchainNetwork blockchainNetwork,
                         Controller controller, MissionPlanner missionPlanner, AgentStatus agentStatus) {
        this.consensusAlgorithm = consensusAlgorithm;
        this.blockchainNetwork = blockchainNetwork;
        this.controller = controller;
        this.missionPlanner = missionPlanner;
        this.agentStatus = agentStatus;

        logger.info("Initialised agent: " + agentStatus.getName()
                + ". With: " + consensusAlgorithm.getClass().getSimpleName()
                + ", " + blockchainNetwork.getClass().getSimpleName()
                + ", " + controller.getClass().getSimpleName()
                + ", " + missionPlanner.getClass().getSimpleName());
    }

    @Override
    public ConsensusAlgorithm consensusAlgorithm() {
        return this.consensusAlgorithm;
    }

    @Override
    public BlockchainNetwork blockchain() {
        return this.blockchainNetwork;
    }

    @Override
    public Controller controller() {
        return this.controller;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return this.missionPlanner;
    }

    @Override
    public List<Pair<String, String>> status() {
        return this.agentStatus.transportableStatus();
    }

    @Override
    public void start() {
        consensusAlgorithm.start();
        blockchainNetwork.start();
        controller.start();
    }

    @Override
    public boolean isAvailable() {
        return consensusAlgorithm.isAvailable() && blockchainNetwork.isAvailable() && controller.isAvailable();
    }

    @Override
    public void shutdown() {
        consensusAlgorithm.shutdown();
        blockchainNetwork.shutdown();
        controller.shutdown();
    }

}

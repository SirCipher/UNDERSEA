package com.type2labs.undersea.agent;

import com.type2labs.consensus.Consensus;
import com.type2labs.undersea.controller.controller.Controller;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.seachain.BlockchainNetwork;

/**
 * Created by Thomas Klapwijk on 2019-07-25.
 */
public class AgentFacade {

    private static AgentFacade instance;

    private Controller controller;
    private BlockchainNetwork blockchainNetwork;
    private Consensus consensus;
    private MissionPlanner missionPlanner;

    private AgentFacade() {

    }

    public static AgentFacade getInstance() {
        if (instance == null) {
            instance = new AgentFacade();
        }

        return instance;
    }

    public boolean isInitialised() {
        return !(controller == null || blockchainNetwork == null || consensus == null || missionPlanner == null);
    }

    public void setBlockchainNetwork(BlockchainNetwork blockchainNetwork) {
        this.blockchainNetwork = blockchainNetwork;
    }

    public void setConsensus(Consensus consensus) {
        this.consensus = consensus;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setMissionPlanner(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
    }

}

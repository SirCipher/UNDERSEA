package com.type2labs.undersea.dsl.uuv.model;

import com.type2labs.consensus.Consensus;
import com.type2labs.undersea.agent.model.Agent;
import com.type2labs.undersea.controller.controller.Controller;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.seachain.BlockchainNetwork;

public class AgentProxy extends Agent {

    private boolean parsed = false;

    private String metaFileName;

    public void checkParsed(String access) {
        if (!parsed) {
            throw new RuntimeException("Cannot set " + access + " before agent has been parsed");
        }
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    @Override
    public void setBlockchainNetwork(BlockchainNetwork blockchainNetwork) {
        checkParsed("blockchain network");
        super.setBlockchainNetwork(blockchainNetwork);
    }

    @Override
    public void setConsensus(Consensus consensus) {
        checkParsed("consensus");
        super.setConsensus(consensus);
    }

    @Override
    public void setController(Controller controller) {
        checkParsed("controller");
        super.setController(controller);
    }

    @Override
    public void setMissionPlanner(MissionPlanner missionPlanner) {
        checkParsed("mission planner");
        super.setMissionPlanner(missionPlanner);
    }


}

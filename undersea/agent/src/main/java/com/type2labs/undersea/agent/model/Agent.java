package com.type2labs.undersea.agent.model;


import com.type2labs.consensus.Consensus;
import com.type2labs.undersea.controller.controller.Controller;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.model.node.Node;
import com.type2labs.undersea.seachain.BlockchainNetwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Agent {

    private static final Logger logger = LogManager.getLogger(Agent.class);

    private String name;
    private List<Node> assignedNodes = new ArrayList<>();
    private MissionPlanner missionPlanner;
    private BlockchainNetwork blockchainNetwork;
    private Consensus consensus;
    private Controller controller;

    private String rate;
    private String serverPort;
    private Range speedRange;
    private List<Sensor> sensors = new ArrayList<>();

    public List<Node> getAssignedNodes() {
        return assignedNodes;
    }

    public void setAssignedNodes(List<Node> assignedNodes) {
        this.assignedNodes = assignedNodes;
    }

    public BlockchainNetwork getBlockchainNetwork() {
        return blockchainNetwork;
    }

    public void setBlockchainNetwork(BlockchainNetwork blockchainNetwork) {
        this.blockchainNetwork = blockchainNetwork;
    }

    public Consensus getConsensus() {
        return consensus;
    }

    public void setConsensus(Consensus consensus) {
        this.consensus = consensus;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public MissionPlanner getMissionPlanner() {
        return missionPlanner;
    }

    public void setMissionPlanner(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Range getSpeedRange() {
        return speedRange;
    }

    public void setSpeedRange(Range speedRange) {
        this.speedRange = speedRange;
    }
}



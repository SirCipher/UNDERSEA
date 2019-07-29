package com.type2labs.undersea.models;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Agent {

    private static final Logger logger = LogManager.getLogger(Agent.class);

    private String name;
    private List<Node> assignedNodes = new ArrayList<>();

    private String rate;
    private String serverPort;
    private Range speedRange;
    private List<Sensor> sensors = new ArrayList<>();

    public double getBatteryRange() {
        return batteryRange;
    }

    // TEST
    // Assume 1 per metre travelled
    private double batteryRange = ThreadLocalRandom.current().nextDouble(100);

    public void assignNode(Node node) {
        assignedNodes.add(node);
    }

    public List<Node> getAssignedNodes() {
        return assignedNodes;
    }

    public void setAssignedNodes(List<Node> assignedNodes) {
        this.assignedNodes = assignedNodes;
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

    public String getBehaviour() {
        StringBuilder result = new StringBuilder();
        Iterator<Node> it = assignedNodes.iterator();

        while (it.hasNext()) {
            Node n = it.next();
            result.append(n.getVector().getX()).append(",").append(n.getVector().getY());

            if (it.hasNext()) {
                result.append(":");
            }
        }

        return result.toString();
    }
}



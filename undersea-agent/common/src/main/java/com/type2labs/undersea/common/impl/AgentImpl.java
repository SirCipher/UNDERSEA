package com.type2labs.undersea.common.impl;


import com.type2labs.undersea.common.Agent;
import com.type2labs.undersea.common.AgentService;
import com.type2labs.undersea.common.Endpoint;
import com.type2labs.undersea.common.ServiceManager;
import com.type2labs.undersea.common.visualiser.VisualiserClient;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentImpl implements Agent {

    private static final Logger logger = LogManager.getLogger(AgentImpl.class);

    private String name;
    // TODO: Remove
    private List<Node> assignedNodes = new ArrayList<>();

    private String rate;
    private int serverPort;
    private Range speedRange;
    private List<Sensor> sensors = new ArrayList<>();
    // TODO: Add to grammar
    private String host = "localhost";
    // TODO: Add to grammar
    private String groupName = "test";
    // TEST
    // Assume 1 per metre travelled
    private double batteryRange = ThreadLocalRandom.current().nextDouble(100);

    public AgentImpl(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        return null;
    }

    @Override
    public VisualiserClient visualiser() {
        return null;
    }

    @Override
    public Endpoint endpoint() {
        return null;
    }

    @Override
    public String name() {
        return name;
    }

    public double getBatteryRange() {
        return batteryRange;
    }

    public void assignNode(Node node) {
        assignedNodes.add(node);
    }

    public List<Node> getAssignedNodes() {
        return assignedNodes;
    }

    public void setAssignedNodes(List<Node> assignedNodes) {
        this.assignedNodes = assignedNodes;
    }

    public boolean hasSensorType(Sensor.SensorType sensorType) {
        return sensors.stream().anyMatch(s -> s.getSensorType().equals(sensorType));
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

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = Integer.parseInt(serverPort);
    }

    public void setServerPort(int port) {
        this.serverPort = port;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentImpl dslAgent = (AgentImpl) o;

        return getName().equals(dslAgent.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getServerPort());
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}


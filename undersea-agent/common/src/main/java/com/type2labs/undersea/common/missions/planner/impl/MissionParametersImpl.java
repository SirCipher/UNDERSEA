package com.type2labs.undersea.common.missions.planner.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParametersImpl implements MissionParameters {

    private int startingNode;
    private double[][] polygon;
    private List<Client> agents;
    private double minimumSensorRange;
    private double[][] centroids;

    public MissionParametersImpl(){

    }

    public MissionParametersImpl(int startingNode, double[][] polygon, int minimumSensorRange) {
        this.startingNode = startingNode;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    public double[][] getPolygon() {
        return polygon;
    }

    public double getMinimumSensorRange() {
        return minimumSensorRange;
    }

    public int getAgentCount() {
        return agents.size();
    }

    public int getStartingNode() {
        return startingNode;
    }

    @Override
    public List<Client> getClients() {
        return agents;
    }

    @Override
    public void setClients(List<Client> agents) {
        this.agents = agents;
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public void setCentroids(double[][] centroids) {
        this.centroids = centroids;
    }

    public void setStartingNode(int startingNode) {
        this.startingNode = startingNode;
    }

    public void setPolygon(double[][] polygon) {
        this.polygon = polygon;
    }

    public List<Client> getAgents() {
        return agents;
    }

    public void setAgents(List<Client> agents) {
        this.agents = agents;
    }

    public void setMinimumSensorRange(double minimumSensorRange) {
        this.minimumSensorRange = minimumSensorRange;
    }

    public double[] getCentroid(int index) {
        return centroids[index];
    }

}

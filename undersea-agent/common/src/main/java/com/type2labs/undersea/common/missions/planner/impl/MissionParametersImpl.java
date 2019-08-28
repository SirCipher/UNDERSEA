package com.type2labs.undersea.common.missions.planner.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParametersImpl implements MissionParameters {

    private final int startingNode;
    private final double[][] polygon;
    private List<Client> agents;
    private double minimumSensorRange;
    private double[][] centroids;

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


    public double[] getCentroid(int index) {
        return centroids[index];
    }

}

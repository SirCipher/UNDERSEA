package com.type2labs.undersea.common.missionplanner.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missionplanner.models.MissionParameters;

import java.util.Collection;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParametersImpl implements MissionParameters {

    private final int startingNode;
    private List<Client> agents;
    private final double[][] polygon;
    private double minimumSensorRange;
    private double[][] centroids;

    public MissionParametersImpl(int startingNode, double[][] polygon, int minimumSensorRange) {
        this.startingNode = startingNode;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    @Override
    public void setClients(List<Client> agents) {
        this.agents = agents;
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
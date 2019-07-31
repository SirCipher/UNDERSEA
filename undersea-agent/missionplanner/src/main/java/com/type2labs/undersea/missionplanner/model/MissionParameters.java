package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.models.model.Agent;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParameters {

    // TODO: remove once second provider is implemented
    private final int depot;
    private final List<Agent> agents;
    private final double[][] polygon;
    private double minimumSensorRange;
    private double[][] centroids;

    public MissionParameters(List<Agent> agents, int depot, double[][] polygon, int minimumSensorRange) {
        this.agents = agents;
        this.depot = depot;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    public List<Agent> getAgents() {
        return agents;
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

    public int getDepot() {
        return depot;
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

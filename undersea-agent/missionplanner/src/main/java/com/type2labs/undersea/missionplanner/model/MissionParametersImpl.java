package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.models.impl.AgentImpl;
import com.type2labs.undersea.models.missionplanner.MissionParameters;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParametersImpl implements MissionParameters {

    private final int startingNode;
    private final List<AgentImpl> agents;
    private final double[][] polygon;
    private double minimumSensorRange;
    private double[][] centroids;

    public MissionParametersImpl(List<AgentImpl> agents, int startingNode, double[][] polygon, int minimumSensorRange) {
        this.agents = agents;
        this.startingNode = startingNode;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    public List<AgentImpl> getAgents() {
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

    public int getStartingNode() {
        return startingNode;
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

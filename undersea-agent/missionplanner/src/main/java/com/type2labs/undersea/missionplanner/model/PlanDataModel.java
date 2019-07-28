package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlanDataModel {
    private final double[][] distanceMatrix;
    private final int agentCount;
    private final int depot;

    public PlanDataModel(MissionParameters missionParameters, double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.agentCount = missionParameters.getAgentCount();
        this.depot = missionParameters.getDepot();
    }

    public int getAgentCount() {
        return agentCount;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getDepot() {
        return depot;
    }
}

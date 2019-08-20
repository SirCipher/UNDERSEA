package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.common.missionplanner.MissionParameters;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
// TODO: Remove
public class PlanDataModel {
    private final double[][] distanceMatrix;
    private final int agentCount;
    private final int depot;

    public PlanDataModel(MissionParameters missionParametersImpl, double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.agentCount = missionParametersImpl.getAgents().size();
        this.depot = missionParametersImpl.getStartingNode();
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

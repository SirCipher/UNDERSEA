package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlanDataModel {
    private final double[][] data;
    private final int agentCount;
    private final int depot;

    public PlanDataModel(MissionParameters missionParameters, double[][] data) {
        this.data = data;
        this.agentCount = missionParameters.getAgentCount();
        this.depot = missionParameters.getDepot();
    }

    public int getAgentCount() {
        return agentCount;
    }

    public double[][] getData() {
        return data;
    }

    public int getDepot() {
        return depot;
    }
}

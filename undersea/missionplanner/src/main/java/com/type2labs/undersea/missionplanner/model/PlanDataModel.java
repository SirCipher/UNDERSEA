package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlanDataModel {
    private double[][] data;
    private int agentCount;
    private int depot;

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

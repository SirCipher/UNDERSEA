package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlanDataModel {
    private double[][] data;
    private int agentCount;
    private int depot;

    public PlanDataModel(double[][] data, int agentCount, int depot) {
        this.data = data;
        this.agentCount = agentCount;
        this.depot = depot;
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

package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParameters {

    private int agentCount;
    // TODO: remove once second provider is implemented
    private int depot;

    public MissionParameters(int agentCount, int depot) {
        this.agentCount = agentCount;
        this.depot = depot;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public int getDepot() {
        return depot;
    }
}

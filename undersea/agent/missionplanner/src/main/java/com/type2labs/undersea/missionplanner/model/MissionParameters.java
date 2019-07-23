package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParameters {

    private final int agentCount;
    // TODO: remove once second provider is implemented
    private final int depot;

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

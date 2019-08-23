package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
// TODO: Remove
public class PlanDataModel {
    private final double[][] distanceMatrix;
    private final int depot;

    public PlanDataModel(MissionParameters missionParameters, double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.depot = missionParameters.getStartingNode();
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getDepot() {
        return depot;
    }

}

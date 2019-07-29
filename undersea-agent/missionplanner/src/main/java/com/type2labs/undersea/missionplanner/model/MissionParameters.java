package com.type2labs.undersea.missionplanner.model;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionParameters {

    private final int agentCount;
    // TODO: remove once second provider is implemented
    private final int depot;
    private double minimumSensorRange;
    private final double[][] polygon;

    private double[][] centroids;

    public double[][] getPolygon() {
        return polygon;
    }

    public double getMinimumSensorRange() {
        return minimumSensorRange;
    }

    public MissionParameters(int agentCount, int depot, double[][] polygon, int minimumSensorRange) {
        this.agentCount = agentCount;
        this.depot = depot;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public int getDepot() {
        return depot;
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public double[] getCentroid(int index) {
        return centroids[index];
    }

    public void setCentroids(double[][] centroids) {
        this.centroids = centroids;
    }
}

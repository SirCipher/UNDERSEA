package com.type2labs.undersea.missionplanner.utils;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlannerUtils {

    private PlannerUtils() {

    }

    public static double[][] computeEuclideanDistanceMatrix(double[][] locations) {
        // Calculate distance matrix using Euclidean distance.
        double[][] distanceMatrix = new double[locations.length][locations.length];

        for (int fromNode = 0; fromNode < locations.length; ++fromNode) {
            for (int toNode = 0; toNode < locations.length; ++toNode) {

                if (fromNode == toNode) {
                    distanceMatrix[fromNode][toNode] = 0;
                } else {
                    distanceMatrix[fromNode][toNode] =
                            Math.hypot(locations[toNode][0] - locations[fromNode][0],
                                    locations[toNode][1] - locations[fromNode][1]);
                }
            }
        }

        return distanceMatrix;
    }

}

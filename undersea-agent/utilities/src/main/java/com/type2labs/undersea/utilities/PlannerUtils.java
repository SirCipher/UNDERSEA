package com.type2labs.undersea.utilities;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class PlannerUtils {

    private PlannerUtils() {

    }

    public static double distanceBetweenCoordinates(double lat1, double long1, double lat2, double long2) {
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = long2 * Math.PI / 180 - long1 * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;

        return d * 1000; // meters
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

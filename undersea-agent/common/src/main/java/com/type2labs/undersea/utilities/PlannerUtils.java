/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

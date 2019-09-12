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

package com.type2labs.undersea.common.missions.planner.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

import java.util.List;

/**
 * Mission parameters that will be used to decompose the mission by the
 * {@link com.type2labs.undersea.common.missions.planner.model.MissionPlanner}
 */
public class MissionParametersImpl implements MissionParameters {

    private int startingNode;
    private double[][] polygon;
    private List<Client> agents;
    private double minimumSensorRange;
    private double[][] centroids;

    // Required by Jackson
    @SuppressWarnings("unused")
    public MissionParametersImpl() {

    }

    public MissionParametersImpl(int startingNode, double[][] polygon, int minimumSensorRange) {
        this.startingNode = startingNode;
        this.polygon = polygon;
        this.minimumSensorRange = minimumSensorRange;
    }

    public double[][] getPolygon() {
        return polygon;
    }

    public void setPolygon(double[][] polygon) {
        this.polygon = polygon;
    }

    public double getMinimumSensorRange() {
        return minimumSensorRange;
    }

    public int getStartingNode() {
        return startingNode;
    }

    @Override
    public List<Client> getClients() {
        return agents;
    }

    @Override
    public void setClients(List<Client> agents) {
        this.agents = agents;
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public void setCentroids(double[][] centroids) {
        this.centroids = centroids;
    }

    public List<Client> getAgents() {
        return agents;
    }

    public void setAgents(List<Client> agents) {
        this.agents = agents;
    }

}

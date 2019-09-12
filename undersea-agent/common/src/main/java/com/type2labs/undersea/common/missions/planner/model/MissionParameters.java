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

package com.type2labs.undersea.common.missions.planner.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;

import java.util.List;

/**
 * Mission parameters that will be used to decompose the mission by the
 * {@link com.type2labs.undersea.common.missions.planner.model.MissionPlanner}
 * <p>
 * The jackson annotation below needs to be migrated to being automatically set at runtime
 */
@JsonDeserialize(as = MissionParametersImpl.class)
public interface MissionParameters {

    /**
     * Which node that the {@link com.type2labs.undersea.common.agent.Agent}s should start at when they execute the
     * mission.
     *
     * @return
     */
    int getStartingNode();

    /**
     * All the available {@link Client}s that will be executing this mission
     *
     * @return the assigned clients
     */
    List<Client> getClients();

    /**
     * All the available {@link Client}s that will be executing this mission
     *
     * @param clients to set
     */
    void setClients(List<Client> clients);

    /**
     * The mission boundary
     *
     * @return the boundary
     */
    double[][] getPolygon();

    /**
     * Sets the mission boundary
     *
     * @param polygon/boundary
     */
    void setPolygon(double[][] polygon);

    /**
     * The minimum sensor range as defined by an {@link com.type2labs.undersea.common.agent.Agent}'s
     * {@link com.type2labs.undersea.common.agent.Subsystem}
     *
     * @return the minimum sensor range
     */
    double getMinimumSensorRange();

    /**
     * The decomposed centroids from the mission boundary
     *
     * @return the centroids
     */
    double[][] getCentroids();

    /**
     * Sets the decomposed centroids from the mission boundary
     *
     * @param centroids to set
     */
    void setCentroids(double[][] centroids);
}

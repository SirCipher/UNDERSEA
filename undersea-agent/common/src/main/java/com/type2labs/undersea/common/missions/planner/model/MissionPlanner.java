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

import com.type2labs.undersea.common.agent.AgentAware;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.missions.PlannerException;

/**
 * A mission planner which will decompose the {@link MissionParameters#getPolygon()} by the
 * {@link MissionParameters#getMinimumSensorRange()} and assign each of the {@link GeneratedMission}s to the
 * {@link MissionParameters#getClients()}.
 * <p>
 * Implementors must be thread safe and must handle all native library handling by themselves - if required.
 */
public interface MissionPlanner extends AgentAware {

    /**
     * Generates a mission meeting the given criteria. Mission parameters are retrieved from the
     * {@link RuntimeConfig}
     *
     * @return a generated mission
     * @throws PlannerException if the arguments do not match the expected MATLAB parameters
     */
    GeneratedMission generate() throws PlannerException;

    /**
     * Print a given mission
     *
     * @param generatedMission to print
     */
    void print(GeneratedMission generatedMission);

}

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

import com.type2labs.undersea.common.missions.task.model.Task;

import java.util.List;

/**
 * Structure for a mission that has been generated by a {@link MissionPlanner}. This structure contains all of the
 * sub-missions for agents with the cluster.
 */
public interface GeneratedMission {

    /**
     * Adds an {@link AgentMission} to the structure
     *
     * @param agentMission to add
     */
    void addAgentMission(AgentMission agentMission);

    /**
     * Returns the overall progress that the agents have made through the mission. The progress returned will be the
     * given progress from the last heartbeat from the agents
     *
     * @return the overall progress
     */
    double progress();

    /**
     * Returns the individual {@link AgentMission}s for this generated mission
     *
     * @return the missions
     */
    List<AgentMission> subMissions();

    /**
     * Returns all tasks from all of the {@link AgentMission}s
     *
     * @return the tasks
     */
    List<Task> allTasks();

    /**
     * The polygon (mission) area that the {@link com.type2labs.undersea.common.agent.Agent}s are covering while
     * performing all the missions
     *
     * @return the mission area
     */
    double[][] polygon();

}

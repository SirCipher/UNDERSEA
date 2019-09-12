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
import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

/**
 * A mission manager that assigns missions, manages the mission decomposition, and executes tasks for an
 * {@link com.type2labs.undersea.common.agent.Agent}
 */
public interface MissionManager extends AgentService {

    /**
     * Adds a task to the {@link com.type2labs.undersea.common.agent.Agent}
     *
     * @param tasks to assign to this agent
     */
    void addTasks(List<Task> tasks);

    /**
     * The global mission that all agents are working together to complete
     *
     * @param mission the global mission
     */
    void assignMission(GeneratedMission mission);

    /**
     * Returns all tasks that have been assigned to this agent
     *
     * @return the {@link Task}s assigned to the {@link com.type2labs.undersea.common.agent.Agent}
     */
    List<Task> getAssignedTasks();

    /**
     * Checks whether or not the agent has been assigned a mission
     *
     * @return whether or not the agent has been assigned a mission
     */
    boolean missionHasBeenAssigned();

    /**
     * Returns the {@link MissionPlanner} implementation
     *
     * @return the assigned {@link MissionManager}
     */
    MissionPlanner missionPlanner();

    /**
     * Notifies the manager that a message has been received from the underlying hardware layer
     *
     * @param message received
     */
    void notify(String message);

}

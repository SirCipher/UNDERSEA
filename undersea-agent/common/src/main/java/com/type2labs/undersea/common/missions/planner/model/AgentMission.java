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
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.task.model.Task;

import java.util.List;

/**
 * A mission for an {@link com.type2labs.undersea.common.agent.Agent}. Containing all the {@link Task}s that compose it.
 * <p>
 * The jackson annotation below needs to be migrated to being automatically set at runtime
 */
@JsonDeserialize(as = AgentMissionImpl.class)
public interface AgentMission {

    /**
     * Who this mission is assigned to
     *
     * @return the assigned client
     */
    Client getAssignee();

    /**
     * A list of all the tasks that compose this mission. All tasks should have a corresponding
     * {@link com.type2labs.undersea.common.missions.task.model.TaskExecutor} that is registered for them
     *
     * @return the tasks assigned
     */
    List<Task> getTasks();

    /**
     * @return the progress made through this mission (number of tasks marked as complete)
     */
    double getProgress();

    /**
     * Whether or not this mission has been started
     *
     * @return whether or not this mission has been started
     */
    boolean started();

    /**
     * Mark this mission as started r not
     *
     * @param started
     */
    void setStarted(boolean started);

    /**
     * The {@link com.type2labs.undersea.common.cluster.PeerId} of the {@link Client} that has been assigned to this
     * mission
     *
     * @return the ID
     */
    String peerId();

}

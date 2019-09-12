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

package com.type2labs.undersea.common.missions.task.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;

import java.util.UUID;

/**
 * A composed task ready for an {@link com.type2labs.undersea.common.agent.Agent} to execute. Associated with a given
 * {@link com.type2labs.undersea.common.missions.planner.model.AgentMission}. Each task must have an associated
 * {@link TaskExecutor} for the given {@link TaskType}.
 * <p>
 * The jackson annotation below needs to be migrated to being automatically set at runtime
 */
@JsonDeserialize(as = TaskImpl.class)
public interface Task {

    /**
     * The coordinates that this task will be executed at (if required). If no coordinates are required, then the
     * associated {@link TaskExecutor} should be aware
     *
     * @return the coordinates
     */
    double[] getCoordinates();

    /**
     * The progress through this task
     *
     * @return the progress
     */
    double getProgress();

    /**
     * The current status of this task
     *
     * @return the status
     */
    TaskStatus getTaskStatus();

    /**
     * Sets a new task status
     *
     * @param taskStatus to change to
     */
    void setTaskStatus(TaskStatus taskStatus);

    /**
     * What type of task this is. This must be called by the {@link TaskExecutor} during initialisation to check that
     * it can handle it.
     *
     * @return the type
     */
    TaskType getTaskType();

    /**
     * A unique identifier for this task
     *
     * @return the identifier
     */
    UUID getUuid();

}

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.task.model.Task;

import java.util.Arrays;
import java.util.List;

/**
 * A mission which has been assigned to a particular {@link com.type2labs.undersea.common.agent.Agent}
 */
public class AgentMissionImpl implements AgentMission {

    private final double progress = 0;
    private List<Task> tasks;
    @JsonIgnore
    private Client assignee;
    private String peerId;
    private String points;
    private boolean started = false;

    // Required by Jackson
    @SuppressWarnings("unused")
    public AgentMissionImpl() {

    }

    public AgentMissionImpl(Client assignee, List<Task> tasks) {
        this.assignee = assignee;
        this.peerId = assignee.peerId().toString();
        this.tasks = tasks;
    }

    @Override
    @JsonProperty("peerId")
    public String peerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    @Override
    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public Client getAssignee() {
        return assignee;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public double getProgress() {
        return 0;
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public String toString() {
        return "AgentMissionImpl{" +
                "tasks=" + Arrays.toString(tasks.toArray()) +
                ", progress=" + progress +
                '}';
    }
}

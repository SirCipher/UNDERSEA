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

package com.type2labs.undersea.common.logger.model;

import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.AgentService;

/**
 * A entry produced by an {@link AgentService} after some event has been fired. Entries can be flagged as
 * replicable and they will be replicated across the cluster.
 */
public class LogEntry {

    /**
     * The {@link ConsensusAlgorithm} term that this entry was created in
     */
    private int term;

    /**
     * Whether or not this entry should be replicated across the cluster
     */
    private boolean replicate = true;

    /**
     * Who the leader was when this entry was created
     */
    private PeerId leader;
    private Object data;
    private Object value;

    /**
     * Which service created this entry
     */
    private AgentService agentService;

    public LogEntry(PeerId leader, Object data, Object value, int term, AgentService agentService, boolean replicate) {
        this.leader = leader;
        this.data = data;
        this.term = term;
        this.replicate = replicate;

        if (value != null) {
            this.value = value.toString();
        }

        this.agentService = agentService;
    }

    public static Class<? extends AgentService> forName(String className) {
        switch (className.toUpperCase()) {
            case "MISSIONMANAGER":
                return MissionManager.class;
            case "CONSENSUSALGORITHM":
                return ConsensusAlgorithm.class;
            default:
                throw new IllegalArgumentException(className);
        }
    }

    public boolean replicate() {
        return replicate;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "term=" + term +
                ", data=" + data +
                ", value=" + value +
                ", agentService=" + agentService.getClass().getSimpleName() +
                '}';
    }

    public Object getValue() {
        return value;
    }

    public PeerId getLeader() {
        return leader;
    }

    public int getTerm() {
        return term;
    }

    public Object getData() {
        return data;
    }

    public AgentService getAgentService() {
        return agentService;
    }
}

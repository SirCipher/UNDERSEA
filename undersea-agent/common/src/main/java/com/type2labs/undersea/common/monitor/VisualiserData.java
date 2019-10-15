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

package com.type2labs.undersea.common.monitor;

import java.io.Serializable;

/**
 * DTO for writing an {@link com.type2labs.undersea.common.agent.Agent}'s current state to the Visualiser
 */
public class VisualiserData implements Serializable {

    private static final long serialVersionUID = 1980452255341789631L;
    private final String name;
    private final String consensusPeerId;
    private String multiRoleStatus = "N/A";
    private String serviceManagerStatus = "N/A";
    private String consensusRole = "N/A";
    private String leaderPeerId;
    private int noAssignedTasks;
    private int completedTasks;
    private int port;
    private int noPeers;

    public VisualiserData(String consensusPeerId, String name) {
        this.consensusPeerId = consensusPeerId;
        this.name = name;
    }

    public VisualiserData(String name, String consensusPeerId, String multiRoleStatus, String serviceManagerStatus,
                          String consensusRole, String leaderPeerId, int noAssignedTasks, int completedTasks, int port,
                          int noPeers) {
        this.name = name;
        this.consensusPeerId = consensusPeerId;
        this.multiRoleStatus = multiRoleStatus;
        this.serviceManagerStatus = serviceManagerStatus;
        this.consensusRole = consensusRole;
        this.leaderPeerId = leaderPeerId;
        this.noAssignedTasks = noAssignedTasks;
        this.completedTasks = completedTasks;
        this.port = port;
        this.noPeers = noPeers;
    }

    public int getPort() {
        return port;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public String getMultiRoleStatus() {
        return multiRoleStatus;
    }

    @Override
    public String toString() {
        return "VisualiserData{" +
                "name='" + name + '\'' +
                ", consensusPeerId='" + consensusPeerId + '\'' +
                ", multiRoleStatus='" + multiRoleStatus + '\'' +
                ", serviceManagerStatus='" + serviceManagerStatus + '\'' +
                ", consensusRole='" + consensusRole + '\'' +
                ", leaderPeerId='" + leaderPeerId + '\'' +
                ", noTasks=" + noAssignedTasks +
                ", completedTasks=" + completedTasks +
                ", port=" + port +
                ", noPeers=" + noPeers +
                '}';
    }

    public String getServiceManagerStatus() {
        return serviceManagerStatus;
    }

    public void setServiceManagerStatus(String serviceManagerStatus) {
        this.serviceManagerStatus = serviceManagerStatus;
    }

    public String getConsensusPeerId() {
        return consensusPeerId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return consensusRole;
    }

    public void setRole(String consensusRole) {
        this.consensusRole = consensusRole;
    }

    public int getNoAssignedTasks() {
        return noAssignedTasks;
    }

    public String getLeaderPeerId() {
        return leaderPeerId;
    }

    public void setLeaderPeerId(String leaderPeerId) {
        this.leaderPeerId = leaderPeerId;
    }

    public int getNoPeers() {
        return noPeers;
    }
}

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

package com.type2labs.undersea.prospect.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.*;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contains an agent's multi-role state. A node that is in a {@link MultiRoleStatus#LEADER_FOLLOWER} is leading one
 * cluster and a follower in another.
 */
public class MultiRoleStateImpl implements MultiRoleState {

    private static final Logger logger = LogManager.getLogger(MultiRoleStateImpl.class);

    /**
     * The leader of the parent cluster. Followers know of their raft node leader's multi role leader. This is in
     * case their leader fails and their is a shift in the hierarchy
     */
    private Client leader;
    private MultiRoleStatus status = MultiRoleStatus.NOT_APPLIED;
    private RaftNode raftNode;
    private Map<PeerId, Client> remotePeers = new HashMap<>();
    /**
     * A map of clients that we have communicated with and their respective states. This will be different to what
     * {@link Agent#clusterClients()} will return as that is a list of all known nodes.
     */
    private Map<Client, MultiRoleClientState> clientStates = new HashMap<>();

    MultiRoleStateImpl(RaftNode raftNode) {
        this.raftNode = Objects.requireNonNull(raftNode);
    }

    public Client getLeader() {
        return leader;
    }

    @Override
    public MultiRoleStatus status() {
        return status;
    }

    public void setStatus(MultiRoleStatus status) {
        this.status = status;
    }

    @Override
    public boolean isLeader() {
        return status == MultiRoleStatus.LEADER;
    }

    @Override
    public void setLeader(Client leader) {
        this.leader = leader;
    }

    public boolean isApplied() {
        return status == MultiRoleStatus.LEADER_FOLLOWER || status == MultiRoleStatus.LEADER;
    }

    /**
     * Sets the {@link MultiRoleStatus} based on the current {@link ConsensusAlgorithm#raftRole()}
     */
    void updateStatus() {
        if (raftNode.raftRole() == ConsensusAlgorithmRole.LEADER && this.leader != null) {
            this.status = MultiRoleStatus.LEADER_FOLLOWER;
        } else {
            this.status = MultiRoleStatus.NOT_APPLIED;
        }
    }

    private void checkAndAdd(Client client) {
        clientStates.putIfAbsent(client, new MultiRoleClientState(client));
    }

    /**
     * Redistributes the client's failed mission to our cluster
     *
     * @param client who failed
     */
    @Override
    public void handleFailure(Client client) {
        remotePeers.remove(client.peerId());

        ObjectMapper objectMapper = new ObjectMapper();
        String missionCoordinates = clientStates.get(client).getJsonMissionCoordinates();
        if (StringUtils.isEmpty(missionCoordinates)) {
            logger.error(raftNode.parent() + ": failed client {" + client.peerId() + "} did not sent a mission",
                    raftNode.parent());
            return;
        }

        Double[][] coordinates;
        try {
            coordinates = objectMapper.readValue(missionCoordinates, Double[][].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(raftNode.parent() + ": failed to parse json mission: " + e.getLocalizedMessage(),
                    raftNode.parent());
            return;
        }

        double[][] unboxed = new double[coordinates.length][];

        for (int i = 0; i < coordinates.length; i++) {
            unboxed[i] = new double[coordinates[i].length];
            for (int j = 0; j < coordinates[i].length; j++) {
                unboxed[i][j] = coordinates[i][j];
            }
        }

        raftNode.config().getRuntimeConfig().missionParameters().setPolygon(unboxed);
        raftNode.fireCallback(LifecycleEvent.ELECTED_LEADER);
    }

    @Override
    public void setGeneratedMission(Client client, String jsonMission) {
        checkAndAdd(client);
        MultiRoleClientState multiRoleClientState = clientStates.get(client);
        multiRoleClientState.setJsonMissionCoordinates(jsonMission);
    }

    @Override
    public Map<PeerId, Client> remotePeers() {
        return remotePeers;
    }


}

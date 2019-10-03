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

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.impl.RaftClientImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private static final Logger logger = LogManager.getLogger(RaftState.class);

    private final RaftNode raftNode;
    private ConcurrentMap<PeerId, Client> localNodes;
    private Pair<Client, ClusterState.ClientState> votedFor;
    private int currentTerm;
    private Candidate candidate;
    private ClusterState preVoteClusterState;
    private Client leader;

    public RaftState(RaftNode agent) {
        this.raftNode = agent;
        this.localNodes = agent.parent().clusterClients();
    }

    public ClusterState getPreVoteClusterState() {
        return preVoteClusterState;
    }

    public boolean isPreVoteState() {
        return preVoteClusterState != null;
    }

    public void initPreVoteClusterState() {
        this.preVoteClusterState = new ClusterState(raftNode);
    }

    public void initCandidate() {
        this.candidate = new Candidate(preVoteClusterState.getMembers().size() / 2 + 1);
        this.votedFor = preVoteClusterState.getNominee(raftNode.self());
        this.preVoteClusterState = null;
        this.currentTerm++;
    }

    public void clearCandidate() {
        this.candidate = null;
    }

    public Client getLeader() {
        return leader;
    }

    public void setLeader(Client leader) {
        this.leader = leader;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Pair<Client, ClusterState.ClientState> getVotedFor() {
        return votedFor;
    }

    public ClusterState clusterState() {
        return preVoteClusterState;
    }

    /**
     * Used only for simulation.
     * <p>
     * Creates an RPC client from the node
     *
     * @param node to discover
     */
    public void discoverNode(RaftNode node) {
        InetSocketAddress address = node.server().getSocketAddress();
        localNodes.computeIfAbsent(node.parent().peerId(), n -> new RaftClientImpl(raftNode, address,
                node.parent().peerId()));

        logger.info(raftNode.parent().name() + ": discovered: " + node.parent().name() + ", with PeerId: " + node.parent().peerId(), raftNode);
    }

    public ConcurrentMap<PeerId, Client> localNodes() {
        return localNodes;
    }

    public Client getClient(PeerId peerId) {
        return localNodes.get(peerId);
    }

    public int getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(int currentTerm) {
        this.currentTerm = currentTerm;
    }

    public synchronized void updateMembers(List<Client> clients) {
        localNodes.clear();

        for (Client client : clients) {
            if (client.peerId().equals(raftNode.parent().peerId())) {
                continue;
            }

            localNodes.put(client.peerId(), client);
            logger.info(raftNode.parent().name() + ": added client: " + client.peerId(), raftNode.parent());
        }
    }

    public void leaderFailed() {
        this.leader = null;
    }

    public void closeClients() {
        for (Client client : localNodes.values()) {
            client.shutdown();
        }
    }

    public void removeNode(PeerId peerId) {
        Client client = localNodes.get(peerId);
        if (client == null) {
            return;
        }

        logger.info(raftNode.parent().name() + ": removing node: " + peerId, raftNode.parent());

        client.shutdown();
        localNodes.remove(client.peerId());
    }

    public class Candidate {
        private final int threshold;
        private final Set<Client> voters = new HashSet<>();

        public Candidate(int threshold) {
            this.threshold = threshold;
        }

        public void vote(Client client) {
            logger.info(raftNode.parent().name() + " registering vote from: " + client);
            this.voters.add(client);
        }

        public boolean wonRound() {
            return voters.size() >= threshold;
        }
    }

}

package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    public void toLeader(Client leader) {
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

        logger.info(raftNode.name() + ": discovered: " + node.parent().peerId(), raftNode);
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
            if(client.peerId().equals(raftNode.parent().peerId())){
                continue;
            }

            localNodes.put(client.peerId(), client);
            logger.info(raftNode.name() + ": added client: " + client.peerId(), raftNode.parent());
        }
    }

    public class Candidate {
        private final int threshold;
        private final Set<Client> voters = new HashSet<>();

        public Candidate(int threshold) {
            this.threshold = threshold;
        }

        public void vote(Client client) {
            logger.info(raftNode.name() + " registering vote from: " + client);
            this.voters.add(client);
        }

        public boolean wonRound() {
            return voters.size() >= threshold;
        }
    }

}

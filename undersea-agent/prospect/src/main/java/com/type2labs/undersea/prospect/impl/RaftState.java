package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private static final Logger logger = LogManager.getLogger(RaftState.class);

    private final ConcurrentMap<PeerId, Client> localNodes;
    private final RaftNode parent;
    private Client votedFor;
    private int currentTerm;
    private Candidate candidate;
    private ClusterState clusterState;
    private Client leader;

    public RaftState(RaftNode agent) {
        this.parent = agent;
        this.localNodes = agent.parent().clusterClients();
    }

    public void initCandidate(int term) {
        this.candidate = new Candidate((localNodes.size()));
        this.votedFor = null;
        this.clusterState = new ClusterState(parent, localNodes.size());
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

    public Client getVotedFor() {
        return votedFor;
    }

    public int clusterSize() {
        return clusterState.getMembers().size();
    }

    public ClusterState clusterState() {
        return clusterState;
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
        localNodes.computeIfAbsent(node.parent().peerId(), n -> new RaftClientImpl(parent, address,
                node.parent().peerId()));

        logger.info(parent.name() + ": discovered: " + node.parent().peerId(), parent);
    }

    public void removeNode(PeerId peerId) {
        localNodes.remove(peerId);
        logger.info(parent.name() + ": removing peer: " + peerId, parent);
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

    public void setVote(Client votedFor) {
        this.votedFor = votedFor;
    }

    public class Candidate {
        private final int threshold;
        private final Set<Client> voters = new HashSet<>();

        public Candidate(int threshold) {
            this.threshold = threshold;
        }

        public void vote(Client client) {
            logger.info(parent.name() + " registering vote for: " + client);
            this.voters.add(client);
        }

        public boolean wonRound() {
            return voters.size() >= threshold;
        }
    }

}

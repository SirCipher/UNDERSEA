package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.NodeLog;
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
    /**
     * Endpoint voted for during last term
     */
    private final NodeLog nodeLog = new NodeLog();
    private final RaftNode parent;
    private Client votedFor;
    private int term;
    private Candidate candidate;
    private ClusterState clusterState;

    public RaftState(RaftNode parent) {
        this.parent = parent;
        this.localNodes = parent.agent().clusterClients();
        this.clusterState = new ClusterState(parent, term);
    }

    public void initCandidate() {
        this.candidate = new Candidate((localNodes.size() - 1));
        this.votedFor = null;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Client getVotedFor() {
        return votedFor;
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
        localNodes.computeIfAbsent(node.parent().peerId(), n -> new RaftClientImpl(parent, address, node.parent().peerId()));
    }

    public ConcurrentMap<PeerId, Client> localNodes() {
        return localNodes;
    }

    public Client getClient(PeerId peerId) {
        return localNodes.get(peerId);
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
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
            this.voters.add(client);
            logger.info(parent.name() + " registering vote for: " + client);
        }

        public boolean wonRound() {
            return voters.size() >= threshold;
        }
    }

}

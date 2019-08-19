package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.networking.ClientImpl;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private final ConcurrentMap<RaftPeerId, Client> localNodes = new ConcurrentHashMap<>();
    /**
     * Endpoint voted for during last term
     */
    private final NodeLog nodeLog = new NodeLog();
    private final RaftNode parent;
    private Client votedFor;
    private int term;
    private Candidate candidate;

    public RaftState(RaftNode parent) {
        this.parent = parent;
    }

    public void initCandidate() {
        this.candidate = new Candidate(localNodes.size() / 2);
        this.votedFor = null;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Client getVotedFor() {
        return votedFor;
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
        localNodes.computeIfAbsent(node.peerId(), n -> new ClientImpl(parent, address, node.peerId()));
    }

    public ConcurrentMap<RaftPeerId, Client> localNodes() {
        return localNodes;
    }

    public Client getClient(RaftPeerId peerId) {
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
        private final int mean;
        private final Set<Client> voters = new HashSet<>();

        public Candidate(int mean) {
            this.mean = mean;
        }

        public void vote(Client client) {
            this.voters.add(client);
        }

        public boolean wonRound() {
            return voters.size() >= mean;
        }
    }

}

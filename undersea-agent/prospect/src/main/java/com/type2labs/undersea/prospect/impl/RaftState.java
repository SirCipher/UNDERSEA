package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.networking.ClientImpl;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private final ConcurrentMap<RaftPeerId, Client> localNodes = new ConcurrentHashMap<>();
    /**
     * Endpoint voted for during last term
     */
    private final NodeLog nodeLog = new NodeLog();
    private Client votedFor;
    private int term;
    private final RaftNode parent;

    public RaftState(RaftNode parent){
        this.parent = parent;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setVote(Client votedFor) {
        this.votedFor = votedFor;
    }

}

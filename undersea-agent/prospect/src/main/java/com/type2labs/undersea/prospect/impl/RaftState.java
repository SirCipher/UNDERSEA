package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.Endpoint;
import com.type2labs.undersea.prospect.NodeLog;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    // TODO: Remove raft node value as we won't know it
    private final ConcurrentMap<Endpoint, RaftNode> localNodes = new ConcurrentHashMap<>();
    private final ConcurrentMap<Endpoint, RaftNode> lastNodeGroup = new ConcurrentHashMap<>();
    /**
     * Endpoint voted for during last term
     */
    private final NodeLog nodeLog = new NodeLog();
    private Endpoint votedFor;
    private int term;

    public Endpoint getVotedFor() {
        return votedFor;
    }

    public void discoverNode(RaftNode node) {
        localNodes.putIfAbsent(node.getLocalEndpoint(), node);
    }

    public ConcurrentMap<Endpoint, RaftNode> localNodes() {
        return localNodes;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setVote(Endpoint votedFor) {
        this.votedFor = votedFor;
    }

}

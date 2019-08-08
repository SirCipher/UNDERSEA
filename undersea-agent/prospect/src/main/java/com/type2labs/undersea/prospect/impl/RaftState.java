package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private final ConcurrentMap<Endpoint, RaftNode> localNodes = new ConcurrentHashMap<>();
    private final ConcurrentMap<Endpoint, RaftNode> lastNodeGroup = new ConcurrentHashMap<>();

    private int term;

    public void discoverNode(RaftNode node) {
        localNodes.putIfAbsent(node.getLocalEndpoint(), node);
    }

    public ConcurrentMap<Endpoint, RaftNode> localNodes (){
        return localNodes;
    }



}

package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.model.Endpoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private final ConcurrentMap<Endpoint, RaftNodeImpl> nodes = new ConcurrentHashMap<>();


}

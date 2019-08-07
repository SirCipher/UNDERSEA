package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.model.Endpoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaftState {

    private final ConcurrentMap<Endpoint, RaftNodeImpl> nodes = new ConcurrentHashMap<>();


}

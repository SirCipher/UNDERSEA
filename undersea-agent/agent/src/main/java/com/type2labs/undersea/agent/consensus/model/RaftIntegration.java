package com.type2labs.undersea.agent.consensus.model;

import com.type2labs.undersea.agent.consensus.impl.RaftNodeImpl;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public interface RaftIntegration {

    Endpoint getLocalEndpoint();

    boolean ready();

    ConcurrentMap<Endpoint, RaftNodeImpl> localNodes();

    void schedule(Runnable task, long delayInMillis, TimeUnit timeUnit);

}

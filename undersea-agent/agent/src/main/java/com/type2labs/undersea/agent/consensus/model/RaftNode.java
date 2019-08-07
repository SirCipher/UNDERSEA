package com.type2labs.undersea.agent.consensus.model;

import com.type2labs.undersea.agent.consensus.RaftClusterConfig;
import com.type2labs.undersea.agent.consensus.impl.PoolInfo;
import com.type2labs.undersea.agent.consensus.impl.RaftNodeImpl;
import com.type2labs.undersea.models.Agent;

public interface RaftNode {

    String name();

    Endpoint getLocalEndpoint();

    void start();

    boolean isAvailable();

    Agent agent();


    void execute(Runnable task);

    RaftIntegration integration();

    PoolInfo poolInfo();

    RaftNodeImpl.RaftRole getRaftRole();

    RaftClusterConfig config();

}

package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.models.Agent;

public interface RaftNode extends ConsensusAlgorithm {

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

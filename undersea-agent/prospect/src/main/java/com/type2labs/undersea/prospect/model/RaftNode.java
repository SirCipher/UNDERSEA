package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;

public interface RaftNode extends ConsensusAlgorithm {

    Agent agent();

    RaftClusterConfig config();

    void execute(Runnable task);

    Endpoint getLocalEndpoint();

    RaftNodeImpl.RaftRole getRaftRole();

    RaftIntegration integration();

    String name();

    PoolInfo poolInfo();

}

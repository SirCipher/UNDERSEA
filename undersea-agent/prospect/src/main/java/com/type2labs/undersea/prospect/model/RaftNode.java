package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.networking.Endpoint;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.impl.RaftState;

public interface RaftNode extends ConsensusAlgorithm {

    Agent agent();

    RaftClusterConfig config();

    void execute(Runnable task);

    Endpoint getLocalEndpoint();

    RaftNodeImpl.RaftRole getRaftRole();

    RaftIntegration integration();

    String name();

    PoolInfo poolInfo();

    RaftState state();

    void toLeader();

    void toFollower(int term);

    void toCandidate();
}

package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.GrpcServer;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.impl.RaftState;

import java.util.Set;

public interface RaftNode extends ConsensusAlgorithm {

    Agent agent();

    void execute(Runnable task);

    RaftRole getRaftRole();

    RaftIntegration integration();

    String name();

    RaftState state();

    void toLeader();

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

}

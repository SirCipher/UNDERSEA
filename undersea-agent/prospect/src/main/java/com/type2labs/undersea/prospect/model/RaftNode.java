package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.prospect.impl.GrpcServer;
import com.type2labs.undersea.prospect.impl.RaftState;

public interface RaftNode extends ConsensusAlgorithm {

    Agent agent();

    void execute(Runnable task);

    RaftRole getRaftRole();

    MultiRoleState multiRole();

    RaftIntegration integration();

    String name();

    RaftState state();

    void toLeader();

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

}

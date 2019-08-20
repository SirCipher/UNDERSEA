package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.*;

public interface RaftNode extends ConsensusAlgorithm {

    Agent agent();

    RaftClusterConfig config();

    void execute(Runnable task);

    RaftNodeImpl.RaftRole getRaftRole();

    RaftIntegration integration();

    String name();

    RaftState state();

    void toLeader();

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

    RaftPeerId peerId();
}

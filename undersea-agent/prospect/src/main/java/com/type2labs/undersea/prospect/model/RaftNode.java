package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.prospect.impl.GrpcServer;
import com.type2labs.undersea.prospect.impl.RaftState;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.utilities.lang.ReschedulableTask;

public interface RaftNode extends ConsensusAlgorithm {

    void execute(Runnable task);

    RaftRole getRaftRole();

    String name();

    RaftState state();

    void toLeader(int term);

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

    void schedule(ReschedulableTask task, long delayInMillis);

    RaftClient self();

}

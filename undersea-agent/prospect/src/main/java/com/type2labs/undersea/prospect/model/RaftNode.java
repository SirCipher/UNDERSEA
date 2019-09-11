package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.consensus.RaftRole;
import com.type2labs.undersea.prospect.impl.GrpcServer;
import com.type2labs.undersea.prospect.impl.RaftState;
import com.type2labs.undersea.prospect.networking.model.RaftClient;

public interface RaftNode extends ConsensusAlgorithm {

    void execute(Runnable task);

    RaftRole raftRole();

    RaftState state();

    void toLeader(int term);

    void toFollower(int term);

    void toCandidate();

    GrpcServer server();

    void schedule(Runnable task, long delayInMillis);

    RaftClient self();

}

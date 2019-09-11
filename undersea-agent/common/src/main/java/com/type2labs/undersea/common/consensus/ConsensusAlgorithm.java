package com.type2labs.undersea.common.consensus;


import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.service.AgentService;

public interface ConsensusAlgorithm extends AgentService {

    RaftClusterConfig config();

    RaftRole raftRole();

    MultiRoleState multiRoleState();

    int term();

    PeerId leaderPeerId();

}

package com.type2labs.undersea.common.consensus;


import com.type2labs.undersea.common.config.UnderseaConfig;
import com.type2labs.undersea.common.service.AgentService;

public interface ConsensusAlgorithm extends AgentService {

    UnderseaConfig config();

}

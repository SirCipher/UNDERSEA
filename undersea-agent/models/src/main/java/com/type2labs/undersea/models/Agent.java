package com.type2labs.undersea.models;

import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface Agent extends AgentService {

    AgentServices services();

    AgentService getService();

    List<Pair<String, String>> status();

}

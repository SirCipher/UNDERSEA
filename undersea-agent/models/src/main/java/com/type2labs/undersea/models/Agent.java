package com.type2labs.undersea.models;

import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface Agent extends Initialiseable{

    ConsensusAlgorithm consensusAlgorithm();

    BlockchainNetwork blockchain();

    Controller controller();

    MissionPlanner missionPlanner();

    List<Pair<String, String>> status();

}

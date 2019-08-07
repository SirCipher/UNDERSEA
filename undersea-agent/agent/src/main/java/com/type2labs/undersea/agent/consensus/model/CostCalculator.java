package com.type2labs.undersea.agent.consensus.model;

import java.util.Map;

public interface CostCalculator {

    Map<Endpoint, Double> generate(RaftNode parent);

}

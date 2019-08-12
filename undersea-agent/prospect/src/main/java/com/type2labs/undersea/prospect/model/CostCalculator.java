package com.type2labs.undersea.prospect.model;

import java.util.Map;

public interface CostCalculator {

    Map<Endpoint, Double> generateCosts(RaftNode parent);

}

package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.common.Endpoint;

import java.util.Map;

public interface CostCalculator {

    Map<Endpoint, Double> generateCosts(RaftNode parent);

}

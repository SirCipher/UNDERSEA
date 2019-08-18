package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.prospect.networking.Client;

import java.util.Map;

public interface CostCalculator {

    Map<Client, Double> generateCosts(RaftNode parent);

}

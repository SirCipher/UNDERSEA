package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.prospect.impl.ClusterState;
import com.type2labs.undersea.prospect.networking.Client;

import java.util.Map;

public interface CostCalculator {

    void generateCosts(ClusterState clusterState);

}

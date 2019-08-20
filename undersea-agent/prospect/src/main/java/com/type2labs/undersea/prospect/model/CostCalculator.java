package com.type2labs.undersea.prospect.model;

import com.type2labs.undersea.prospect.impl.ClusterState;

public interface CostCalculator {

    void generateCosts(ClusterState clusterState);

}

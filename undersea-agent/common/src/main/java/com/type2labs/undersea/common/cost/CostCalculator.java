package com.type2labs.undersea.common.cost;

import com.type2labs.undersea.common.cluster.ClusterState;

public interface CostCalculator {

    void generateCosts(ClusterState clusterState);

}

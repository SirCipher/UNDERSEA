package com.type2labs.undersea.agent.consensus;

import static com.type2labs.undersea.utilities.Preconditions.isNotNull;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RaftClusterConfig {


    private CostConfiguration costConfiguration;


    public RaftClusterConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }


    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }
}

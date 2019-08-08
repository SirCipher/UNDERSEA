package com.type2labs.undersea.prospect;

import static com.type2labs.undersea.utilities.Preconditions.isNotNull;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RaftClusterConfig {

    public static final long HEARTBEAT_PERIOD = 100;

    private CostConfiguration costConfiguration;

    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }

    public RaftClusterConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }
}

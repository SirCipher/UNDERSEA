package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.config.UnderseaConfig;
import com.type2labs.undersea.prospect.model.CostCalculator;

import static com.type2labs.undersea.utilities.Preconditions.isNotNull;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class RaftClusterConfig implements UnderseaConfig {

    public static final long HEARTBEAT_PERIOD = 100;

    private CostConfiguration costConfiguration;
    private final UnderseaRuntimeConfig underseaRuntimeConfig;

    public UnderseaRuntimeConfig getUnderseaRuntimeConfig() {
        return underseaRuntimeConfig;
    }

    public RaftClusterConfig(UnderseaRuntimeConfig underseaRuntimeConfig){
        this.underseaRuntimeConfig = underseaRuntimeConfig;
    }

    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }

    public RaftClusterConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }


    public CostCalculator getCostCalculator() {
        return costConfiguration.getCostCalculator();
    }

}

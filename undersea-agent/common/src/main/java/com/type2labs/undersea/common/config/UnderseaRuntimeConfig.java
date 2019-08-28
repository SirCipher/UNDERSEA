package com.type2labs.undersea.common.config;

import com.type2labs.undersea.common.cost.CostCalculator;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;

import java.util.HashMap;
import java.util.Map;

import static com.type2labs.undersea.utilities.preconditions.Preconditions.isNotNull;

public class UnderseaRuntimeConfig {

    private VisualiserClient visualiser;
    private boolean visualiserEnabled = true;
    private boolean autoLogTransactions = true;
    private MissionParameters missionParameters;
    private CostConfiguration costConfiguration;

    private Map<Class<? extends UnderseaConfig>, UnderseaConfig> serviceConfigs = new HashMap<>();

    public UnderseaRuntimeConfig addServiceConfig(UnderseaConfig config) {
        this.serviceConfigs.put(config.getClass(), config);
        return this;
    }

    public UnderseaConfig getServiceConfig(Class<? extends UnderseaConfig> clazz) {
        return serviceConfigs.get(clazz);
    }

    public UnderseaRuntimeConfig missionParameters(MissionParameters missionParameters) {
        this.missionParameters = missionParameters;
        return this;
    }

    public MissionParameters missionParameters() {
        return missionParameters;
    }

    public UnderseaRuntimeConfig autoLogTransactions(boolean autoLogTransactions) {
        this.autoLogTransactions = autoLogTransactions;
        return this;
    }

    public boolean autoLogTransactions() {
        return autoLogTransactions;
    }

    public boolean isVisualiserEnabled() {
        return visualiserEnabled;
    }

    public UnderseaRuntimeConfig enableVisualiser(boolean visualiserEnabled) {
        this.visualiserEnabled = visualiserEnabled;

        return this;
    }

    public UnderseaRuntimeConfig setVisualiser(VisualiserClient visualiser) {
        this.visualiser = visualiser;
        this.visualiserEnabled = true;

        return this;
    }

    public CostConfiguration getCostConfiguration() {
        return costConfiguration;
    }

    public UnderseaRuntimeConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }

    public CostCalculator getCostCalculator() {
        return costConfiguration.getCostCalculator();
    }

}

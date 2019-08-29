package com.type2labs.undersea.common.config;

import com.type2labs.undersea.common.cost.CostCalculator;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;

import java.util.HashMap;
import java.util.Map;

import static com.type2labs.undersea.utilities.preconditions.Preconditions.isNotNull;

@SuppressWarnings("UnusedReturnValue")
public class UnderseaRuntimeConfig {

    private boolean visualiserEnabled = true;
    private MissionParameters missionParameters;
    private CostConfiguration costConfiguration;

    public UnderseaRuntimeConfig missionParameters(MissionParameters missionParameters) {
        this.missionParameters = missionParameters;
        return this;
    }

    public MissionParameters missionParameters() {
        return missionParameters;
    }

    public boolean isVisualiserEnabled() {
        return visualiserEnabled;
    }

    public UnderseaRuntimeConfig enableVisualiser(boolean visualiserEnabled) {
        this.visualiserEnabled = visualiserEnabled;

        return this;
    }

    public UnderseaRuntimeConfig setCostConfiguration(CostConfiguration costConfiguration) {
        isNotNull(costConfiguration, "Cost configuration cannot be null");
        this.costConfiguration = costConfiguration;

        return this;
    }

    public CostCalculator getCostCalculator() {
        if (costConfiguration == null) {
            throw new NullPointerException("Cost configuration not supplied");
        }

        return costConfiguration.getCostCalculator();
    }

}

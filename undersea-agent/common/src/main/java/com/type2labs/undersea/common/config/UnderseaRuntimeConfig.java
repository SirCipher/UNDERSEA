package com.type2labs.undersea.common.config;

import com.type2labs.undersea.common.missionplanner.MissionParameters;
import com.type2labs.undersea.common.monitor.VisualiserClient;

public class UnderseaRuntimeConfig {

    private VisualiserClient visualiser;
    private boolean visualiserEnabled = false;
    private boolean autoLogTransactions = true;
    private MissionParameters missionParameters;

    public UnderseaRuntimeConfig missionParameters(MissionParameters missionParameters){
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

}

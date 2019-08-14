package com.type2labs.undersea.common.config;

import com.type2labs.undersea.common.visualiser.VisualiserClient;

public class UnderseaRuntimeConfig {

    private VisualiserClient visualiser;
    private boolean visualiserEnabled = false;

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

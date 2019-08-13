package com.type2labs.undersea.common;

import com.type2labs.undersea.common.visualiser.VisualiserClient;

public class UnderseaRuntimeConfig {

    private VisualiserClient visualiser;
    private boolean visualiserEnabled = true;

    public boolean isVisualiserEnabled() {
        return visualiserEnabled;
    }

    public UnderseaRuntimeConfig setVisualiser(VisualiserClient visualiser) {
        this.visualiser = visualiser;
        this.visualiserEnabled = true;

        return this;
    }


}

package com.type2labs.undersea.common;

import com.type2labs.undersea.common.visualiser.VisualiserClient;

public class UnderseaRuntimeConfig {

    private VisualiserClient visualiser;

    public UnderseaRuntimeConfig setVisualiser(VisualiserClient visualiser){
        this.visualiser = visualiser;

        return this;
    }


}

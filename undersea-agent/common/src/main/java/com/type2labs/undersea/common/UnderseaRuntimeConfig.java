package com.type2labs.undersea.common;

import com.type2labs.undersea.common.visualiser.Visualiser;

public class UnderseaRuntimeConfig {

    private Visualiser visualiser;

    public UnderseaRuntimeConfig setVisualiser(Visualiser visualiser){
        this.visualiser = visualiser;

        return this;
    }


}

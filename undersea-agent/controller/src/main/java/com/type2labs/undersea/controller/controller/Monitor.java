package com.type2labs.undersea.controller.controller;

public abstract class Monitor {

    private final Knowledge knowledge;

    public Monitor(Knowledge knowledge) {
        this.knowledge = knowledge;
    }


    public abstract void run();

    public Knowledge getKnowledge() {
        return knowledge;
    }
}

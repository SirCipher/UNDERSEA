package com.type2labs.undersea.controller.controller;

public abstract class Analyser {

    private final Knowledge knowledge;

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public Analyser(Knowledge knowledge) {
        this.knowledge = knowledge;
    }


    public abstract void run();
}

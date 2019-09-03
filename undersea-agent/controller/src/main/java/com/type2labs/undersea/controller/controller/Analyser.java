package com.type2labs.undersea.controller.controller;

public abstract class Analyser {

    private final Knowledge knowledge;

    public Analyser(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public abstract void run();
}

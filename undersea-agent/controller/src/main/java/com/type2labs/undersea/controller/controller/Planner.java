package com.type2labs.undersea.controller.controller;

public abstract class Planner {

    private final Knowledge knowledge;

    public Planner(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public abstract void run();
}

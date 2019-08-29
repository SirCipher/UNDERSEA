package com.type2labs.undersea.controller.controller;

public abstract class Planner {

    private final Knowledge knowledge;

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public Planner(Knowledge knowledge) {
        this.knowledge = knowledge;
    }


    public abstract void run();
}

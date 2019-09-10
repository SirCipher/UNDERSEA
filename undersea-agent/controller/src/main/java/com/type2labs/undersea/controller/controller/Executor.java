package com.type2labs.undersea.controller.controller;

public abstract class Executor {

    private final Knowledge knowledge;
    protected String command;

    public Executor(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    /**
     * Get the command:
     * it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
     */
    public String getCommand() {
        return command;
    }

    /**
     * Construct command:
     * it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
     */
    public abstract void run();
}

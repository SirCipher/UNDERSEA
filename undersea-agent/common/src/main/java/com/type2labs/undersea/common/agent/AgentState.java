package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.service.ServiceManager;

public class AgentState {

    private State state = State.OFFLINE;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        /**
         * The {@link Agent} is online and active
         */
        ACTIVE,

        /**
         * The {@link Agent} is online and is not performing any tasks
         */
        INACTIVE,

        /**
         * The {@link Agent} is offline either shutdown gracefully or is yet to start
         */
        OFFLINE,

        /**
         * The {@link Agent} is offline due to a failure. This must align with {@link ServiceManager#isHealthy()} being
         * false
         */
        FAILED,

        /**
         * Denotes that this agent is a reserve
         */
        BACKUP
    }
}

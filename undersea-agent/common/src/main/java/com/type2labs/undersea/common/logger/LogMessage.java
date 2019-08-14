package com.type2labs.undersea.common.logger;

import java.io.Serializable;

public class LogMessage implements Serializable {

    private static final long serialVersionUID = 9070444023024621142L;
    private final String agentName;
    private final Object message;

    public String getAgentName() {
        return agentName;
    }

    public Object getMessage() {
        return message;
    }

    public LogMessage(String agentName, Object message) {
        this.agentName = agentName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "agentName='" + agentName + '\'' +
                ", message=" + message +
                '}';
    }
}

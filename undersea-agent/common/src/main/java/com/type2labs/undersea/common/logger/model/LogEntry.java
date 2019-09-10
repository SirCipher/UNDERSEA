package com.type2labs.undersea.common.logger.model;

import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.AgentService;

public class LogEntry {

    private int term;
    private PeerId leader;
    private Object data;
    private Object value;
    private AgentService agentService;

    public LogEntry(PeerId leader, Object data, Object value, int term, AgentService agentService) {
        this.leader = leader;
        this.data = data;
        this.term = term;

        if (value != null) {
            this.value = value.toString();
        }

        this.agentService = agentService;
    }

    public static Class<? extends AgentService> forName(String className) {
        switch (className.toUpperCase()) {
            case "MISSIONMANAGER":
                return MissionManager.class;
            case "CONSENSUSALGORITHM":
                return ConsensusAlgorithm.class;
            default:
                throw new IllegalArgumentException(className);
        }
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "term=" + term +
                ", data=" + data +
                ", value=" + value +
                ", agentService=" + agentService +
                '}';
    }

    public Object getValue() {
        return value;
    }

    public PeerId getLeader() {
        return leader;
    }

    public int getTerm() {
        return term;
    }

    public Object getData() {
        return data;
    }

    public AgentService getAgentService() {
        return agentService;
    }
}

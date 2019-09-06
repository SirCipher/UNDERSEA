package com.type2labs.undersea.common.logger.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.AgentService;

public class LogEntry {

    private final int term;
    private final Object data;
    private final Object value;
    private final AgentService agentService;

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

    public LogEntry(Object data, Object value, int term, AgentService agentService) {
        this.data = data;
        this.term = term;
        this.value = value.toString();
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

    public int getTerm() {
        return term;
    }

    public String mapObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getData() {
        return data;
    }

    public AgentService getAgentService() {
        return agentService;
    }
}

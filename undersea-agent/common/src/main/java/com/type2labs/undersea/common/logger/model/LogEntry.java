package com.type2labs.undersea.common.logger.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.AgentService;

public class LogEntry {

    private final int term;
    private final Object message;
    private final AgentService agentService;

    public LogEntry(Object message, int term, AgentService agentService) {
        this.message = message;
        this.term = term;
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
                ", message=" + message +
                ", agentService=" + agentService +
                '}';
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

    public Object getMessage() {
        return message;
    }

    public AgentService getAgentService() {
        return agentService;
    }
}

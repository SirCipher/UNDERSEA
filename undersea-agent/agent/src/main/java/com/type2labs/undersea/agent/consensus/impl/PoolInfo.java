package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.model.Endpoint;

import java.util.HashMap;
import java.util.Map;

public class PoolInfo {

    private Map<Endpoint, AgentInfo> agentInfo = new HashMap<>();

    public void setAgentInformation(Endpoint endpoint, AgentInfo agent) {
        this.agentInfo.put(endpoint, agent);
    }

    public static class AgentInfo {
        private int noSensors;
        private double remainingBattery;
        private double range;

        public AgentInfo(int noSensors, double remainingBattery, double range) {
            this.noSensors = noSensors;
            this.remainingBattery = remainingBattery;
            this.range = range;
        }

        @Override
        public String toString() {
            return "AgentInfo{" +
                    "noSensors=" + noSensors +
                    ", remainingBattery=" + remainingBattery +
                    ", range=" + range +
                    '}';
        }
    }

    public boolean hasInfo() {
        return agentInfo.size() > 0;
    }

    public Map<Endpoint, AgentInfo> getAgentInfo() {
        return agentInfo;
    }
}

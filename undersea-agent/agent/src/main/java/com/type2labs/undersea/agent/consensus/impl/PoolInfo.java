package com.type2labs.undersea.agent.consensus.impl;

import com.type2labs.undersea.agent.consensus.CostConfigurationImpl;
import com.type2labs.undersea.agent.consensus.RaftClusterConfig;
import com.type2labs.undersea.agent.consensus.RaftProtos;
import com.type2labs.undersea.agent.consensus.model.CostCalculator;
import com.type2labs.undersea.agent.consensus.model.Endpoint;
import com.type2labs.undersea.agent.consensus.model.RaftNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: This whole system needs refactoring as it's far too fragile
public class PoolInfo implements CostCalculator {

    private Map<Endpoint, AgentInfo> agentInfo = new HashMap<>();

    public void setAgentInformation(Endpoint endpoint, AgentInfo agent) {
        this.agentInfo.put(endpoint, agent);
    }

    @Override
    public Map<Endpoint, Double> generate(RaftNode parent) {
        RaftClusterConfig clusterConfig = parent.config();
        CostConfigurationImpl costConfiguration = (CostConfigurationImpl) clusterConfig.getCostConfiguration();

        double accuracyWeighting = costConfiguration.getAccuracyWeighting();
        double speedWeighting = costConfiguration.getSpeedWeighting();

        Map<Endpoint, Double> costs = new HashMap<>(agentInfo.size());

        for (Map.Entry<Endpoint, AgentInfo> e : agentInfo.entrySet()) {
            AgentInfo agentInfo = e.getValue();
            double cost = ((agentInfo.accuracy * accuracyWeighting) + (agentInfo.remainingBattery * speedWeighting)) / agentInfo.range;

            costs.put(e.getKey(), cost);
        }

        return costs;
    }

    public static class AgentInfo {
        private double speed;
        private double remainingBattery;
        private double range;
        private double accuracy;

        public AgentInfo(List<RaftProtos.Tuple> statusList) {
            setFields(statusList);
        }

        private void setFields(List<RaftProtos.Tuple> statusList) {
            for (RaftProtos.Tuple tuple : statusList) {
                switch (tuple.getFieldType()) {
                    case "speed":
                        speed = Double.parseDouble(tuple.getValue());
                        break;
                    case "remainingBattery":
                        remainingBattery = Double.parseDouble(tuple.getValue());
                        break;
                    case "range":
                        range = Double.parseDouble(tuple.getValue());
                        break;
                    case "accuracy":
                        accuracy = Double.parseDouble(tuple.getValue());
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown field type: " + tuple.getFieldType());
                }
            }
        }

        @Override
        public String toString() {
            return "AgentInfo{" +
                    "speed=" + speed +
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

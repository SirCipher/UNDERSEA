package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.CostConfigurationImpl;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.CostCalculator;
import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.RaftNode;

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
    public Map<Endpoint, Double> generateCost(RaftNode parent) {
        RaftClusterConfig clusterConfig = parent.config();
        CostConfigurationImpl costConfiguration = (CostConfigurationImpl) clusterConfig.getCostConfiguration();

        double accuracyWeighting = costConfiguration.getAccuracyWeighting();
        double speedWeighting = costConfiguration.getSpeedWeighting();

        Map<Endpoint, Double> costs = new HashMap<>(agentInfo.size());

        for (Map.Entry<Endpoint, AgentInfo> e : agentInfo.entrySet()) {
            AgentInfo agentInfo = e.getValue();
            double cost = ((agentInfo.accuracy * accuracyWeighting)
                    + (agentInfo.remainingBattery * speedWeighting))
                    / agentInfo.range;

            costs.put(e.getKey(), cost);
        }

        return costs;
    }

    public boolean hasInfo() {
        return agentInfo.size() > 0;
    }

    public Map<Endpoint, AgentInfo> getAgentInfo() {
        return agentInfo;
    }

    public static class AgentInfo {
        private double speed;
        private double remainingBattery;
        private double range;
        private double accuracy;
        private Endpoint endpoint;

        public AgentInfo(Endpoint endpoint, List<RaftProtos.Tuple> statusList) {
            this.endpoint = endpoint;
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
                    ", accuracy=" + accuracy +
                    ", endpoint=" + endpoint +
                    '}';
        }
    }
}

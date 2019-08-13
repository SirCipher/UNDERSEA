package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.common.Endpoint;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// TODO: This whole system needs refactoring as it's far too fragile
public class PoolInfo {

    private Map<Endpoint, AgentInfo> agentInfo = new HashMap<>();
    private RaftNode parent;
    private Map<Endpoint, Double> poolCosts;

    public PoolInfo(RaftNode parent) {
        this.parent = parent;
    }

    public void setAgentInformation(Endpoint endpoint, AgentInfo agent) {
        this.agentInfo.put(endpoint, agent);
    }

    public boolean hasInfo() {
        return agentInfo.size() > 0;
    }

    public Map<Endpoint, AgentInfo> getMembers() {
        return agentInfo;
    }

    public Pair<Endpoint, Double> getLowestCost() {
        if (poolCosts == null) {
            RaftClusterConfig config = parent.config();
            this.poolCosts = config.getCostCalculator().generateCosts(parent);
        }

        Entry<Endpoint, Double> min = Collections.min(poolCosts.entrySet(), Entry.comparingByValue());
        return Pair.of(min.getKey(), min.getValue());
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

        public double getSpeed() {
            return speed;
        }

        public double getRemainingBattery() {
            return remainingBattery;
        }

        public double getRange() {
            return range;
        }

        public double getAccuracy() {
            return accuracy;
        }

        public Endpoint getEndpoint() {
            return endpoint;
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

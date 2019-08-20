package com.type2labs.undersea.prospect.impl;

import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.Map.Entry;

// TODO: This whole system needs refactoring as it's far too fragile
public class ClusterState {

    private final Map<Client, ClientState> clusterCosts;
    //    private Map<Client, Double> clientCosts;
    private final RaftNode parent;
    private final int term;
    private boolean calculatedCosts = false;

    public ClusterState(RaftNode parent, int term) {
        this.parent = parent;
        this.term = term;
        this.clusterCosts = new HashMap<>();
    }

    private void addSelf() {
        Client self = RaftClientImpl.ofSelf(parent);
        ClientState agentInfo = new ClientState(self);
        List<Pair<String, String>> status = parent.agent().status();

        for (Pair<String, String> pair : status) {
            agentInfo.setField(pair.getKey(), pair.getValue());
        }

        this.clusterCosts.put(self, agentInfo);
    }

    public void setAgentInformation(Client client, ClientState agent) {
        this.clusterCosts.put(client, agent);
    }

    public Map<Client, ClientState> getMembers() {
        return clusterCosts;
    }

    public Pair<Client, ClientState> getNominee() {
        if (!calculatedCosts) {
            addSelf();
            RaftClusterConfig config = parent.config();
            config.getCostCalculator().generateCosts(this);
            calculatedCosts = true;
        }

        Optional<Entry<Client, ClientState>> min =
                clusterCosts.entrySet().stream().min(Comparator.comparing(e -> e.getValue().getCost()));

        if (min.isPresent()) {
            Entry<Client, ClientState> var = min.get();
            return Pair.of(var.getKey(), var.getValue());
        } else {
            throw new RuntimeException("Minimum value not found");
        }

    }

    public static class ClientState {
        private final Client client;

        private double speed;
        private double remainingBattery;
        private double range;
        private double accuracy;
        private double cost;
        private boolean reachable = true;

        private ClientState(Client client) {
            this.client = client;
        }

        public ClientState(Client client, boolean reachable) {
            this.client = client;
            this.reachable = reachable;
        }

        public ClientState(Client client, List<RaftProtos.Tuple> statusList) {
            this.client = client;
            setFields(statusList);
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public boolean isReachable() {
            return reachable;
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

        public Client getClient() {
            return client;
        }

        private void setField(String field, String value) {
            switch (field) {
                case "speed":
                    speed = Double.parseDouble(value);
                    break;
                case "remainingBattery":
                    remainingBattery = Double.parseDouble(value);
                    break;
                case "range":
                    range = Double.parseDouble(value);
                    break;
                case "accuracy":
                    accuracy = Double.parseDouble(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field type: " + field);
            }
        }

        private void setFields(List<RaftProtos.Tuple> statusList) {
            for (RaftProtos.Tuple tuple : statusList) {
                setField(tuple.getFieldType(), tuple.getValue());
            }
        }

        @Override
        public String toString() {
            return "AgentInfo{" +
                    "speed=" + speed +
                    ", remainingBattery=" + remainingBattery +
                    ", range=" + range +
                    ", accuracy=" + accuracy +
                    ", endpoint=" + client +
                    '}';
        }
    }
}

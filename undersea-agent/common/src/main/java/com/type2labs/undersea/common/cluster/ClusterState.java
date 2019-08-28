package com.type2labs.undersea.common.cluster;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.Map.Entry;

// TODO: This whole system needs refactoring as it's far too fragile
public class ClusterState {

    private final Map<Client, ClientState> clusterCosts;
    private final ConsensusAlgorithm associatedAlg;
    private final int term;
    private boolean calculatedCosts = false;

    public ClusterState(ConsensusAlgorithm associatedAlg, int term) {
        this.associatedAlg = associatedAlg;
        this.term = term;
        this.clusterCosts = new HashMap<>();
    }

    public void addSelf(Client self) {
        ClientState agentInfo = new ClientState(self);
        List<Pair<String, String>> status = associatedAlg.parent().status();

        for (Pair<String, String> pair : status) {
            agentInfo.setField(pair.getKey(), pair.getValue());
        }

        this.clusterCosts.put(self, agentInfo);
    }

    public void setAgentInformation(Client client, ClientState agent) {
        this.clusterCosts.put(client, agent);
    }

    public ClientState getClientState(Client client) {
        return clusterCosts.get(client);
    }

    public Map<Client, ClientState> getMembers() {
        return clusterCosts;
    }

    public Pair<Client, ClientState> getNominee(Client self) {
        if (!calculatedCosts) {
            addSelf(self);
            associatedAlg.parent().config().getCostCalculator().generateCosts(this);
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

        public ClientState(Client client, List<Pair<String, String>> statusList) {
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

        private void setFields(List<Pair<String, String>> statusList) {
            for (Pair<String, String> p : statusList) {
                setField(p.getKey(), p.getValue());
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

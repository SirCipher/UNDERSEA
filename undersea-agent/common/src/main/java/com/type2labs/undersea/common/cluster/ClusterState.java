package com.type2labs.undersea.common.cluster;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.cost.CostCalculator;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

// TODO: This whole system needs refactoring as it's far too fragile
public class ClusterState {

    private final Map<Client, ClientState> clusterState;
    private final ConsensusAlgorithm associatedAlg;
    private boolean calculatedCosts = false;
    private int clusterSize;
    private CostCalculator costCalculator;

    public ClusterState(ConsensusAlgorithm associatedAlg, int clusterSize) {
        this.associatedAlg = associatedAlg;
        this.clusterState = new HashMap<>(clusterSize);
        this.clusterSize = clusterSize;
        this.costCalculator = associatedAlg.parent().config().getCostCalculator();
    }

    private void addSelf(Client self) {
        ClientState agentInfo = new ClientState(self);
        agentInfo.cost = associatedAlg.parent().services().getService(SubsystemMonitor.class).getCurrentCost();
        this.clusterState.put(self, agentInfo);
    }

    public void setAgentInformation(Client client, ClientState agent) {
        this.clusterState.put(client, agent);
    }

    public ClientState getClientState(Client client) {
        return clusterState.get(client);
    }

    public Map<Client, ClientState> getMembers() {
        return clusterState;
    }

    public boolean heardFromAllNodes() {
        return clusterSize == clusterState.size();
    }

    public Pair<Client, ClientState> getNominee(Client self) {
        if (!calculatedCosts) {
            addSelf(self);
            calculatedCosts = true;
        }

        Optional<Entry<Client, ClientState>> min =
                clusterState.entrySet().stream().min(Comparator.comparing(e -> e.getValue().getCost()));

        if (min.isPresent()) {
            Entry<Client, ClientState> var = min.get();
            return Pair.of(var.getKey(), var.getValue());
        } else {
            throw new RuntimeException("Minimum value not found");
        }

    }

    public static class ClientState {
        private final Client client;
        private double cost;
        private boolean reachable = true;

        private ClientState(Client client) {
            this.client = client;
        }

        public ClientState(Client client, double cost) {
            this.client = client;
            this.cost = cost;
        }

        public ClientState(Client client, boolean reachable) {
            this.client = client;
            this.reachable = reachable;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public Client getClient() {
            return client;
        }

        public boolean isReachable() {
            return reachable;
        }


    }
}

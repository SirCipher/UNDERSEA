/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.cluster;

import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * Maintains the state of the cluster when the {@link ConsensusAlgorithm} is in a candidate state. Storing the
 * calculated costs for all {@link Client}s that responded during the status requests.
 */
public class ClusterState {

    private final Map<Client, ClientState> clusterState;
    private final ConsensusAlgorithm associatedAlg;
    private boolean calculatedCosts = false;

    public ClusterState(ConsensusAlgorithm associatedAlg) {
        this.associatedAlg = associatedAlg;
        this.clusterState = new HashMap<>();
    }

    private void addSelf(Client self) {
        ClientState agentInfo = new ClientState(self);
        agentInfo.cost = associatedAlg.parent().serviceManager().getService(SubsystemMonitor.class).getCurrentCost();
        this.clusterState.put(self, agentInfo);
    }

    public void setAgentInformation(Client client, ClientState agent) {
        this.clusterState.put(client, agent);
    }

    public Map<Client, ClientState> getMembers() {
        return clusterState;
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

    /**
     * Stores the associated cost of the client
     */
    public static class ClientState {
        private final Client client;
        private double cost;

        private ClientState(Client client) {
            this.client = client;
        }

        public ClientState(Client client, double cost) {
            this.client = client;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "ClientState{" +
                    "client=" + client +
                    ", cost=" + cost +
                    '}';
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

    }
}

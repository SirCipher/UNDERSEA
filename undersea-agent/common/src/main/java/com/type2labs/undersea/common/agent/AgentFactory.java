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

package com.type2labs.undersea.common.agent;


import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.utilities.factory.AbstractFactory;
import org.apache.commons.lang3.NotImplementedException;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory for creating agents. Largely used for test classes, however, it allows for quick construction of agents.
 */
public class AgentFactory implements AbstractFactory<Agent> {

    private int count = 0;

    public void populateCluster(Agent agent, List<Agent> peers) {
        for (Agent peer : peers) {
            PeerId peerId = PeerId.newId();
            agent.clusterClients().put(peerId, new Client() {
                @Override
                public String name() {
                    return peerId.toString();
                }

                @Override
                public ClusterState.ClientState state() {
                    return new ClusterState.ClientState(this, new Random().nextInt(100));
                }

                @Override
                public PeerId peerId() {
                    return peerId;
                }

                @Override
                public InetSocketAddress socketAddress() {
                    return new InetSocketAddress(0);
                }

                @Override
                public void shutdown() {

                }

                @Override
                public boolean isSelf() {
                    return false;
                }
            });
        }
    }

    @Override
    public Agent get(String name) {
        throw new NotImplementedException("Get agent not supported at present");
    }

    public Agent createWith(RuntimeConfig config, String name, ServiceManager serviceManager) {
        return new AbstractAgent(config, name, serviceManager, PeerId.newId()) {
        };
    }

    public Agent createWith(RuntimeConfig config) {
        String name = "test:" + count++;
        ServiceManager serviceManager = new ServiceManager();

        return new AbstractAgent(config, name, serviceManager, PeerId.newId()) {
        };
    }

    @Override
    public Agent create() {
        String name = "test-" + count++;
        ServiceManager serviceManager = new ServiceManager();

        return new AbstractAgent(new RuntimeConfig(), name, serviceManager, PeerId.newId()) {
        };
    }

    @Override
    public List<Agent> createN(int n) {
        List<Agent> dslAgents = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            dslAgents.add(create());
        }

        return dslAgents;
    }

}

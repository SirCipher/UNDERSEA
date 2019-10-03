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

package com.type2labs.undersea.prospect.impl;

import com.google.common.collect.Sets;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithmRole;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.missions.planner.impl.NoMissionManager;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.impl.RaftClientImpl;
import com.type2labs.undersea.utilities.executor.ExecutorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class LocalAgentGroup implements Closeable {

    private static final Logger logger = LogManager.getLogger(LocalAgentGroup.class);

    private ExecutorService executorService;

    private final List<RaftNodeImpl> raftNodes;
    private final List<Agent> agents = new ArrayList<>();
    private final Client[] clients;

    public LocalAgentGroup(int size, Set<Class<? extends AgentService>> services, boolean withVisualiser,
                           boolean withCallbacks) {
        executorService = ExecutorUtils.newExecutor(size, "%d");

        raftNodes = new ArrayList<>(size);
        clients = new RaftClientImpl[size];

        RaftClusterConfig config = defaultConfig();
        AgentFactory agentFactory = new AgentFactory();

        for (int i = 0; i < size; i++) {
            String name = "agent:" + i;
            RaftNodeImpl raftNode = new RaftNodeImpl(
                    config,
                    new InetSocketAddress("localhost", 0)
            );

            ServiceManager serviceManager = new ServiceManager();

            Agent agent = agentFactory.createWith(config.getRuntimeConfig(), name, serviceManager);

            serviceManager.registerService(raftNode);

            for (Class<? extends AgentService> clazz : services) {
                AgentService agentService;
                try {
                    agentService = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to load class: " + clazz.getSimpleName());
                }

                serviceManager.registerService(agentService);
            }

            serviceManager.registerService(new LogServiceImpl());

            if (withCallbacks) {
                raftNode.registerCallback(DefaultServiceCallbacks.defaultMissionCallback(agent, raftNode, config));
            }

            if (withVisualiser) {
                SubsystemMonitor subsystemMonitor = new SubsystemMonitorSpoofer();
                VisualiserClient client = new VisualiserClientImpl();
                subsystemMonitor.setVisualiser(client);
                client.initialise(agent);

                serviceManager.registerService(subsystemMonitor);
            }

            raftNode.initialise(agent);
            raftNode.run();

            raftNodes.add(raftNode);
            agents.add(agent);
        }

//        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
//        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");
//
//        MissionParameters missionParameters = new MissionParametersImpl(0, area, 40);
//        config.getUnderseaRuntimeConfig().missionParameters(missionParameters);
    }

    private LocalAgentGroup(int size, Set<Class<? extends AgentService>> services) {
        this(size, services, false, false);
    }

    public LocalAgentGroup(int size) {
        this(size, Sets.newHashSet(SubsystemMonitorSpoofer.class, NoMissionManager.class));
    }

    @Override
    public void close() {
        logger.info("Shutting down local agent group");

        for (RaftNodeImpl node : raftNodes) {
            node.parent().shutdown();
        }

        executorService.shutdown();
    }

    private RaftClusterConfig defaultConfig() {
        RuntimeConfig underseaConfig = new RuntimeConfig();
        RaftClusterConfig config = new RaftClusterConfig(underseaConfig);

        CostConfiguration costConfiguration = new CostConfiguration();
        costConfiguration.setBias("ACCURACY", 30.0);
        costConfiguration.setBias("SPEED", 5.0);

        underseaConfig.setCostConfiguration(costConfiguration);

        return config;
    }

    public void doManualDiscovery() {
        for (RaftNodeImpl raftNode : raftNodes) {
            for (int j = raftNodes.size() - 1; j >= 0; j--) {
                RaftNode nodeB = raftNodes.get(j);

                if (raftNode != nodeB) {
                    raftNode.state().discoverNode(nodeB);
                }
            }
        }

        System.out.println();
    }

    public RaftNode getLeaderNode() {
        int count = 0;
        RaftNode match = null;

        for (RaftNodeImpl raftNode : raftNodes) {
            if (raftNode.raftRole() == ConsensusAlgorithmRole.LEADER) {
                match = raftNode;
                count++;
            }
        }

        if (count == 1) {
            return match;
        } else if (count > 1) {
            throw new RuntimeException("More than one leader elected");
        } else {
            return null;
        }
    }

    public List<RaftNodeImpl> getRaftNodes() {
        return raftNodes;
    }

    public void start() {
        for (RaftNodeImpl node : raftNodes) {
            ServiceManager serviceManager = node.parent().serviceManager();

            for (AgentService agentService : serviceManager.getServices()) {
                if (RaftNode.class.isAssignableFrom(agentService.getClass())) {
                    continue;
                } else {
                    executorService.submit(() -> serviceManager.startService(agentService.getClass()));
                }
            }
        }
    }

    public void removeNode(RaftNodeImpl leaderNode) {
        raftNodes.remove(leaderNode);
    }
}

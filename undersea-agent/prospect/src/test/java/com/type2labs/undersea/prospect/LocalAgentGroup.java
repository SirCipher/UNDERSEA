package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.missionplanner.NoMissionPlanner;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.impl.ClusterState;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;

class LocalAgentGroup implements Closeable {

    private static final Logger logger = LogManager.getLogger(LocalAgentGroup.class);

    private final RaftNodeImpl[] raftNodes;
    private final Client[] clients;
    private final RaftIntegrationImpl[] integrations;

    LocalAgentGroup(int size) {
        raftNodes = new RaftNodeImpl[size];
        clients = new RaftClientImpl[size];
        integrations = new RaftIntegrationImpl[size];

        RaftClusterConfig config = defaultConfig();
        AgentFactory agentFactory = new AgentFactory();

        for (int i = 0; i < size; i++) {
            String name = "agent:" + i;
            RaftIntegrationImpl integration = new RaftIntegrationImpl("endpoint:" + i);
            integrations[i] = integration;
            RaftNodeImpl raftNode = new RaftNodeImpl(
                    config,
                    "agent:" + i,
                    integration,
                    new InetSocketAddress("localhost", 0),
                    PeerId.newId()
            );

            Monitor monitor = new MonitorImpl();
            ServiceManager serviceManager = new ServiceManager();

            Agent agent = agentFactory.createWith(config.getUnderseaRuntimeConfig(), name, serviceManager,
                    new AgentStatus(name, new ArrayList<>()));

            serviceManager.registerService(new NoMissionPlanner());
            raftNode.initialise(agent);

            serviceManager.registerService(monitor);
            serviceManager.startServices();

            raftNodes[i] = raftNode;
        }
    }

    public RaftNodeImpl[] getRaftNodes() {
        return raftNodes;
    }

    private RaftClusterConfig defaultConfig() {
        UnderseaRuntimeConfig underseaConfig = new UnderseaRuntimeConfig();
        RaftClusterConfig config = new RaftClusterConfig(underseaConfig);

        CostConfiguration costConfiguration = new CostConfigurationImpl();
        costConfiguration.setCostCalculator((ClusterState clusterState) -> {
            double accuracyWeighting = (double) costConfiguration.getBias("ACCURACY");
            double speedWeighting = (double) costConfiguration.getBias("SPEED");

            for (Map.Entry<Client, ClusterState.ClientState> e : clusterState.getMembers().entrySet()) {
                ClusterState.ClientState a = e.getValue();
                if (!a.isReachable()) {
                    continue;
                }

                double cost = ((a.getAccuracy() * accuracyWeighting)
                        + (a.getRemainingBattery() * speedWeighting))
                        / a.getRange();

                e.getValue().setCost(cost);
            }
        });

        costConfiguration.setBias("ACCURACY", 30.0);
        costConfiguration.setBias("SPEED", 5.0);

        config.setCostConfiguration(costConfiguration);

        return config;
    }


    void start() {
        for (RaftNodeImpl node : raftNodes) {
            node.run();
        }
    }

    void doManualDiscovery() {
        for (RaftNodeImpl raftNode : raftNodes) {
            for (int j = raftNodes.length - 1; j >= 0; j--) {
                RaftNode nodeB = raftNodes[j];

                if (raftNode != nodeB) {
                    raftNode.state().discoverNode(nodeB);
                }
            }
        }
    }

    // TODO
    public RaftNode getLeaderNode() {
        return raftNodes[0];
    }

    @Override
    public void close() {
        logger.info("Shutting down local agent group");

        for (RaftNodeImpl node : raftNodes) {
            node.shutdown();
            node.agent().shutdown();
        }
    }
}

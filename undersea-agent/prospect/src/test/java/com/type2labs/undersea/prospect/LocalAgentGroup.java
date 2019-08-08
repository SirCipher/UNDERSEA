package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.impl.*;
import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

class LocalAgentGroup {

    private static final Logger logger = LogManager.getLogger(LocalAgentGroup.class);

    private final RaftNodeImpl[] raftNodes;
    private final Endpoint[] endpoints;
    private final RaftIntegrationImpl[] integrations;

    LocalAgentGroup(int size) {
        raftNodes = new RaftNodeImpl[size];
        endpoints = new EndpointImpl[size];
        integrations = new RaftIntegrationImpl[size];

        RaftClusterConfig config = defaultConfig();

        for (int i = 0; i < size; i++) {
            Endpoint endpoint = new EndpointImpl("endpoint:" + i, new InetSocketAddress("localhost", 5000 + i));
            endpoints[i] = endpoint;
            RaftIntegrationImpl integration = new RaftIntegrationImpl("endpoint:" + i, endpoint);
            integrations[i] = integration;
            RaftNodeImpl node = new RaftNodeImpl(config, "agent:" + i, endpoint, integration);
            node.setAgent(new AgentImpl());
            raftNodes[i] = node;

        }
    }

    public RaftNodeImpl[] getRaftNodes() {
        return raftNodes;
    }

    private RaftClusterConfig defaultConfig() {
        RaftClusterConfig config = new RaftClusterConfig();

        CostConfigurationImpl costConfiguration = new CostConfigurationImpl();
        costConfiguration.withAccuracyWeighting(30);
        costConfiguration.withSpeedWeighting(5);
        config.setCostConfiguration(costConfiguration);

        return config;
    }

    void shutdown() {
        for (RaftNodeImpl node : raftNodes) {
            node.shutdown();
        }
    }

    void start() {
        for (RaftNodeImpl node : raftNodes) {
            node.start();
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

}

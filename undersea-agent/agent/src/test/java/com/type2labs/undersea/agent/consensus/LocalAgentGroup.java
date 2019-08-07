package com.type2labs.undersea.agent.consensus;

import com.type2labs.undersea.agent.consensus.impl.*;
import com.type2labs.undersea.agent.consensus.model.Endpoint;
import com.type2labs.undersea.agent.consensus.model.GroupId;
import com.type2labs.undersea.agent.consensus.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

class LocalAgentGroup {

    private static final Logger logger = LogManager.getLogger(LocalAgentGroup.class);

    private final GroupId groupId;
    private final RaftNodeImpl[] raftNodes;
    private final Endpoint[] endpoints;
    private final RaftIntegrationImpl[] integrations;

    LocalAgentGroup(int size) {
        groupId = new GroupIdImpl("test", "testuuid");
        raftNodes = new RaftNodeImpl[size];
        endpoints = new EndpointImpl[size];
        integrations = new RaftIntegrationImpl[size];

        RaftClusterConfig config = defaultConfig();

        for (int i = 0; i < size; i++) {
            Endpoint endpoint = new EndpointImpl("endpoint:" + i, new InetSocketAddress("localhost", 5000 + i));
            endpoints[i] = endpoint;
            RaftIntegrationImpl integration = new RaftIntegrationImpl("endpoint:" + i, endpoint);
            integrations[i] = integration;
            raftNodes[i] = new RaftNodeImpl(config, new AgentImpl(), "agent:" + i, endpoint, groupId, integration);
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

    void initDiscovery() {
        for (RaftIntegrationImpl integration : integrations) {
            for (RaftNodeImpl node : raftNodes) {
                if (!node.getLocalEndpoint().equals(integration.getLocalEndpoint())) {
                    integration.discoverNode(node);
                }
            }
        }
    }

    // TODO
    public RaftNode getLeaderNode() {
        return raftNodes[0];
    }
}

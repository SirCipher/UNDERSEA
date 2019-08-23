package com.type2labs.undersea.prospect.impl;

import com.google.common.collect.Sets;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.cost.CostConfigurationImpl;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.impl.NoMissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.monitor.impl.MonitorImpl;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClientImpl;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.*;

public class LocalAgentGroup implements Closeable {

    private static final Logger logger = LogManager.getLogger(LocalAgentGroup.class);

    private final RaftNodeImpl[] raftNodes;
    private final List<Agent> agents = new ArrayList<>();
    private final Client[] clients;
    private final RaftIntegrationImpl[] integrations;

    public LocalAgentGroup(int size, Set<AgentService> services, boolean withVisualiser) {
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

            ServiceManager serviceManager = new ServiceManager();

            Agent agent = agentFactory.createWith(config.getUnderseaRuntimeConfig(), name, serviceManager,
                    new AgentStatus(name, new ArrayList<>()));

            serviceManager.registerService(raftNode);
            serviceManager.registerServices(services);

            if (withVisualiser) {
                Monitor monitor = new MonitorImpl();
                VisualiserClient client = new VisualiserClientImpl();
                monitor.setVisualiser(client);
                client.initialise(agent);

                serviceManager.registerService(monitor);
            }

            raftNode.initialise(agent);

            raftNodes[i] = raftNode;
            agents.add(agent);
        }

        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        MissionParameters missionParameters = new MissionParametersImpl(0, area, 40);
        config.getUnderseaRuntimeConfig().missionParameters(missionParameters);
    }

    public LocalAgentGroup(int size, Set<AgentService> services) {
        this(size, services, false);
    }

    public LocalAgentGroup(int size) {
        this(size, Sets.newHashSet(new MonitorImpl(), new NoMissionManager()));
    }

    @Override
    public void close() {
        logger.info("Shutting down local agent group");

        for (RaftNodeImpl node : raftNodes) {
            node.shutdown();
            node.agent().shutdown();
        }
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

        underseaConfig.setCostConfiguration(costConfiguration);

        return config;
    }

    public void doManualDiscovery() {
        for (RaftNodeImpl raftNode : raftNodes) {
            for (int j = raftNodes.length - 1; j >= 0; j--) {
                RaftNode nodeB = raftNodes[j];

                if (raftNode != nodeB) {
                    raftNode.state().discoverNode(nodeB);
                }
            }

            System.out.println(raftNode.parent().clusterClients().size());
        }
    }

    // TODO
    public RaftNode getLeaderNode() {
        return raftNodes[0];
    }

    public RaftNodeImpl[] getRaftNodes() {
        return raftNodes;
    }

    public void start() {
        for (RaftNodeImpl node : raftNodes) {
            node.parent().services().startServices();
        }
    }
}

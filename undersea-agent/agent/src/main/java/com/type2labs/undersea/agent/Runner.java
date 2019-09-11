package com.type2labs.undersea.agent;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentState;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.cost.CostConfigurationImpl;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.runner.AbstractRunner;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.impl.MultiRoleLeaderClientImpl;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Entry point of UNDERSEA application
 */
public class Runner extends AbstractRunner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private ParserEngine parserEngine;
    private AgentInitialiserImpl agentInitialiser;

    public Runner(String configurationFileLocation) {
        super(configurationFileLocation, new AgentInitialiserImpl(defaultConfig(configurationFileLocation)));

        this.agentInitialiser = (AgentInitialiserImpl) super.getAgentInitialiser();
    }

    private static RaftClusterConfig defaultConfig(String configurationFileLocation) {
        UnderseaRuntimeConfig underseaRuntimeConfig = new UnderseaRuntimeConfig();
        RaftClusterConfig raftClusterConfig = new RaftClusterConfig(underseaRuntimeConfig);

        CostConfiguration costConfiguration = new CostConfigurationImpl();
        costConfiguration.setCostCalculator((ClusterState clusterState) -> {
            double accuracyWeighting = (double) costConfiguration.getBias("ACCURACY");
            double speedWeighting = (double) costConfiguration.getBias("SPEED");

            for (Map.Entry<Client, ClusterState.ClientState> e : clusterState.getMembers().entrySet()) {
                ClusterState.ClientState a = e.getValue();
                if (!a.isReachable()) {
                    continue;
                }

//                double cost = ((a.getAccuracy() * accuracyWeighting)
//                        + (a.getRemainingBattery() * speedWeighting))
//                        / a.getRange();

                e.getValue().setCost(0);
            }
        });

        costConfiguration.setBias("ACCURACY", 30.0);
        costConfiguration.setBias("SPEED", 5.0);

        underseaRuntimeConfig.setCostConfiguration(costConfiguration);

        Properties properties = Utility.getPropertiesByName(configurationFileLocation);
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        MissionParameters missionParameters = new MissionParametersImpl(0, area, 40);
        underseaRuntimeConfig.missionParameters(missionParameters);

        underseaRuntimeConfig.enableVisualiser(true);

        return raftClusterConfig;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Configuration file must be provided");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(Utility::killMoos));

        Runner runner = new Runner(args[0]);
        runner.setup();
        runner.onParsed(args[0]);
    }

    @SuppressWarnings("DuplicatedCode")
    public void onParsed(String args) throws InterruptedException {
        Agent shoreside = null;

        for (Agent agent : super.getAgents()) {
            if (agent.name().equals("shoreside")) {
                shoreside = agent;
            }
        }

        if (shoreside == null) {
            throw new UnderseaException("Shoreside agent not found");
        }

        RaftNode shoresideRaftNode = shoreside.services().getService(RaftNode.class);
        MultiRoleLeaderClientImpl shoresideClient = new MultiRoleLeaderClientImpl(shoresideRaftNode,
                shoresideRaftNode.server().getSocketAddress(),
                shoresideRaftNode.parent().peerId());

        super.getAgents().forEach((a) -> {
            if (a.name().equals("shoreside")) {
                RaftNode raftNode = a.services().getService(RaftNode.class);
                raftNode.multiRoleState().setLeader(shoresideClient);
            }
        });

        Properties properties = Utility.getPropertiesByName(args);
        boolean localNodeDiscovery = Boolean.parseBoolean(Utility.getProperty(properties, "config.localnodediscovery"));

        if (localNodeDiscovery) {
            for (Agent agentA : super.getAgents()) {
                while (!agentA.services().isHealthy()) {
                    Thread.sleep(500);
                }

                RaftNodeImpl raftNodeA = agentA.services().getService(RaftNodeImpl.class);

                if (raftNodeA.multiRoleState().isLeader()) {
                    continue;
                }

                raftNodeA.multiRoleState().setLeader(shoresideClient);

                if (agentA.state().getState() == AgentState.State.BACKUP) {
                    shoresideRaftNode.state().discoverNode(raftNodeA);

                    for (Agent agentB :
                            super.getAgents().stream().filter(a -> a.state().getState() == AgentState.State.BACKUP).collect(Collectors.toList())) {
                        while (!agentB.services().isHealthy()) {
                            Thread.sleep(500);
                        }

                        RaftNodeImpl raftNodeB = agentB.services().getService(RaftNodeImpl.class);
                        if (raftNodeB.multiRoleState().isLeader()) {
                            continue;
                        }

                        if (raftNodeA != raftNodeB) {
                            raftNodeA.state().discoverNode(raftNodeB);
                        }
                    }
                } else {
                    for (Agent agentB :
                            super.getAgents().stream().filter(a -> a.state().getState() != AgentState.State.BACKUP).collect(Collectors.toList())) {
                        while (!agentB.services().isHealthy()) {
                            Thread.sleep(500);
                        }


                        RaftNodeImpl raftNodeB = agentB.services().getService(RaftNodeImpl.class);
                        shoresideRaftNode.multiRoleState().remotePeers().put(agentB.peerId(), raftNodeB.self());
                        if (raftNodeB.multiRoleState().isLeader()) {
                            continue;
                        }

                        if (raftNodeA != raftNodeB) {
                            raftNodeA.state().discoverNode(raftNodeB);
                        }
                    }
                }
            }

            Thread.sleep(1000);
        }
    }

    @Override
    protected void generateFiles() {
        parserEngine.generateFiles();
    }

    @Override
    protected Map<String, DslAgentProxy> parseDsl(String configurationFileLoation) {
        Properties properties = Utility.getPropertiesByName(configurationFileLoation);
        properties.put("pwd", new File(configurationFileLoation).getAbsoluteFile().getParent());

        logger.info("Initialised " + properties.size() + " properties");

        parserEngine = new ParserEngine(properties);

        EnvironmentProperties environmentProperties;

        try {
            environmentProperties = parserEngine.parseMission();
            agentInitialiser.setEnvironmentProperties(environmentProperties);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse mission: ", e);
        }

        return environmentProperties.getAllAgents();
    }

    public boolean missionComplete() {
        boolean completed = true;

        for (Agent agent : super.getAgents()) {
            if (agent.name().equals("shoreside")) {
                continue;
            }

            MoosMissionManagerImpl missionManager = agent.services().getService(MoosMissionManagerImpl.class);
            while (!missionManager.missionHasBeenAssigned()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }

            completed &= missionManager.getAssignedTasks().stream().filter(e -> e.getTaskStatus() == TaskStatus.COMPLETED).count() == missionManager.getAssignedTasks().size();
        }

        if (completed) {
            logger.info("Mission complete");
        }

        return completed;
    }
}

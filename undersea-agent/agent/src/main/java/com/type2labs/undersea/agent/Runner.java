package com.type2labs.undersea.agent;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.cost.CostConfiguration;
import com.type2labs.undersea.common.cost.CostConfigurationImpl;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.runner.AbstractRunner;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Entry point of UNDERSEA application
 */
public class Runner extends AbstractRunner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private ParserEngine parserEngine;
    private AgentInitialiserImpl agentInitialiser;
    private static RaftClusterConfig raftClusterConfig;
    private static UnderseaRuntimeConfig underseaRuntimeConfig = new UnderseaRuntimeConfig();

    static {
        raftClusterConfig = new RaftClusterConfig(underseaRuntimeConfig);

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

        underseaRuntimeConfig.setCostConfiguration(costConfiguration);

        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        MissionParameters missionParameters = new MissionParametersImpl(0, area, 40);
        underseaRuntimeConfig.missionParameters(missionParameters);

        underseaRuntimeConfig.enableVisualiser(true);
    }

    public Runner(String configurationFileLocation) {
        super(configurationFileLocation, new AgentInitialiserImpl(raftClusterConfig));

        this.agentInitialiser = (AgentInitialiserImpl) super.getAgentInitialiser();
    }

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Configuration file must be provided");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(Utility::killMoos));

        Runner runner = new Runner(args[0]);
        runner.setup();
        runner.onParsed(args[0]);
    }

    public void onParsed(String args) {
        Properties properties = Utility.getPropertiesByName(args);
        boolean localNodeDiscovery = Boolean.parseBoolean(Utility.getProperty(properties, "config.localnodediscovery"));

        if (localNodeDiscovery) {
            for (Agent agentA : super.getAgents()) {
                RaftNodeImpl raftNodeA = agentA.services().getService(RaftNodeImpl.class);

                for (Agent agentB : super.getAgents()) {
                    RaftNodeImpl raftNodeB = agentB.services().getService(RaftNodeImpl.class);

                    if (raftNodeA != raftNodeB) {
                        raftNodeA.state().discoverNode(raftNodeB);
                    }
                }
            }
        }
    }

    @Override
    protected void generateFiles() {
        parserEngine.generateFiles();
    }

    @Override
    protected Map<String, DslAgentProxy> parseDsl(String configurationFileLoation) {
        Properties properties = Utility.getPropertiesByName(configurationFileLoation);
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

}

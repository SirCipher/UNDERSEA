package com.type2labs.undersea.runner;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.common.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.impl.AgentImpl;
import com.type2labs.undersea.common.impl.Node;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.missionplanner.PlannerException;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.missionplanner.model.MissionImpl;
import com.type2labs.undersea.missionplanner.model.MissionParametersImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Entry point of UNDERSEA application
 */
@SuppressWarnings("Duplicates")
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private static AgentInitialiser agentInitialiser =
            AgentInitialiser.getInstance(new RaftClusterConfig(new UnderseaRuntimeConfig()));
    private static EnvironmentProperties environmentProperties;
    private static Properties properties;

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("runner.properties file location must be supplied");
        }

        Runner runner = new Runner();
        runner.init(args[0]);
    }

    private static MissionImpl planMission() {
        MissionPlanner missionPlanner = new VehicleRoutingOptimiser();
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        List<AgentImpl> dslAgents = new ArrayList<>(environmentProperties.getAgents().values());
        MissionParametersImpl missionParametersImpl = new MissionParametersImpl(dslAgents, 0, area, 50);

        try {
            MissionImpl mission = (MissionImpl) missionPlanner.generate(missionParametersImpl);
            missionPlanner.print(mission);

            return mission;
        } catch (PlannerException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to plan mission", e);
        }
    }

    private void init(String propLocation) {
//        new Visualiser();

        properties = Utility.getPropertiesByName(propLocation);
        logger.info("Initialised " + properties.size() + " properties");

        ParserEngine parserEngine = new ParserEngine(properties);
        try {
            environmentProperties = parserEngine.parseMission();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse mission: ", e);
        }

        agentInitialiser.initalise(environmentProperties.getAgents());

        MissionImpl mission = planMission();
        RoutingModel routingModel = mission.getRoutingModel();
        Assignment assignment = mission.getAssignment();
        RoutingIndexManager manager = mission.getRoutingIndexManager();

        List<AgentImpl> dslAgents = new ArrayList<>(environmentProperties.getAgents().values());

        for (int i = 0; i < manager.getNumberOfVehicles(); ++i) {
            long index = routingModel.start(i);
            AgentImpl dslAgent = dslAgents.get(i);

            while (!routingModel.isEnd(index)) {
                int centroidIndex = manager.indexToNode(index);
                index = assignment.value(routingModel.nextVar(index));

                double[] centroid = mission.getMissionParametersImpl().getCentroid(centroidIndex);

                Node node = new Node(centroid[0], centroid[1]);
                dslAgent.assignNode(node);
            }

            // TODO: Change to missions instead of assigned nodes. An assigned node is a 'go to waypoint' task
            if (dslAgent.getAssignedNodes().size() == 1) {
                logger.warn("More agents than required have been assigned to the mission. Removing nodes for agent: " + dslAgent.getName());
                dslAgent.setAssignedNodes(new ArrayList<>());
            }
        }

        parserEngine.generateFiles();

        logger.info("Exiting runner...");
    }

}

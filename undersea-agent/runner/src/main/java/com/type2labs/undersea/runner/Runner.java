package com.type2labs.undersea.runner;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.missionplanner.model.MissionImpl;
import com.type2labs.undersea.missionplanner.model.MissionParametersImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.common.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.impl.AgentImpl;
import com.type2labs.undersea.common.impl.Node;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.missionplanner.PlannerException;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.visualiser.Visualiser;
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
    private static AgentInitialiser agentInitialiser = AgentInitialiser.getInstance(new RaftClusterConfig(new UnderseaRuntimeConfig()));
    private static EnvironmentProperties environmentProperties;
    private static Properties properties;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("runner.properties file location must be supplied");
        }
        Visualiser visualiser = new Visualiser();


        properties = Utility.getPropertiesByName(args[0]);
        logger.info("Initialised " + properties.size() + " properties");

        ParserEngine parserEngine = new ParserEngine(properties);
        environmentProperties = parserEngine.parseMission();

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

            if (dslAgent.getAssignedNodes().size() == 1) {
                logger.warn("More agents than required have been assigned to the mission. Removing nodes for agent: " + dslAgent.getName());
                dslAgent.setAssignedNodes(new ArrayList<>());
            }
        }

        parserEngine.generateFiles();

        agentInitialiser.initalise(environmentProperties.getAgents());

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Exiting runner...");
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

}

package com.type2labs.undersea.runner;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.mathworks.toolbox.javabuilder.MWApplication;
import com.mathworks.toolbox.javabuilder.MWMCROption;
import com.type2labs.undersea.agent.model.Agent;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.model.AgentProxy;
import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.model.node.Node;
import com.type2labs.undersea.missionplanner.planner.tsp.TspMissionPlanner;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Entry point of UNDERSEA application
 */
@SuppressWarnings("Duplicates")
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private static AgentInitialiser agentInitialiser = AgentInitialiser.getInstance();
    private static EnvironmentProperties environmentProperties;
    private static Properties properties;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("runner.properties file location must be supplied");
        }

        properties = Utility.getPropertiesByName(args[0]);
        logger.info("Initialised " + properties.size() + " properties");

        ParserEngine parserEngine = new ParserEngine(properties);
        environmentProperties = parserEngine.parseMission();

        Mission mission = planMission();
        RoutingModel routingModel = mission.getRoutingModel();
        Assignment assignment = mission.getAssignment();
        RoutingIndexManager manager = mission.getRoutingIndexManager();

        long index = routingModel.start(0);

        Agent agent = environmentProperties.getShoreside();

        while (!routingModel.isEnd(index)) {
            manager.indexToNode(index);

            double[] centroid = mission.getMissionParameters().getCentroid((int) index);

            Node node = new Node(centroid[0], centroid[1]);
            agent.assignNode(node);

            index = assignment.value(routingModel.nextVar(index));
        }

        logger.info("Assigned " + agent.getAssignedNodes().size() + " nodes to agent: " + agent.getName());

        for (Map.Entry<String, AgentProxy> entry : environmentProperties.getAgents().entrySet()) {
//            AgentProxy agent = entry.getValue();
            // TODO: Set agent's behaviour parameter based on mission output
        }

        parserEngine.generateFiles();

        agentInitialiser.initalise(environmentProperties.getAgents());

    }

    private static Mission planMission() {
        MWApplication.initialize(MWMCROption.NODISPLAY, MWMCROption.NOJVM);

        MissionPlanner missionPlanner = new TspMissionPlanner();
        int agentCount = environmentProperties.getAgents().size();

        MissionParameters missionParameters = new MissionParameters(1, 1,
                new int[][]{
                        {0, 0},
                        {0, 20},
                        {10, 50},
                        {20, 20},
                        {20, 0}});

        try {
            Mission mission = missionPlanner.generate(missionParameters);
            missionPlanner.print(mission);

            return mission;
        } catch (PlannerException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to plan mission", e);
        }
    }

}

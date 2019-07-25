package com.type2labs.undersea.runner;

import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.planner.tsp.TspMissionPlanner;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Entry point of UNDERSEA application
 */
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

        parseMission();
        planMission();
    }

    private static void parseMission() throws IOException {
        ParserEngine parserEngine = new ParserEngine(properties);
        environmentProperties = parserEngine.parse();

        agentInitialiser.initalise(environmentProperties.getAgents());
    }

    private static void planMission() {
        MissionPlanner missionPlanner = new TspMissionPlanner();
        int agentCount = environmentProperties.getAgents().size();

        MissionParameters missionParameters = new MissionParameters(agentCount, 1,
                new int[][]{
                        {0, 0},
                        {0, 20},
                        {10, 50},
                        {20, 20},
                        {20, 0}});

        try {
            Mission mission = missionPlanner.generate(missionParameters);
            missionPlanner.print(mission);
        } catch (PlannerException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to plan mission", e);
        }
    }

}

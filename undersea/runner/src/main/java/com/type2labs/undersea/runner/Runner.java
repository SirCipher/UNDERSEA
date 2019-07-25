package com.type2labs.undersea.runner;

import com.type2labs.undersea.agent.AgentInitialiser;
import com.type2labs.undersea.agent.AgentProxy;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.properties.EnvironmentProperties;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Entry point of UNDERSEA application
 */
public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private static AgentInitialiser agentInitialiser;
    private static Properties runnerProperties = Utility.getPropertiesByName("resources/runner.properties");
    private static EnvironmentProperties environmentProperties;
    private static final String[] args = new String[]{"resources/mission.config", "resources/sensors.config", "." +
            "./UNDERSEA_Controller",
            "missions", "resources/config.properties", "missions", "mission-includes"};

    private static void parseMission() throws IOException {
        try {
            environmentProperties = ParserEngine.main(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        agentInitialiser = new AgentInitialiser();
        agentInitialiser.setRunnerPropeties(runnerProperties);
        agentInitialiser.initalise(environmentProperties.getAgents());

        for (Map.Entry<String, AgentProxy> entry : environmentProperties.getAgents().entrySet()) {
            AgentProxy agent = entry.getValue();
            agentInitialiser.createAgent(agent.getName());
        }

    }

    public static void main(String[] args) throws IOException {
        parseMission();
    }

}

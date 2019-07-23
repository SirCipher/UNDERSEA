package com.type2labs.undersea.runner;

import com.type2labs.undersea.AgentInitialiser;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.model.UUV;
import com.type2labs.undersea.dsl.uuv.properties.EnvironmentProperties;

import java.io.IOException;
import java.util.Map;

/**
 * Entry point of UNDERSEA application
 */
public class Runner {

    private final static AgentInitialiser agentInitialiser = new AgentInitialiser();

    public static void main(String[] args) throws IOException {
        args = new String[]{"resources/mission.config", "resources/sensors.config", "../UNDERSEA_Controller",
                "missions", "resources/config.properties", "missions", "mission-includes"};

        EnvironmentProperties environmentProperties;

        try {
            environmentProperties = ParserEngine.main(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        for (Map.Entry<String, UUV> entry : environmentProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();
            agentInitialiser.createAgent(uuv.getName());
        }

    }
}

package com.type2labs.undersea.agent;

import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.runner.AbstractRunner;
import com.type2labs.undersea.common.runner.AgentInitialiser;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.ParserEngine;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.prospect.RaftClusterConfig;
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

    public Runner(String configurationFileLocation, AgentInitialiser agentInitialiser) {
        super(configurationFileLocation, new AgentInitialiserImpl(new RaftClusterConfig(new UnderseaRuntimeConfig())));
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
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse mission: ", e);
        }

        return environmentProperties.getAgents();
    }
}

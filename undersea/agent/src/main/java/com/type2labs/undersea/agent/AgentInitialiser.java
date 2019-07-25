package com.type2labs.undersea.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiser {

    private static final Logger logger = LogManager.getLogger(AgentInitialiser.class);
    private Properties properties;
    private List<Agent> agents = new LinkedList<>();


    public AgentInitialiser() {
        logger.info("Initialised");
    }

    public void createAgent(String name) {
//        Agent agent = new Agent(name);
//
//        agents.add(new Agent(name));
    }

    public void setRunnerPropeties(Properties runnerProperties) {
        this.properties = runnerProperties;
    }

    public void initalise(Map<String, AgentProxy> agentProxyMap) {
        agentProxyMap.forEach((key, value) -> {
            if (!value.isParsed()) {
                throw new RuntimeException("Agent: " + value.getName() + " is uninitialised");
            }
            agents.add(value);
        });
        logger.info("Registered " + agentProxyMap.size() + " agents");
    }
}

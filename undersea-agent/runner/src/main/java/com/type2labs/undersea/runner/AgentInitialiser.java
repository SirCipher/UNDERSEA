package com.type2labs.undersea.runner;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.models.impl.DslAgent;
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
    private static AgentInitialiser instance;
    private Properties properties;
    private List<DslAgent> dslAgents = new LinkedList<>();

    private AgentInitialiser() {
        logger.info("Initialised");
    }

    public static AgentInitialiser getInstance() {
        if (instance == null) {
            instance = new AgentInitialiser();
        }

        return instance;
    }

    public void initalise(Map<String, DslAgentProxy> agentProxyMap) {
        agentProxyMap.forEach((key, value) -> {
            if (!value.isParsed()) {
                throw new RuntimeException("Agent: " + value.getName() + " is uninitialised. Cannot proceed");
            }
            dslAgents.add(value);
        });

        logger.info("Registered " + agentProxyMap.size() + " agents");
    }

}

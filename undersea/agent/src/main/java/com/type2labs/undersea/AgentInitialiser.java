package com.type2labs.undersea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiser {

    private static final Logger logger = LogManager.getLogger(AgentInitialiser.class);
    private List<Agent> agents = new LinkedList<>();


    public AgentInitialiser() {
        logger.info("Initialised");
    }

    public void createAgent(String name) {
        agents.add(new Agent(name));
    }

}

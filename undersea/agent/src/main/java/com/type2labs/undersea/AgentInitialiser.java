package com.type2labs.undersea;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiser {

    private static final Logger logger = Logger.getLogger(AgentInitialiser.class.getName());
    private List<Agent> agents = new LinkedList<>();


    public AgentInitialiser() {
        logger.info("Initialising...");
    }

    public void createAgent(String name) {
        agents.add(new Agent(name));
    }

}

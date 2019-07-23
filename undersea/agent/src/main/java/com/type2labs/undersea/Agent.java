package com.type2labs.undersea;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Agent {

    private static final Logger logger = LogManager.getLogger(Agent.class);

    public Agent(String name) {
        logger.info("Initialising agent: " + name);
    }

}

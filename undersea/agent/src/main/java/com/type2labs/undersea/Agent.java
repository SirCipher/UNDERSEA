package com.type2labs.undersea;

import java.util.logging.Logger;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Agent {

    private static final Logger logger = Logger.getLogger(Agent.class.getName());


    public Agent(String name) {
        logger.info("Initialising agent: " + name);
    }

}

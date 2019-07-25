package com.type2labs.undersea;


import com.type2labs.undersea.missionplanner.model.node.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Agent {

    private static final Logger logger = LogManager.getLogger(Agent.class);
    private static List<Node> assignedNodes = new ArrayList<>();
    private static List<Node> world = new ArrayList<>();

    public Agent(String name) {
        logger.info("Initialising agent: " + name);
    }

}

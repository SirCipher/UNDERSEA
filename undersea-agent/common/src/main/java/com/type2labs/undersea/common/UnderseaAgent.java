package com.type2labs.undersea.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnderseaAgent extends AbstractAgent {

    private static final Logger logger = LogManager.getLogger(UnderseaAgent.class);
    private static final long serialVersionUID = 788441301443053690L;

    public UnderseaAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status
            , Endpoint endpoint) {
        super(config, name, serviceManager, status, endpoint);
    }

}

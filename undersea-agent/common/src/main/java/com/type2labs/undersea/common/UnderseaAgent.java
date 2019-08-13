package com.type2labs.undersea.common;

import com.type2labs.undersea.common.visualiser.VisualiserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnderseaAgent extends AbstractAgent {

    private static final Logger logger = LogManager.getLogger(UnderseaAgent.class);
    private static final long serialVersionUID = 788441301443053690L;

    public UnderseaAgent(String name, ServiceManager serviceManager, AgentStatus status, VisualiserClient visualiser, Endpoint endpoint) {
        super(name, serviceManager, status, visualiser, endpoint);
    }

}

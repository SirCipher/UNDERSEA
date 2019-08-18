package com.type2labs.undersea.common.agent;

import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;

public class UnderseaAgent extends AbstractAgent {

    private static final long serialVersionUID = 788441301443053690L;

    public UnderseaAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status) {
        super(config, name, serviceManager, status);
    }

}

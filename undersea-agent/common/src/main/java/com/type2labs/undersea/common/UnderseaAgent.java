package com.type2labs.undersea.common;

public class UnderseaAgent extends AbstractAgent {

    private static final long serialVersionUID = 788441301443053690L;

    public UnderseaAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status
            , Endpoint endpoint) {
        super(config, name, serviceManager, status, endpoint);
    }

}

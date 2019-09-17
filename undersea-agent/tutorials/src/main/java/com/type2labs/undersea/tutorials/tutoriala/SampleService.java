package com.type2labs.undersea.tutorials.tutoriala;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.AgentService;

public class SampleService implements AgentService {

    private Agent agent;

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {
        System.out.println("Hello from " + agent.name());
    }


}

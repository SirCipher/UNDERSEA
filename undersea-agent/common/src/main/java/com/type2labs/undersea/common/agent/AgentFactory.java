package com.type2labs.undersea.common.agent;


import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.utilities.factory.AbstractFactory;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class AgentFactory implements AbstractFactory<Agent> {

    private int count = 0;

    @Override
    public Agent get(String name) {
        throw new NotImplementedException("Get agent not supported at present");
    }

    public Agent createWith(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager,
                            AgentStatus agentStatus) {
        return new AbstractAgent(config, name, serviceManager, agentStatus) {
            private static final long serialVersionUID = -2866679824255773653L;

            @Override
            public List<Pair<String, String>> status() {
                return new ArrayList<>();
            }
        };
    }

    @Override
    public Agent create() {
        String name = "test:" + count++;
        ServiceManager serviceManager = new ServiceManager();
        AgentStatus agentStatus = new AgentStatus(name, new ArrayList<>());

        return new AbstractAgent(new UnderseaRuntimeConfig(), name, serviceManager, agentStatus) {
            private static final long serialVersionUID = -2866679824255773653L;

            @Override
            public List<Pair<String, String>> status() {
                return new ArrayList<>();
            }
        };
    }

    @Override
    public List<Agent> createN(int n) {
        List<Agent> dslAgents = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            dslAgents.add(create());
        }

        return dslAgents;
    }

}

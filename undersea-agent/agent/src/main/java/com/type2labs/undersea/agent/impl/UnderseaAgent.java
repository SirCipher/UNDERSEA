package com.type2labs.undersea.agent.impl;


import com.type2labs.undersea.common.agent.AbstractAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UnderseaAgent extends AbstractAgent {

    private static final long serialVersionUID = 788441301443053690L;

    private double speed;
    private double remainingBattery;
    private double range;
    private double accuracy;
    private String name;

    public UnderseaAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status
            , PeerId peerId) {
        super(config, name, serviceManager, status, peerId);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        speed = random.nextDouble(100);
        remainingBattery = random.nextDouble(100);
        range = random.nextDouble(100);
        accuracy = random.nextDouble(100);
    }

    public static UnderseaAgent DEFAULT() {
        return new UnderseaAgent(
                new UnderseaRuntimeConfig(),
                "DEFAULT",
                new ServiceManager(),
                new AgentStatus("DEFAULT", new ArrayList<>()),
                PeerId.newId()
        );
    }

    public void start() {
        services().startServices();
    }

}

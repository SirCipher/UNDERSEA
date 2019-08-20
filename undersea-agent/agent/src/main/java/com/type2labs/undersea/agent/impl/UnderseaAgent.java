package com.type2labs.undersea.agent.impl;


import com.type2labs.undersea.common.agent.AbstractAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
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

    public UnderseaAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status) {
        super(config, name, serviceManager, status);

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
                new AgentStatus("DEFAULT", new ArrayList<>())
        );
    }

    @Override
    public List<Pair<String, String>> status() {
        List<Pair<String, String>> status = new ArrayList<>();

        status.add(Pair.of("speed", Double.toString(speed)));
        status.add(Pair.of("remainingBattery", Double.toString(remainingBattery)));
        status.add(Pair.of("range", Double.toString(range)));
        status.add(Pair.of("accuracy", Double.toString(accuracy)));

        return status;
    }

    public void start() {
        services().startServices();
    }

}

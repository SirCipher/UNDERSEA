package com.type2labs.undersea.runner;

import com.type2labs.undersea.models.Agent;
import com.type2labs.undersea.models.AgentService;
import com.type2labs.undersea.models.AgentServices;
import com.type2labs.undersea.models.AgentStatus;
import com.type2labs.undersea.models.blockchain.BlockchainNetwork;
import com.type2labs.undersea.models.controller.Controller;
import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class UnderseaAgent implements Agent {

    private static final Logger logger = LogManager.getLogger(UnderseaAgent.class);
    private final ScheduledExecutorService internalExecutor = new ScheduledThreadPoolExecutor(4);
    private final AgentServices services;
    private final AgentStatus status;

    public UnderseaAgent(AgentServices agentServices, AgentStatus status) {
        this.services = agentServices;
        this.status = status;

        StringBuilder s = new StringBuilder("Agent: " + status.getName() + ". Registered services:");

        Iterator<AgentService> it = services.getServices().iterator();

        while (it.hasNext()) {
            s.append(" ").append(it.next().getClass().getSimpleName());

            if (it.hasNext()) {
                s.append(",");
            }
        }

        logger.info(s);
    }

    public BlockchainNetwork getBlockchainNetwork() {
        return (BlockchainNetwork) services.getService(BlockchainNetwork.class);
    }

    public Controller getController() {
        return (Controller) services.getService(Controller.class);
    }

    public MissionPlanner getMissionPlanner() {
        return (MissionPlanner) services.getService(MissionPlanner.class);
    }

    @Override
    public AgentServices services() {
        return services;
    }

    @Override
    public AgentService getService() {
        return null;
    }

    @Override
    public List<Pair<String, String>> status() {
        return null;
    }

    @Override
    public void start() {
        services.startServices();
    }

    @Override
    public boolean isAvailable() {
        return services.available();
    }

    @Override
    public void shutdown() {
        services.shutdownServices();
    }
}

package com.type2labs.undersea.common;

import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.visualiser.VisualiserClient;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AbstractAgent implements Agent {

    private static final Logger logger = LogManager.getLogger(AbstractAgent.class);
    private static final long serialVersionUID = 6578957795091837535L;

    private final ScheduledExecutorService internalExecutor = new ScheduledThreadPoolExecutor(4);
    private final ServiceManager services;
    private final AgentStatus status;
    private final Endpoint endpoint;
    private final UnderseaRuntimeConfig config;
    private VisualiserClient visualiser;
    private String name;

    public AbstractAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status,
                         Endpoint endpoint) {
        this.config = config;
        this.name = name;
        this.services = serviceManager;
        this.status = status;
        this.endpoint = endpoint;

        logServices();
    }

    public static Logger getLogger() {
        return logger;
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

    public ServiceManager getServices() {
        return services;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public VisualiserClient getVisualiser() {
        return visualiser;
    }

    public void setVisualiser(VisualiserClient visualiser) {
        this.visualiser = visualiser;
    }

    public String getName() {
        return name;
    }

    @Override
    public UnderseaRuntimeConfig config() {
        return config;
    }

    private void logServices() {
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

    @Override
    public ServiceManager services() {
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
    public VisualiserClient visualiser() {
        return visualiser;
    }

    @Override
    public Endpoint endpoint() {
        return endpoint;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void schedule(Runnable task) {
        internalExecutor.schedule(task, 500, TimeUnit.MILLISECONDS);
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

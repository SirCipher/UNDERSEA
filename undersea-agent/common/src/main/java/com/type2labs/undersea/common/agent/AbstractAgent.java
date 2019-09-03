package com.type2labs.undersea.common.agent;


import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public abstract class AbstractAgent implements Agent {

    private static final Logger logger = LogManager.getLogger(AbstractAgent.class);
    private static final long serialVersionUID = 6578957795091837535L;

    private final ScheduledExecutorService internalExecutor = new ScheduledThreadPoolExecutor(4);
    private final ServiceManager services;
    private final AgentStatus status;
    private final UnderseaRuntimeConfig config;
    private final ConcurrentHashMap<PeerId, Client> clusterClients = new ConcurrentHashMap<>();
    private final PeerId peerId;
    private AgentMetaData metaData;
    private String name;

    public AbstractAgent(UnderseaRuntimeConfig config, String name, ServiceManager serviceManager, AgentStatus status
            , PeerId peerId) {
        // Avoided using javax validation for minimal reliance on reflection
        if (config == null) {
            throw new IllegalArgumentException("Runtime configuration must be provided");
        }

        if (serviceManager == null) {
            throw new IllegalArgumentException("Service manager must be provided");
        }

        if (status == null) {
            throw new IllegalArgumentException("Agent status must be provided");
        }

        if (peerId == null) {
            throw new IllegalArgumentException("Peer ID must be provided");
        }

        this.config = config;
        this.name = name;
        this.services = serviceManager;
        this.status = status;
        this.peerId = peerId;

        serviceManager.setAgent(this);
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void setMetadata(AgentMetaData metaData) {
        this.metaData = metaData;
    }

    public BlockchainNetwork getBlockchainNetwork() {
        return services.getService(BlockchainNetwork.class);
    }

    public ConsensusAlgorithm getConsensusAlgorithm() {
        return services.getService(ConsensusAlgorithm.class);
    }

    public Controller getController() {
        return services.getService(Controller.class);
    }

    public MissionManager getMissionManager() {
        return services.getService(MissionManager.class);
    }

    public String getName() {
        return name;
    }

    public ServiceManager getServices() {
        return services;
    }

    public AgentStatus getStatus() {
        return status;
    }

    @Override
    public AgentMetaData metadata() {
        return metaData;
    }

    @Override
    public ServiceManager services() {
        return services;
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
    public UnderseaRuntimeConfig config() {
        return config;
    }

    @Override
    public void shutdown() {
        services.shutdownServices();
    }

    @Override
    public ConcurrentMap<PeerId, Client> clusterClients() {
        return clusterClients;
    }

    @Override
    public PeerId peerId() {
        return peerId;
    }
}

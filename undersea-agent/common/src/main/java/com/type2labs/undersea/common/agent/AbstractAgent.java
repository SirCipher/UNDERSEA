/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.agent;


import com.type2labs.undersea.common.blockchain.BlockchainNetwork;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * An abstract implementation of {@link Agent}. Implementing the core functionality of an agent for users to extend
 * to their own needs.
 */
public abstract class AbstractAgent implements Agent {

    private static final Logger logger = LogManager.getLogger(AbstractAgent.class);

    private final ServiceManager services;
    private final RuntimeConfig config;
    private final ConcurrentHashMap<PeerId, Client> clusterClients = new ConcurrentHashMap<>();
    private final PeerId peerId;
    private AgentMetaData metaData;
    private String name;
    private AgentState agentState = new AgentState();

    public AbstractAgent(RuntimeConfig config, String name, ServiceManager serviceManager, PeerId peerId) {
        this.config = Objects.requireNonNull(config);
        this.name = Objects.requireNonNull(name);
        this.services = Objects.requireNonNull(serviceManager);
        this.peerId = Objects.requireNonNull(peerId);

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

    public String getName() {
        return name;
    }

    public ServiceManager getServices() {
        return services;
    }

    @Override
    public AgentMetaData metadata() {
        return metaData;
    }

    @Override
    public ServiceManager serviceManager() {
        return services;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public RuntimeConfig config() {
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

    @Override
    public void log(LogEntry logEntry) {
        LogService logService = services.getService(LogService.class);

        if (logService != null) {
            logService.add(logEntry);
        }
    }

    @Override
    public AgentState state() {
        return agentState;
    }
}

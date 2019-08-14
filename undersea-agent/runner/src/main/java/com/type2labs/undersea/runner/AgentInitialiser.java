package com.type2labs.undersea.runner;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerEngine;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.EndpointImpl;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;
import com.type2labs.undersea.monitor.VisualiserClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiser {

    private static final Logger logger = LogManager.getLogger(AgentInitialiser.class);
    private static AgentInitialiser instance;
    private final RaftClusterConfig raftClusterConfig;
    private Properties properties;
    private List<UnderseaAgent> agents = new LinkedList<>();


    private AgentInitialiser(RaftClusterConfig raftClusterConfig) {
        this.raftClusterConfig = raftClusterConfig;
        logger.info("Initialised");
    }

    public static AgentInitialiser getInstance(RaftClusterConfig raftClusterConfig) {
        if (instance == null) {
            instance = new AgentInitialiser(raftClusterConfig);
        }

        return instance;
    }

    public List<UnderseaAgent> initalise(Map<String, DslAgentProxy> agentProxyMap) {
        agentProxyMap.forEach((key, value) -> {
//            if (!value.isParsed()) {
//                throw new RuntimeException("Agent: " + value.getName() + " is uninitialised. Cannot proceed");
//            }

            EndpointImpl endpoint = new EndpointImpl(value.getName(), new InetSocketAddress(value.getHost(),
                    value.getServerPort()));
            RaftNodeImpl raftNode = new RaftNodeImpl(
                    raftClusterConfig,
                    value.getName(),
                    endpoint,
                    new RaftIntegrationImpl(value.getName(), endpoint)
            );

            ServiceManager serviceManager = new ServiceManager();
            serviceManager.registerService(raftNode);
            serviceManager.registerService(new BlockchainNetworkImpl());
            serviceManager.registerService(new ControllerEngine());
            serviceManager.registerService(new VehicleRoutingOptimiser());

            Monitor monitor = new MonitorImpl();
            serviceManager.registerService(monitor);

            UnderseaAgent underseaAgent = new UnderseaAgent(raftClusterConfig.getUnderseaRuntimeConfig(),
                    key,
                    serviceManager,
                    new AgentStatus(value.getName(), value.getSensors()),
                    endpoint);

            VisualiserClientImpl visualiser = new VisualiserClientImpl(underseaAgent);
            monitor.setVisualiser(visualiser);

            serviceManager.setAgent(underseaAgent);
            raftNode.setAgent(underseaAgent);

            underseaAgent.start();

            agents.add(underseaAgent);
        });

        logger.info("Registered " + agentProxyMap.size() + " agents");
        return agents;
    }

}

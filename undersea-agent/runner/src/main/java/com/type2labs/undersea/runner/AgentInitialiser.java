package com.type2labs.undersea.runner;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.monitor.VisualiserClientImpl;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.impl.RaftPeerId;
import com.type2labs.undersea.prospect.networking.ClientImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;
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
            ClientImpl endpoint = new ClientImpl(new InetSocketAddress(value.getHost(),
                    value.getServerPort()));
            RaftNodeImpl raftNode = new RaftNodeImpl(
                    raftClusterConfig,
                    value.getName(),
                    new RaftIntegrationImpl(value.getName()),
                    new InetSocketAddress("localhost", value.getServerPort()),
                    RaftPeerId.newId()
            );

            ServiceManager serviceManager = new ServiceManager();
            serviceManager.registerService(raftNode);
            serviceManager.registerService(new BlockchainNetworkImpl());
            serviceManager.registerService(new VehicleRoutingOptimiser());

            Monitor monitor = new MonitorImpl();
            serviceManager.registerService(monitor);

            UnderseaAgent underseaAgent = new UnderseaAgent(raftClusterConfig.getUnderseaRuntimeConfig(),
                    key,
                    serviceManager,
                    new AgentStatus(value.getName(), value.getSensors()));

            serviceManager.registerService(new ControllerImpl(
                    underseaAgent,
                    new MonitorPMC(),
                    new AnalyserPMC(),
                    new PlannerPMC(),
                    new ExecutorPMC()
            ));

            VisualiserClientImpl visualiser = new VisualiserClientImpl(underseaAgent);
            monitor.setVisualiser(visualiser);

            serviceManager.setAgent(underseaAgent);
            raftNode.setAgent(underseaAgent);

            serviceManager.startServices();

            agents.add(underseaAgent);
        });

        logger.info("Registered " + agentProxyMap.size() + " agents");
        return agents;
    }

}

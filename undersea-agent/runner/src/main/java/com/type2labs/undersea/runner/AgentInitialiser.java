package com.type2labs.undersea.runner;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.missions.planner.impl.MissionManagerImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.monitor.impl.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiser {

    private static final Logger logger = LogManager.getLogger(AgentInitialiser.class);
    private static AgentInitialiser instance;
    private final RaftClusterConfig raftClusterConfig;
    private Properties properties;
    private List<Agent> agents = new LinkedList<>();


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

    public List<Agent> initialise(Map<String, DslAgentProxy> agentProxyMap) {
        AgentFactory agentFactory = new AgentFactory();

        agentProxyMap.forEach((key, value) -> {
            RaftNodeImpl raftNode = new RaftNodeImpl(
                    raftClusterConfig,
                    value.getName(),
                    new RaftIntegrationImpl(value.getName()),
                    new InetSocketAddress("localhost", value.getServerPort()),
                    PeerId.newId()
            );

            ServiceManager serviceManager = new ServiceManager();
            serviceManager.registerService(raftNode);
            serviceManager.registerService(new BlockchainNetworkImpl());
            MissionManager missionManager = new MissionManagerImpl(new VehicleRoutingOptimiser());
            serviceManager.registerService(missionManager);

            Monitor monitor = new MonitorImpl();
            serviceManager.registerService(monitor);

            Agent underseaAgent = agentFactory.createWith(raftClusterConfig.getUnderseaRuntimeConfig(), key,
                    serviceManager,
                    new AgentStatus(key, new ArrayList<>()));


            serviceManager.registerService(new ControllerImpl(
                    underseaAgent,
                    new MonitorPMC(),
                    new AnalyserPMC(),
                    new PlannerPMC(),
                    new ExecutorPMC()
            ));

            monitor.setVisualiser( new VisualiserClientImpl());

            serviceManager.startServices();

            agents.add(underseaAgent);
        });

        logger.info("Registered " + agentProxyMap.size() + " agents");
        return agents;
    }

}

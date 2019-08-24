package com.type2labs.undersea.agent;

import com.type2labs.undersea.agent.impl.HardwareInterface;
import com.type2labs.undersea.agent.impl.MoosConnector;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.monitor.impl.MonitorImpl;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.runner.AgentInitialiser;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.manager.MissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class AgentInitialiserImpl implements AgentInitialiser {

    private final RaftClusterConfig raftClusterConfig;
    private EnvironmentProperties environmentProperties;

    public AgentInitialiserImpl(RaftClusterConfig raftClusterConfig) {
        this.raftClusterConfig = raftClusterConfig;
    }

    @Override
    public List<Agent> initialise(Map<String, ? extends Agent> agents) {
        AgentFactory agentFactory = new AgentFactory();
        List<Agent> constructedAgents = new LinkedList<>();

        agents.forEach((key, value) -> {
            DslAgentProxy agentProxy = (DslAgentProxy) value;

            RaftNodeImpl raftNode = new RaftNodeImpl(
                    raftClusterConfig,
                    agentProxy.getName(),
                    new RaftIntegrationImpl(agentProxy.getName()),
                    new InetSocketAddress("localhost", agentProxy.getServerPort()),
                    PeerId.newId()
            );

            ServiceManager serviceManager = new ServiceManager();

            serviceManager.registerService(raftNode);
            serviceManager.registerService(new BlockchainNetworkImpl());
            serviceManager.registerService(new MissionManagerImpl(new VehicleRoutingOptimiser()));
            serviceManager.registerService(new HardwareInterface(), ServiceManager.ServiceExecutionPriority.HIGH);
            serviceManager.registerService(new MoosConnector());

            Monitor monitor = new MonitorImpl();
            monitor.setVisualiser(new VisualiserClientImpl());
            serviceManager.registerService(monitor);

            Agent underseaAgent = agentFactory.createWith(raftClusterConfig.getUnderseaRuntimeConfig(), key,
                    serviceManager,
                    new AgentStatus(key, new ArrayList<>()));

            AgentMetaData metaData = new AgentMetaData();
            if (((DslAgentProxy) value).name().equals("shoreside")) {
                metaData.setMaster(true);
            }

            metaData.setHardwarePort(((DslAgentProxy) value).getServerPort());
            metaData.setMetadataFileName(((DslAgentProxy) value).getMetaFileName());

            String missionName =
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.MISSION_NAME);
            metaData.setMissionName(missionName);

            Properties properties = environmentProperties.getRunnerProperties();
            metaData.setMissionDirectory(new File((String) properties.get("config.output")));

            underseaAgent.setMetadata(metaData);

            serviceManager.registerService(new ControllerImpl(
                    underseaAgent,
                    new MonitorPMC(),
                    new AnalyserPMC(),
                    new PlannerPMC(),
                    new ExecutorPMC()
            ));

            monitor.setVisualiser(new VisualiserClientImpl());

            constructedAgents.add(underseaAgent);
        });

        return constructedAgents;
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }
}

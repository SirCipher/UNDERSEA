package com.type2labs.undersea.agent;

import com.type2labs.undersea.agent.impl.HardwareInterface;
import com.type2labs.undersea.agent.impl.MoosConnector;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.consensus.MultiRoleState;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.monitor.impl.MonitorImpl;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.runner.AgentInitialiser;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.hardware.NoNetworkInterfaceImpl;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.DefaultCallbacks;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;

import java.io.File;
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
            ServiceManager serviceManager = new ServiceManager();

            serviceManager.registerService(new BlockchainNetworkImpl());
            serviceManager.registerService(new LogServiceImpl());
            serviceManager.registerService(new MoosMissionManagerImpl(new VehicleRoutingOptimiser()));

            Monitor monitor = new MonitorImpl();
            monitor.setVisualiser(new VisualiserClientImpl());
            serviceManager.registerService(monitor);

            Agent underseaAgent = agentFactory.createWith(raftClusterConfig.getUnderseaRuntimeConfig(), key,
                    serviceManager,
                    new AgentStatus(key, new ArrayList<>()));

            RaftNodeImpl raftNode = new RaftNodeImpl(
                    raftClusterConfig,
                    agentProxy.getName()
            );

            serviceManager.registerService(raftNode);

            raftNode.registerCallback(DefaultCallbacks.defaultMissionCallback(underseaAgent, raftNode,
                    raftClusterConfig));

            Properties properties = environmentProperties.getRunnerProperties();
            AgentMetaData metaData = value.metadata();
            metaData.setProperty(AgentMetaData.PropertyKey.SIMULATION_SPEED,
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.SIMULATION_SPEED));

            if (value.name().equals("shoreside")) {
                metaData.setProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE, true);
                raftNode.multiRole().setStatus(MultiRoleState.Status.LEADER);
            } else {
                metaData.setProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE, false);
            }

            metaData.setProperty(AgentMetaData.PropertyKey.SERVER_PORT, ((DslAgentProxy) value).getServerPort());

            metaData.setProperty(AgentMetaData.PropertyKey.METADATA_FILE_NAME,
                    ((DslAgentProxy) value).getMetaFileName());

            String missionName =
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.MISSION_NAME);
            metaData.setProperty(AgentMetaData.PropertyKey.MISSION_NAME, missionName);


            metaData.setProperty(AgentMetaData.PropertyKey.MISSION_DIRECTORY, new File((String) properties.get(
                    "config.output")));

            underseaAgent.setMetadata(metaData);

            serviceManager.registerService(new HardwareInterface(), ServiceManager.ServiceExecutionPriority.HIGH);

            if (!(boolean) value.metadata().getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)) {
                serviceManager.registerService(new MoosConnector(), ServiceManager.ServiceExecutionPriority.MEDIUM);
                serviceManager.registerService(ControllerImpl.PMCimpl(), ServiceManager.ServiceExecutionPriority.LOW);
            } else {
                serviceManager.registerService(new NoNetworkInterfaceImpl());
            }

            monitor.setVisualiser(new VisualiserClientImpl());

            constructedAgents.add(underseaAgent);
        });

        return constructedAgents;
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }
}

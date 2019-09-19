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

package com.type2labs.undersea.agent;

import com.type2labs.undersea.agent.impl.HardwareInterface;
import com.type2labs.undersea.agent.impl.MoosConnector;
import com.type2labs.undersea.common.agent.*;
import com.type2labs.undersea.common.consensus.MultiRoleStatus;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.runner.AgentInitialiser;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.hardware.NoNetworkInterfaceImpl;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.impl.DefaultServiceCallbacks;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Initialises {@link Agent}s as required by UNDERSEA
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
            ServiceManager serviceManager = new ServiceManager();

            serviceManager.registerService(new BlockchainNetworkImpl());
            serviceManager.registerService(new LogServiceImpl());
            serviceManager.registerService(new MoosMissionManagerImpl(new VehicleRoutingOptimiser()));

            SubsystemMonitor subsystemMonitor = new SubsystemMonitorSpoofer();
            subsystemMonitor.setVisualiser(new VisualiserClientImpl());
            subsystemMonitor.registerSpeedRange(((DslAgentProxy) value).getSpeedRange());

            for (Sensor sensor : ((DslAgentProxy) value).getSensors()) {
                subsystemMonitor.monitorSubsystem(sensor);
            }

            serviceManager.registerService(subsystemMonitor);

            Agent underseaAgent = agentFactory.createWith(raftClusterConfig.getRuntimeConfig(), key,
                    serviceManager);

            if (!((DslAgentProxy) value).isActive()) {
                underseaAgent.state().transitionTo(AgentState.State.BACKUP);
            } else {
                underseaAgent.state().transitionTo(AgentState.State.ACTIVE);
            }

            RaftNodeImpl raftNode = new RaftNodeImpl(raftClusterConfig);

            serviceManager.registerService(raftNode);

            raftNode.registerCallback(DefaultServiceCallbacks.defaultMissionCallback(underseaAgent, raftNode,
                    raftClusterConfig));

            Properties properties = environmentProperties.getRunnerProperties();
            AgentMetaData metaData = value.metadata();
            metaData.setProperty(AgentMetaData.PropertyKey.SIMULATION_SPEED,
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.SIMULATION_SPEED));

            if (value.name().equals("shoreside")) {
                metaData.setProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE, true);
                raftNode.multiRoleState().setStatus(MultiRoleStatus.LEADER);
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

            subsystemMonitor.setVisualiser(new VisualiserClientImpl());

            constructedAgents.add(underseaAgent);
        });

        return constructedAgents;
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }
}

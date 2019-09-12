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

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.consensus.MultiRoleState;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;

import java.util.concurrent.ConcurrentMap;

/**
 * The top-level interface that represents some agent. Be it a submersible, aeriel drone or similar, this structure
 * represents it.
 * <p>
 * An agent has a number of {@link com.type2labs.undersea.common.service.AgentService}s associated
 * with it that execute the required functionality for the agent to achieve its mission. These services are separated
 * according to their functionality and can communicate with one another. The {@link ServiceManager} manages the
 * registered services, ensuring that services transition from an offline state, to online, and notifying services
 * when a service fails.
 */
public interface Agent {

    /**
     * Metadata associated with the agent. This can be local file storage, port mappings, or similar. For
     * auto-generated DSL agents, the files that are required for initialising the agent should be stored here.
     *
     * @return the associated metadata
     */
    AgentMetaData metadata();

    /**
     * Sets the metadata that will be associated with this agent.
     *
     * @param metaData to associate.
     */
    void setMetadata(AgentMetaData metaData);

    /**
     * The {@link ServiceManager} manages all registered {@link com.type2labs.undersea.common.service.AgentService}s
     * for this agent.
     *
     * @return the associated {@link ServiceManager}
     */
    ServiceManager serviceManager();

    /**
     * A human-friendly name of this agent.
     *
     * @return the name
     */
    String name();

    /**
     * The runtime configuration for this agent. This will normally be the same for all agents in a cluster, however,
     * it can be different.
     *
     * @return the configuration
     */
    RuntimeConfig config();

    /**
     * Signal that this agent should shutdown. Initiating a full shutdown of all the running
     * {@link com.type2labs.undersea.common.service.AgentService}s. Upon completion, the {@link Agent#state()} should
     * be transitioned to {@link AgentState.State#OFFLINE} if it has shutdown gracefully. Otherwise, it should be
     * transitioned to {@link AgentState.State#FAILED}.
     */
    void shutdown();

    /**
     * Cluster clients are ones that are registered in the
     * {@link com.type2labs.undersea.common.consensus.ConsensusAlgorithm} cluster. This should not return any
     * {@link MultiRoleState#remotePeers()} as these are not in the same cluster.
     *
     * @return the {@link Client}s that are local to this {@link Agent}
     */
    ConcurrentMap<PeerId, Client> clusterClients();

    /**
     * The unique {@link PeerId} that has been assigned to this agent
     *
     * @return the peer ID
     */
    PeerId peerId();

    /**
     * If this agent has a {@link com.type2labs.undersea.common.logger.model.LogService} registered with it, then
     * append the {@link LogEntry} to this agent. If the {@link LogEntry#replicate()} flag is set then the
     * {@link LogEntry} will be appended to the {@link Agent#clusterClients()}.
     *
     * @param logEntry to append
     */
    void log(LogEntry logEntry);

    /**
     * The current state of this agent. Agents are initialised as offline and following transitioning to a
     * {@link ServiceManager#isHealthy()} state, they are transitioned to a {@link AgentState.State#ACTIVE} state.
     * This, however, differs to {@link ServiceManager#isHealthy()} slightly as the {@link ServiceManager} can be
     * unhealthy due to {@link com.type2labs.undersea.common.service.AgentService}s having failed but they are not
     * registered as {@link AgentService#isCritical()}. I.e, the agent is still online and active but one or more of
     * its {@link AgentService}s have failed but this does not impact on the agent's ability to continue running,
     * given its current {@link com.type2labs.undersea.common.missions.planner.model.GeneratedMission}.
     *
     * @return the state of the agent
     */
    AgentState state();

}

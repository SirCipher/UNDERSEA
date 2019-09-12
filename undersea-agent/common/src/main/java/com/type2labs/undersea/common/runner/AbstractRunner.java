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

package com.type2labs.undersea.common.runner;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.executor.ExecutorUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * Abstract runner for initialising agents for local simulations. Performing agent initialisation in the required
 * order and allowing for callbacks to be made by implementors
 */
public abstract class AbstractRunner {

    private final AgentInitialiser agentInitialiser;
    private final String configurationFileLocation;
    private List<Agent> agents;

    public AbstractRunner(String configurationFileLocation, AgentInitialiser agentInitialiser) {
        try {
            Utility.fileExists(configurationFileLocation, false, false);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(configurationFileLocation + " does not exist");
        }

        this.configurationFileLocation = configurationFileLocation;
        this.agentInitialiser = Objects.requireNonNull(agentInitialiser);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    /**
     * Generate the implementation files for the {@link Agent}s
     */
    protected abstract void generateFiles();

    protected AgentInitialiser getAgentInitialiser() {
        return agentInitialiser;
    }

    /**
     * Parse the configuration files required to initialise the {@link Agent}s and return populated {@link Agent}s
     *
     * @param configurationFileLocation to run
     * @return populated agents
     */
    protected abstract Map<String, ? extends Agent> parseDsl(String configurationFileLocation);

    public void setup() {
        _setup();
    }

    /**
     * Start all of the {@link Agent}s and run the simulation
     */
    public void start() {
        agents.stream().filter(a -> (boolean) a.metadata().getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)).forEach(a -> a.serviceManager().startServices());

        ExecutorService executorService = ExecutorUtils.newExecutor(agents.size(), "abstract-runner-%d");

        agents.stream().filter(a -> !(boolean) a.metadata().getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)).forEach(a -> executorService.submit(() -> a.serviceManager().startServices()));
    }

    private void _setup() {
        Map<String, ? extends Agent> proxies = parseDsl(configurationFileLocation);
        agents = agentInitialiser.initialise(proxies);
        generateFiles();
    }

    /**
     * Invoke the shutdown procedure on all of the {@link Agent}s
     */
    public void shutdown() {
        for (Agent agent : agents) {
            agent.shutdown();
        }
    }

}

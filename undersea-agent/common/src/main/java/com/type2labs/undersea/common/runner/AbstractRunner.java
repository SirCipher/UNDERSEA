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
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public abstract class AbstractRunner {

    private final AgentInitialiser agentInitialiser;
    private final String configurationFileLocation;
    private List<Agent> agents;

    public List<Agent> getAgents() {
        return agents;
    }

    public AbstractRunner(String configurationFileLocation, AgentInitialiser agentInitialiser) {
        try {
            Utility.fileExists(configurationFileLocation, false, false);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(configurationFileLocation + " does not exist");
        }

        this.configurationFileLocation = configurationFileLocation;
        this.agentInitialiser = Objects.requireNonNull(agentInitialiser);
    }

    protected abstract void generateFiles();

    protected AgentInitialiser getAgentInitialiser() {
        return agentInitialiser;
    }

    protected abstract Map<String, ? extends Agent> parseDsl(String configurationFileLocation);

    public void setup() {
        _setup();
    }

    public void start() {
        agents.stream().filter(a -> (boolean) a.metadata().getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)).forEach(a -> a.services().startServices());

        ExecutorService executorService = ExecutorUtils.newExecutor(agents.size(), "abstract-runner-%d");

        agents.stream().filter(a -> !(boolean) a.metadata().getProperty(AgentMetaData.PropertyKey.IS_MASTER_NODE)).forEach(a -> executorService.submit(() -> a.services().startServices()));
    }

    private void _setup() {
        Map<String, ? extends Agent> proxies = parseDsl(configurationFileLocation);
        agents = agentInitialiser.initialise(proxies);
        generateFiles();
    }

    public void shutdown() {
        for (Agent agent : agents) {
            agent.shutdown();
        }
    }

}

package com.type2labs.undersea.common.runner;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.lang.ThreadUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
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

    private void _run() {
        agents.stream().filter(a -> a.metadata().isMaster()).forEach(a -> a.services().startServices());

        // Wait for viewer to start
        ThreadUtils.sleep(2000);

        agents.stream().filter(a -> !a.metadata().isMaster()).forEach(a -> {
            a.services().startServices();
            ThreadUtils.sleep(2000);
        });

    }

    protected abstract void generateFiles();

    protected AgentInitialiser getAgentInitialiser() {
        return agentInitialiser;
    }

    protected abstract Map<String, ? extends Agent> parseDsl(String configurationFileLocation);

    public void run() {
        setup();
        _run();
    }

    private void setup() {
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

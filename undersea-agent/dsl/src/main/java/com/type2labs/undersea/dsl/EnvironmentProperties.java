package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.utilities.UnderseaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class EnvironmentProperties {

    private static Properties runnerProperties;
    private final Map<EnvironmentValue, String> environmentValues = new HashMap<>();
    private final Map<String, DslAgentProxy> agents = new HashMap<>();

    public Properties getRunnerProperties() {
        return runnerProperties;
    }

    public static void setRunnerProperties(Properties runnerProperties) {
        EnvironmentProperties.runnerProperties = runnerProperties;
    }

    public void addAgent(DslAgentProxy agent) {
        if (this.agents.containsKey(agent.getName())) {
            throw new UnderseaException("Duplicate agent created: " + agent.getName());
        }

        this.agents.put(agent.getName(), agent);
    }

    public Map<String, DslAgentProxy> getAllAgents() {
        return agents;
    }

    public Map<String, DslAgentProxy> getAgents() {
        return agents.entrySet().stream()
                .filter(a -> !a.getValue().getName().equals("shoreside"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int getTotalNumberOfAgents() {
        return agents.size();
    }

    public String getEnvironmentValue(EnvironmentValue environmentValue) {
        return environmentValues.get(environmentValue);
    }

    public Map<EnvironmentValue, String> getEnvironmentValues() {
        return environmentValues;
    }

    public DslAgentProxy getShoreside() {
        return agents.get("shoreside");
    }

    public void setEnvironmentValue(EnvironmentValue name, String value) {
        if (environmentValues.containsKey(name)) {
            throw new UnderseaException(name + " is already defined!");
        }

        runnerProperties.put(name.toString(), value);
        environmentValues.put(name, value);
    }

    public void validateEnvironmentValues() {
        StringBuilder errors = new StringBuilder();

        for (EnvironmentValue value : EnvironmentValue.values()) {
            if (!environmentValues.containsKey(value)) {
                errors.append("\t\t")
                        .append(value.name())
                        .append(" not defined: ")
                        .append(value.errorMessage)
                        .append("\n");
            }
        }

        if (errors.length() > 0) {
            throw new UnderseaException("Incorrect configuration file!\n" + errors.toString());
        }
    }

    public enum EnvironmentValue {
        MISSION_NAME("(e.g., mission name = reconnaissance"),
        SIMULATION_TIME("(e.g., simulation time = 10)"),
        SIMULATION_SPEED("(e.g., simulation speed = 2)"),
        HOST("(e.g., host = localhost)"),
        PORT_START("(e.g., port start = 9000)"),
        SENSOR_PORT("(e.g., sensor port= 6000)"),
        TIME_WINDOW("(e.g., time window = 10)");

        private final String errorMessage;

        EnvironmentValue(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

}

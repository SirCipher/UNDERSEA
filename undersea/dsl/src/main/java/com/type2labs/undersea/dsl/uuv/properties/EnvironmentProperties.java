package com.type2labs.undersea.dsl.uuv.properties;


import com.type2labs.undersea.agent.AgentProxy;
import com.type2labs.undersea.dsl.DSLException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EnvironmentProperties {

    private static EnvironmentProperties instance = null;
    private final Map<EnvironmentValue, String> environmentValues = new HashMap<>();
    private final Map<String, AgentProxy> agents = new HashMap<>();

    private EnvironmentProperties() {

    }

    public static EnvironmentProperties getInstance() {
        if (instance == null) {
            instance = new EnvironmentProperties();
        }

        return instance;
    }

    public void addAgent(AgentProxy agent) {
        if (this.agents.containsKey(agent.getName())) {
            throw new DSLException("Duplicate agent created: " + agent.getName());
        }

        this.agents.put(agent.getName(), agent);
    }

    public Map<String, AgentProxy> getAgents() {
        return agents.entrySet().stream()
                .filter(a -> !a.getValue().getName().equals("shoreside"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String getEnvironmentValue(EnvironmentValue environmentValue) {
        return environmentValues.get(environmentValue);
    }

    public Map<EnvironmentValue, String> getEnvironmentValues() {
        return environmentValues;
    }

    public AgentProxy getShoreside() {
        return agents.get("shoreside");
    }

    public void setEnvironmentValue(EnvironmentValue name, String value) {
        if (environmentValues.containsKey(name)) {
            throw new DSLException(name + " is already defined!");
        }

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
            throw new DSLException("Incorrect configuration file!\n" + errors.toString());
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

package com.type2labs.undersea.dsl.uuv.properties;


import com.type2labs.undersea.dsl.DSLException;
import com.type2labs.undersea.dsl.uuv.model.UUV;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationProperties {

    private static SimulationProperties instance = null;
    private final Map<EnvironmentValue, String> environmentValues = new HashMap<>();
    private final Map<String, UUV> agents = new HashMap<>();

    private SimulationProperties() {

    }

    public static SimulationProperties getInstance() {
        if (instance == null) {
            instance = new SimulationProperties();
        }

        return instance;
    }

    public void addUUV(UUV uuv) {
        if (this.agents.containsKey(uuv.getName())) {
            throw new DSLException("Duplicate UUV created: " + uuv.getName());
        }

        this.agents.put(uuv.getName(), uuv);
    }

    public Map<String, UUV> getAgents() {
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

    public UUV getShoreside() {
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

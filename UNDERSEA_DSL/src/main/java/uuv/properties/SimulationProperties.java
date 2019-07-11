package uuv.properties;

import auxiliary.DSLException;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;

import java.util.HashMap;
import java.util.Map;

public class SimulationProperties {

    private static SimulationProperties instance = null;
    private Map<EnvironmentValue, String> environmentValues = new HashMap<>();
    private Map<String, Sensor> globalSensors = new HashMap<>();
    private Map<String, UUV> agents = new HashMap<>();

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

    public Map<String, Sensor> getGlobalSensors() {
        return globalSensors;
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
        SIMULATION_TIME("(e.g., simulation time = 10)"),
        SIMULATION_SPEED("(e.g., simulation speed = 2)"),
        HOST("(e.g., host = localhost)"),
        PORT("(e.g., port = 12345)"),
        TIME_WINDOW("(e.g., time window = 10)");

        private String errorMessage;

        EnvironmentValue(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

}

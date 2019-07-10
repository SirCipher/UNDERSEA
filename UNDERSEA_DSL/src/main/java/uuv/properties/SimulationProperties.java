package uuv.properties;

import auxiliary.DSLException;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;

import java.util.Map;

public class SimulationProperties {

    enum EnvironmentValue {
        SIMULATION_TIME("(e.g., simulation time = 10)"),
        SIMULATION_SPEED("(e.g., simulation speed = 2)"),
        HOST("(e.g., host = localhost)"),
        PORT("(e.g., port = 12345)"),
        TIME_WINDOW("(e.g., time window = 10)");

        private String errorMessage;

        EnvironmentValue(String errorMessage) {
            this.errorMessage=errorMessage;
        }
    }

    private Map<EnvironmentValue, String> environmentValues;
    private Map<String, Sensor> globalSensors;
    private Map<String, UUV> agents;

    public void setEnvironmentValue(EnvironmentValue name, String value) {
        if (environmentValues.get(name) != null) {
            throw new DSLException(name + " is already defined!");
        }

        environmentValues.put(name, value);
    }

    public void validateEnvironmentValues() {
        StringBuilder errors = new StringBuilder();

        for (EnvironmentValue value : EnvironmentValue.values()) {
            if (environmentValues.get(value) == null) {
                errors.append(value.name()).append(" not defined: ").append(value.errorMessage);
            }
        }

        if (errors.length() > 0) {
            throw new DSLException("Incorrect configuration file!\n" + errors.toString());
        }
    }

}

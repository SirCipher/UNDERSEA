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

package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.utilities.exception.UnderseaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

// TODO: 27/08/2019 I think this should be moved to common and be a subclass of env props
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
        // TODO: 2019-08-24 Remove shoreside name and set a flag
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
        MISSION_NAME("(e.g., mission name = reconnaissance)"),
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

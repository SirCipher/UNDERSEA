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

package com.type2labs.undersea.dsl.uuv.factory;


import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Sensor;
import com.type2labs.undersea.dsl.uuv.gen.SensorsParser;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Creates {@link Sensor}s from the data provided in the mission.config file, for
 * {@link com.type2labs.undersea.common.agent.Agent}s
 */
public class SensorFactory implements AbstractFactory<Sensor> {

    private final Map<String, Sensor> sensors = new HashMap<>();

    @Override
    public void create(ParserRuleContext context) {
        SensorsParser.SensorContext sensorContext = (SensorsParser.SensorContext) context;
        String name = sensorContext.name.getText();

        if (sensors.containsKey(name)) {
            throw new UnderseaException("Duplicate sensor defined: " + name);
        }

        double reliability = Double.parseDouble(Objects.requireNonNull(sensorContext.reliability.getText(),
                "Sensor reliability not provided"));
        double rate = Double.parseDouble(Objects.requireNonNull(sensorContext.rate.getText(),
                "Sensor rate not provided"));

        Sensor newSensor = new Sensor(name, rate, reliability);

        for (SensorsParser.ChangeContext d : sensorContext.change()) {
            double begin = Double.parseDouble(d.begin.getText());
            double end = Double.parseDouble(d.end.getText());
            double percentage = Double.parseDouble(d.value.getText());

            Range newChange = new Range(begin, end, percentage);
            newSensor.addChange(newChange);
        }

        sensors.put(name, newSensor);

    }

    @Override
    public Sensor get(String name) {
        if (!sensors.containsKey(name)) {
            throw new IllegalArgumentException("Sensor not found: " + name);
        } else {
            return sensors.get(name);
        }
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }
}

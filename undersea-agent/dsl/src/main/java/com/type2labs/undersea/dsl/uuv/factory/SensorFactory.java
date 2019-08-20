package com.type2labs.undersea.dsl.uuv.factory;


import com.type2labs.undersea.agent.impl.Range;
import com.type2labs.undersea.agent.impl.Sensor;
import com.type2labs.undersea.dsl.uuv.gen.SensorsParser;
import com.type2labs.undersea.utilities.UnderseaException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

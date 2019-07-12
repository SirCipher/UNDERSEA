package undersea.uuv.dsl.factory;

import undersea.DSLException;
import org.antlr.v4.runtime.ParserRuleContext;
import undersea.uuv.dsl.gen.SensorsParser;
import undersea.uuv.dsl.model.Range;
import undersea.uuv.dsl.model.Sensor;

import java.util.HashMap;
import java.util.Map;

public class SensorFactory implements AbstractFactory<Sensor> {

    private final Map<String, Sensor> sensors = new HashMap<>();

    @Override
    public Sensor create(ParserRuleContext context) {
        SensorsParser.SensorContext sensorContext = (SensorsParser.SensorContext) context;
        String name = sensorContext.name.getText();

        if (sensors.containsKey(name)) {
            throw new DSLException("Duplicate sensor defined: " + name);
        }

        double reliability = Double.parseDouble(sensorContext.reliability.getText());
        double rate = Double.parseDouble(sensorContext.rate.getText());

        Sensor newSensor = new Sensor(name, rate, reliability);

        for (SensorsParser.ChangeContext d : sensorContext.change()) {
            double begin = Double.parseDouble(d.begin.getText());
            double end = Double.parseDouble(d.end.getText());
            double percentage = Double.parseDouble(d.value.getText());

            Range newChange = new Range(begin, end, percentage);
            newSensor.addChange(newChange);
        }

        sensors.put(name, newSensor);

        return newSensor;
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

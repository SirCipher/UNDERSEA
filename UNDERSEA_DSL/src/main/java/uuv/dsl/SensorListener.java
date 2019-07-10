package uuv.dsl;

import auxiliary.DSLException;
import uuv.dsl.gen.SensorsBaseListener;
import uuv.dsl.gen.SensorsParser;
import uuv.dsl.model.Range;
import uuv.dsl.model.Sensor;
import uuv.properties.SimulationProperties;

public class SensorListener extends SensorsBaseListener {

    private SimulationProperties simulationProperties;

    @Override
    public void enterSensor(SensorsParser.SensorContext ctx) {
        String name = ctx.name.getText();

        if (simulationProperties.getGlobalSensors().get(name) != null) {
            throw new DSLException("Duplicate sensor defined: " + name);
        }

        double reliability = Double.parseDouble(ctx.reliability.getText());
        double rate = Double.parseDouble(ctx.rate.getText());

        Sensor newSensor = new Sensor(name, rate, reliability);

        for (SensorsParser.ChangeContext d : ctx.change()) {
            double begin = Double.parseDouble(d.begin.getText());
            double end = Double.parseDouble(d.end.getText());
            double percentage = Double.parseDouble(d.value.getText());

            Range newChange = new Range(begin, end, percentage);
            newSensor.addChange(newChange);
        }

        simulationProperties.getGlobalSensors().put(name, newSensor);
    }

    public void setSimulationProperties(SimulationProperties simulationProperties) {
        this.simulationProperties = simulationProperties;
    }
}

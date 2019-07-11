package uuv.dsl;

import uuv.dsl.factory.AbstractFactory;
import uuv.dsl.factory.FactoryProvider;
import uuv.dsl.gen.SensorsBaseListener;
import uuv.dsl.gen.SensorsParser;
import uuv.dsl.model.Sensor;

public class SensorListener extends SensorsBaseListener {

    private AbstractFactory<Sensor> sensorFactory = FactoryProvider.getSensorFactory();

    @Override
    public void enterSensor(SensorsParser.SensorContext ctx) {
        sensorFactory.create(ctx);
    }

}

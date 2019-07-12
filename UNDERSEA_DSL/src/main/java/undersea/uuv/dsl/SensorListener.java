package undersea.uuv.dsl;

import undersea.uuv.dsl.factory.AbstractFactory;
import undersea.uuv.dsl.factory.FactoryProvider;
import undersea.uuv.dsl.gen.SensorsBaseListener;
import undersea.uuv.dsl.gen.SensorsParser;
import undersea.uuv.dsl.model.Sensor;

public class SensorListener extends SensorsBaseListener {

    private AbstractFactory<Sensor> sensorFactory = FactoryProvider.getSensorFactory();

    @Override
    public void enterSensor(SensorsParser.SensorContext ctx) {
        sensorFactory.create(ctx);
    }

}

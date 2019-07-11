package uuv.dsl.factory;

import uuv.dsl.model.Sensor;

public class FactoryProvider {

    private static final AbstractFactory<Sensor> sensorFactory = new SensorFactory();

    public static AbstractFactory<Sensor> getSensorFactory() {
        return sensorFactory;
    }

}

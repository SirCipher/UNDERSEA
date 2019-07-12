package undersea.uuv.dsl.factory;

public class FactoryProvider {

    private static final SensorFactory sensorFactory = new SensorFactory();

    public static SensorFactory getSensorFactory() {
        return sensorFactory;
    }

}

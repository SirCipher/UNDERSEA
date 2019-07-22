package com.type2labs.undersea.dsl.uuv.listener;

import com.type2labs.undersea.dsl.uuv.factory.AbstractFactory;
import com.type2labs.undersea.dsl.uuv.factory.FactoryProvider;
import com.type2labs.undersea.dsl.uuv.gen.SensorsBaseListener;
import com.type2labs.undersea.dsl.uuv.gen.SensorsParser;
import com.type2labs.undersea.dsl.uuv.model.Sensor;


public class SensorListener extends SensorsBaseListener {

    private AbstractFactory<Sensor> sensorFactory = FactoryProvider.getSensorFactory();

    @Override
    public void enterSensor(SensorsParser.SensorContext ctx) {
        sensorFactory.create(ctx);
    }

}
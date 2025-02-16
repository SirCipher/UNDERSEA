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

package com.type2labs.undersea.dsl.uuv.listener;


import com.type2labs.undersea.common.agent.Range;
import com.type2labs.undersea.common.agent.Sensor;
import com.type2labs.undersea.dsl.EnvironmentProperties;
import com.type2labs.undersea.dsl.uuv.factory.AbstractFactory;
import com.type2labs.undersea.dsl.uuv.factory.FactoryProvider;
import com.type2labs.undersea.dsl.uuv.gen.UUVBaseListener;
import com.type2labs.undersea.dsl.uuv.gen.UUVParser;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;

import java.util.ArrayList;
import java.util.List;

public class UUVListener extends UUVBaseListener {

    private final AbstractFactory<Sensor> sensorFactory = FactoryProvider.getSensorFactory();
    private EnvironmentProperties environmentProperties;
    private int serverPortStart;

    @Override
    public void enterHost(UUVParser.HostContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST, ctx.value.getText());
    }

    @Override
    public void enterInvocation(UUVParser.InvocationContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW,
                ctx.value.getText());
    }

    @Override
    public void enterMissionName(UUVParser.MissionNameContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.MISSION_NAME,
                ctx.value.getText());
    }

    @Override
    public void enterPortStart(UUVParser.PortStartContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.PORT_START,
                ctx.value.getText());

        try {
            serverPortStart = Integer.parseInt(ctx.value.getText());
            serverPortStart++;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Unparseable port start value: " + ctx.value.getText());
        }
    }

    @Override
    public void enterSensorPort(UUVParser.SensorPortContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.SENSOR_PORT,
                ctx.value.getText());
    }

    @Override
    public void enterSimulation(UUVParser.SimulationContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.SIMULATION_TIME,
                ctx.value.getText());
    }

    @Override
    public void enterSpeed(UUVParser.SpeedContext ctx) {
        environmentProperties.setEnvironmentValue(EnvironmentProperties.EnvironmentValue.SIMULATION_SPEED,
                ctx.value.getText());
    }

    @Override
    public void enterUuv(UUVParser.UuvContext ctx) {
        String name = ctx.name.getText();
        String port = Integer.toString(serverPortStart++);
        double min = Double.parseDouble(ctx.min.getText());
        double max = Double.parseDouble(ctx.max.getText());
        int steps = Integer.parseInt(ctx.steps.getText());

        List<Sensor> sensors = new ArrayList<>();

        for (UUVParser.ElemContext context : ctx.elems().elem()) {
            Sensor sensor = sensorFactory.get(context.getText());
            sensors.add(sensor);
        }

        DslAgentProxy agent = new DslAgentProxy(name);
        agent.setServerPort(port);
        Range range = new Range(min, max, steps);
        agent.setSpeedRange(range);
        agent.setSensors(sensors);

        String activeStr = ctx.active.getText();
        agent.setActive(Boolean.parseBoolean(activeStr));

        environmentProperties.addAgent(agent);
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }
}

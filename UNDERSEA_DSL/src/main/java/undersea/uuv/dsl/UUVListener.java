package undersea.uuv.dsl;

import undersea.uuv.dsl.factory.AbstractFactory;
import undersea.uuv.dsl.factory.FactoryProvider;
import undersea.uuv.dsl.gen.UUVBaseListener;
import undersea.uuv.dsl.gen.UUVParser;
import undersea.uuv.dsl.model.PShareConfig;
import undersea.uuv.dsl.model.Sensor;
import undersea.uuv.dsl.model.UUV;
import undersea.uuv.properties.SimulationProperties;

import java.util.ArrayList;
import java.util.List;

public class UUVListener extends UUVBaseListener {

    private SimulationProperties simulationProperties;
    private AbstractFactory<Sensor> sensorFactory = FactoryProvider.getSensorFactory();
    private int serverPortStart;


    @Override
    public void enterPortStart(UUVParser.PortStartContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.PORT_START,
                ctx.value.getText());

        try {
            serverPortStart = Integer.parseInt(ctx.value.getText());
            serverPortStart++;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Unparseable port start value: " + ctx.value.getText());
        }
    }

    @Override
    public void enterSimulation(UUVParser.SimulationContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_TIME,
                ctx.value.getText());
    }

    @Override
    public void enterInvocation(UUVParser.InvocationContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.TIME_WINDOW,
                ctx.value.getText());
    }

    @Override
    public void enterMissionName(UUVParser.MissionNameContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.MISSION_NAME,
                ctx.value.getText());
    }

    @Override
    public void enterHost(UUVParser.HostContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.HOST, ctx.value.getText());
    }

    @Override
    public void enterSpeed(UUVParser.SpeedContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_SPEED,
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

        UUV uuv = new UUV(name, port, min, max, steps);
        PShareConfig pShareConfig = new PShareConfig(uuv);
        uuv.setpShareConfig(pShareConfig);
        uuv.setSensors(sensors);

        simulationProperties.addUUV(uuv);
    }

    public void setSimulationProperties(SimulationProperties simulationProperties) {
        this.simulationProperties = simulationProperties;
    }
}

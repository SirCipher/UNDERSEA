package uuv.dsl;

import uuv.dsl.factory.AbstractFactory;
import uuv.dsl.factory.FactoryProvider;
import uuv.dsl.gen.UUVBaseListener;
import uuv.dsl.gen.UUVParser;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;
import uuv.properties.SimulationProperties;

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
        String behaviourFile = ctx.behaviourFile.getText();
        double min = Double.parseDouble(ctx.min.getText());
        double max = Double.parseDouble(ctx.max.getText());
        int steps = Integer.parseInt(ctx.steps.getText());

        List<Sensor> sensors = new ArrayList<>();

        for (UUVParser.ElemContext context : ctx.elems(0).elem()) {
            Sensor sensor = sensorFactory.get(context.getText());
            sensors.add(sensor);
        }

        UUV uuv = new UUV(name, port, behaviourFile, min, max, steps);
        uuv.setSensors(sensors);

        simulationProperties.addUUV(uuv);
    }

    public void setSimulationProperties(SimulationProperties simulationProperties) {
        this.simulationProperties = simulationProperties;
    }
}

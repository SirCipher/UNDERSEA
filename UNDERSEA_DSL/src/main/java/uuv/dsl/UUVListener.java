package uuv.dsl;

import uuv.dsl.gen.UUVBaseListener;
import uuv.dsl.gen.UUVParser;
import uuv.dsl.model.Sensor;
import uuv.dsl.model.UUV;
import uuv.properties.SimulationProperties;

import java.util.Map;

public class UUVListener extends UUVBaseListener {

    private SimulationProperties simulationProperties;

    @Override
    public void enterSimulation(UUVParser.SimulationContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_TIME, ctx.value.getText());
    }


    @Override
    public void enterInvocation(UUVParser.InvocationContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.TIME_WINDOW, ctx.value.getText());
    }


    @Override
    public void enterHost(UUVParser.HostContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.HOST, ctx.value.getText());
    }


    @Override
    public void enterPort(UUVParser.PortContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.PORT, ctx.value.getText());
    }


    @Override
    public void enterSpeed(UUVParser.SpeedContext ctx) {
        simulationProperties.setEnvironmentValue(SimulationProperties.EnvironmentValue.SIMULATION_SPEED, ctx.value.getText());
    }


    @Override
    public void enterUuv(UUVParser.UuvContext ctx) {
        String name = ctx.name.getText();
        String port = ctx.value.getText();
        double min = Double.parseDouble(ctx.min.getText());
        double max = Double.parseDouble(ctx.max.getText());
        int steps = Integer.parseInt(ctx.steps.getText());

        simulationProperties.addUUV(new UUV(name, port, min, max, steps));
    }

    public void setSimulationProperties(SimulationProperties simulationProperties) {
        this.simulationProperties = simulationProperties;
    }
}

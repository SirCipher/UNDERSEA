package uuv.dsl;

import auxiliary.DSLException;
import uuv.dsl.gen.UUVBaseListener;
import uuv.dsl.gen.UUVParser;
import uuv.properties.UUVproperties;

public class UUVListener extends UUVBaseListener {

    /**
     * Keeps all properties given in the file
     */
    private UUVproperties properties = new UUVproperties();

    public UUVproperties getProperties() {
        return this.properties;
    }


    @Override
    public void enterSimulation(UUVParser.SimulationContext ctx) {
        try {
            properties.setSimulationTime(ctx.value.getText());
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }
    }


    @Override
    public void enterInvocation(UUVParser.InvocationContext ctx) {
        try {
            properties.setTimeWindow(ctx.value.getText());
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }
    }


    @Override
    public void enterHost(UUVParser.HostContext ctx) {
        try {
            properties.setHost(ctx.value.getText());
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }
    }


    @Override
    public void enterPort(UUVParser.PortContext ctx) {
        try {
            properties.setPort(ctx.value.getText());
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }
    }


    @Override
    public void enterSpeed(UUVParser.SpeedContext ctx) {
        try {
            properties.setSpeed(ctx.value.getText());
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }
    }


    @Override
    public void enterUuv(UUVParser.UuvContext ctx) {
        try {
            String name = ctx.name.getText();
            String port = ctx.value.getText();
            double min = Double.parseDouble(ctx.min.getText());
            double max = Double.parseDouble(ctx.max.getText());
            int steps = Integer.parseInt(ctx.steps.getText());

            properties.setUUV(name, port, min, max, steps);
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + ctx.value.getLine() + ")\t" + e.getMessage());
        }

    }


}

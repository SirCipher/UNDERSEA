package uuv.dsl;

import auxiliary.DSLException;
import uuv.dsl.gen.UUVBaseListener;
import uuv.dsl.gen.UUVParser;
import uuv.dsl.gen.UUVParser.ChangeContext;
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


    @Override
    public void enterSensor(UUVParser.SensorContext ctx) {
//		System.out.println(ctx.getText());
        try {
            String name = ctx.name.getText();
            double reliability = Double.parseDouble(ctx.reliability.getText());
            double rate = Double.parseDouble(ctx.rate.getText());

            properties.setSensor(name, rate, reliability);

            for (ChangeContext d : ctx.change()) {
                double begin = Double.parseDouble(d.begin.getText());
                double end = Double.parseDouble(d.end.getText());
                double percentage = Double.parseDouble(d.value.getText());

                properties.setChange(name, begin, end, percentage);

            }

            //		System.out.println( 	name +"\n"
            //							+ 	reliability +"\n"
            //							+ 	rate +"\n"
            //							+	Arrays.toString(degradationList.toArray())
            //						  );
        } catch (DSLException e) {
            System.err.println("ERROR (l:" + (ctx.name.getLine() - 1) + ")\t" + e.getMessage());
        }
    }


}

package uuv.dsl.model;

import uuv.properties.SimulationProperties;

import java.util.ArrayList;
import java.util.List;

public class PShareConfig {

    private Input input;
    private List<Output> outputs = new ArrayList<>();
    private UUV parentUuv;
    private int pSharePort;

    public PShareConfig(UUV parentUuv) {
        this.parentUuv = parentUuv;

        int serverPort = Integer.parseInt(parentUuv.getServerPort());
        // TODO: Make the pSharePort configurable
        this.pSharePort = serverPort + 200;

        this.input = new Input(parentUuv);

        // TODO: Replace once grammar has been updated
        SimulationProperties properties = SimulationProperties.getInstance();
        String route = properties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + ":" +
                properties.getEnvironmentValue(SimulationProperties.EnvironmentValue.PORT_START);
        this.outputs.add(new Output("NODE_REPORT_LOCAL", "NODE_REPORT", route));
        this.outputs.add(new Output("VIEW_SEGLIST", null, route));
        this.outputs.add(new Output("VIEW_POINT", null, route));
    }

    public int getpSharePort() {
        return pSharePort;
    }

    public void addOutput(String src_name, String dest_name, String route) {
        this.outputs.add(new Output(src_name, dest_name, route));
    }

    @Override
    public String toString() {
        StringBuilder config = new StringBuilder();
        SimulationProperties simulationProperties = SimulationProperties.getInstance();
        String host = simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST);

        config.append("\nProcessConfig = pShare\n");
        config.append("{\n");
        config.append("\tAppTick    = 4\n");
        config.append("\tCommsTick  = 4  \n");
        config.append(input.toString()).append("\n");

        for (Output output : outputs) {
            config.append(output);
        }

        config.append("}\n");

        return config.toString();
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public static class Output {
        private String src_name;
        private String dest_name;
        private String route;

        public Output(String src_name, String dest_name, String route) {
            this.src_name = src_name;

            if (dest_name == null) {
                this.dest_name = src_name;
            } else {
                this.dest_name = dest_name;
            }

            this.route = route;
        }

        @Override
        public String toString() {
            return "\toutput = src_name=" + src_name + ", dest_name=" + dest_name + ", route=" + route + "\n";
        }
    }

    public class Input {
        private String input;

        Input(UUV uuv) {
            SimulationProperties properties = SimulationProperties.getInstance();
            this.input = "\tinput = route = " + properties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + ":" + pSharePort;
        }

        @Override
        public String toString() {
            return input;
        }
    }

}

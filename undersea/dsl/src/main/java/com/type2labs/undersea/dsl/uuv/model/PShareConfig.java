package com.type2labs.undersea.dsl.uuv.model;


import com.type2labs.undersea.dsl.uuv.properties.SimulationProperties;

import java.util.ArrayList;
import java.util.List;

public class PShareConfig {

    private final Input input;
    private final List<Output> outputs = new ArrayList<>();
    private final UUV parentUuv;
    private final int pSharePort;

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

    public void addOutput(String src_name, String dest_name, String route) {
        this.outputs.add(new Output(src_name, dest_name, route));
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public int getpSharePort() {
        return pSharePort;
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

    static class Output {
        private final String src_name;
        private final String dest_name;
        private final String route;

        Output(String src_name, String dest_name, String route) {
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

    class Input {
        private final String input;

        Input(UUV uuv) {
            SimulationProperties properties = SimulationProperties.getInstance();
            this.input =
                    "\tinput = route = " + properties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST) + ":" + pSharePort;
        }

        @Override
        public String toString() {
            return input;
        }
    }

}

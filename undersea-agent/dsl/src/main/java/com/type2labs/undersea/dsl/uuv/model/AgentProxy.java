package com.type2labs.undersea.dsl.uuv.model;

import com.type2labs.undersea.models.model.Agent;
import com.type2labs.undersea.models.model.Node;
import com.type2labs.undersea.models.model.Sensor;

import java.util.Iterator;

public class AgentProxy extends Agent {

    private boolean parsed = false;

    private String metaFileName;

    public AgentProxy() {

    }

    public AgentProxy(String name) {
        super(name);
    }

    public void checkParsed(String access) {
        if (!parsed) {
            throw new RuntimeException("Cannot set " + access + " before agent has been parsed");
        }
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    public void assignRandomNodes(int n, double latLowerBound, double latUpperBound, double longLowerBound,
                                  double longUpperBound) {
        for (int i = 0; i < n; i++) {
            this.assignNode(Node.randomNode(0.0, 150.0, -140.0, 0.0));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("//-------------------------\n");
        str.append("// sUUV Configuration Block\n");
        str.append("//-------------------------\n");
        str.append("ProcessConfig = sUUV\n");
        str.append("{\n");
        str.append("\t AppTick = " + super.getRate() + "\n");
        str.append("\t CommsTick = " + super.getRate() + "\n");
        str.append("\t MAX_APPCAST_EVENTS = 25 \n");
        str.append("\t NAME = " + super.getName() + "\n");
        str.append("\t PORT = " + super.getServerPort() + "\n");

        StringBuilder sensorsStr = new StringBuilder();

        Iterator<Sensor> iterator = super.getSensors().iterator();

        while (iterator.hasNext()) {
            sensorsStr.append(iterator.next().getName());

            if (iterator.hasNext()) {
                sensorsStr.append(",");
            }
        }
        str.append("\t SENSORS = ").append(sensorsStr).append("\n").append("}\n");

        return str.toString();
    }


}

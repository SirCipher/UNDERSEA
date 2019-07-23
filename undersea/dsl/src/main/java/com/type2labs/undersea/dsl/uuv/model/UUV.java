package com.type2labs.undersea.dsl.uuv.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UUV {

    private final String name;
    private final String rate;
    // TODO: Convert to int
    private final String serverPort;
    private final Range speedRange;
    private String metaFileName;
    private List<Sensor> sensors = new ArrayList<>();

    public UUV(String name, String serverPort, double min, double max, int steps) {
        this.name = name;
        this.serverPort = serverPort;
        this.speedRange = new Range(min, max, steps + 1);
        this.rate = "4";
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

    public String getName() {
        return this.name;
    }

    public String getRate() {
        return rate;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getServerPort() {
        return this.serverPort;
    }

    public double getSpeedMax() {
        return speedRange.getMax();
    }

    public double getSpeedMin() {
        return speedRange.getMin();
    }


    public int getSpeedSteps() {
        return (int) speedRange.getValue();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("//-------------------------\n");
        str.append("// sUUV Configuration Block\n");
        str.append("//-------------------------\n");
        str.append("ProcessConfig = sUUV\n");
        str.append("{\n");
        str.append("\t AppTick = " + rate + "\n");
        str.append("\t CommsTick = " + rate + "\n");
        str.append("\t MAX_APPCAST_EVENTS = 25 \n");
        str.append("\t NAME = " + name + "\n");
        str.append("\t PORT = " + serverPort + "\n");

        StringBuilder sensorsStr = new StringBuilder();

        Iterator<Sensor> iterator = sensors.iterator();

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
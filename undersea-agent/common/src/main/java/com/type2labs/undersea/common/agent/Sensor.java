package com.type2labs.undersea.common.agent;


import java.util.ArrayList;
import java.util.List;

public class Sensor implements Subsystem {

    private final String name;
    private final double rate;
    private final double reliability;
    private final SensorType sensorType;
    private final List<Range> changesList = new ArrayList<>();

    public Sensor(String name, double rate, double reliability) {
        // TODO: Set from DSl
        this(name, rate, reliability, SensorType.DEPTH);
    }

    public Sensor(String name, double rate, double reliability, SensorType sensorType) {
        this.name = name;
        this.rate = rate;
        this.reliability = reliability;
        this.sensorType = sensorType;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void addChange(Range change) {
        changesList.add(change);
    }

    public String getName() {
        return this.name;
    }

    public double getRate() {
        return this.rate;
    }

    public double getReliability() {
        return this.reliability;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("//---------------------------------\n");
        str.append("// " + name + " Configuration Block\n");
        str.append("//---------------------------------\n");
        str.append("ProcessConfig = " + name + "\n");
        str.append("{\n");
        str.append("\t AppTick\t\t\t= " + rate + "\n");
        str.append("\t CommsTick\t\t\t= " + rate + "\n");
        str.append("\t MAX_APPCAST_EVENTS = 25 \n");
        str.append("\t NAME\t\t\t\t= " + name + "\n");
        str.append("\t RELIABILITY\t\t= " + reliability + "\n");

        for (Range d : changesList) {
            str.append("\t CHANGE\t\t\t\t= ").append(d.toString()).append("\n");
        }

        str.append("}\n");

        return str.toString();
    }

    @Override
    public String name() {
        return name;
    }

    public enum SensorType {
        SIDESCAN_SONAR, CONDUCTIVITY, TEMPERATURE, DEPTH;
    }

}
package uuv.dsl.model;

import java.util.ArrayList;
import java.util.List;

public class UUV {

    private String name;
    private String rate;
    private String port;
    private Range speedRange;
    private List<Sensor> sensors = new ArrayList<>();

    public UUV(String name, String port, double min, double max, int steps) {
        this.name = name;
        this.port = port;
        this.speedRange = new Range(min, max, steps + 1);
        // TODO: What's this?
        this.rate = "4";
    }

    public String getRate() {
        return rate;
    }

    public Range getSpeedRange() {
        return speedRange;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public String getName() {
        return this.name;
    }

    public String getPort() {
        return this.port;
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

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("//------------------------------------------\n");
        str.append("// sUUV config block\n");
        str.append("//------------------------------------------\n");
        str.append("ProcessConfig = " + name + "\n");
        str.append("{\n");
        str.append("\t AppTick = " + rate + "\n");
        str.append("\t CommsTick = " + rate + "\n");
        str.append("\t MAX_APPCAST_EVENTS = 25 \n");
        str.append("\t NAME = " + name + "\n");
        str.append("\t PORT = " + port + "\n");

        String sensorsStr = "";

        // TODO: Fetch corresponding sensors from simulation properties
//        Iterator<String> iterator = sensorsMap.keySet().iterator();
//        while (iterator.hasNext()) {
//            sensorsStr += iterator.next();
//            if (iterator.hasNext())
//                sensorsStr += ",";
//        }
//        str.append("\t SENSORS = " + sensorsStr + "\n");
//
//        str.append("}\n\n");

        return str.toString();
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
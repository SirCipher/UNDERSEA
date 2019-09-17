/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.common.agent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A sensor representation for an {@link Agent}
 */
public class Sensor implements Subsystem {

    private final String name;
    private final double rate;
    private final double reliability;
    private final SensorType sensorType;
    private final List<Range> changesList = new ArrayList<>();
    private double accuracy;

    private double health = 100;

    public Sensor(String name, double rate, double reliability) {
        this(name, rate, reliability, SensorType.DEPTH);
        this.accuracy = ThreadLocalRandom.current().nextDouble(100);
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

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }

    public String toDslString() {
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

    @Override
    public double rate() {
        return rate;
    }

    @Override
    public double reliability() {
        return reliability;
    }

    @Override
    public double accuracy() {
        return accuracy;
    }

    @Override
    public double health() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public enum SensorType {
        SIDESCAN_SONAR, CONDUCTIVITY, TEMPERATURE, DEPTH;
    }

}
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

package com.type2labs.undersea.controller.controllerPMC.prism;

//class that represents a data structure for storing an RQVResult
public class PMCResult {
    int sensor1;
    int sensor2;
    int sensor3;
    double speed;
    double r1;    // expected number of accurate measurements
    double r2;    // expected power consumption
    double cost;    // cost


    public PMCResult(int CSC, double speed, double r1Result, double r2Result, double cost) {
        this.sensor1 = CSC % 2;
        this.sensor2 = CSC % 4 > 1 ? 1 : 0;
        this.sensor3 = CSC % 8 > 3 ? 1 : 0;
        this.speed = speed;
        this.r1 = r1Result;
        this.r2 = r2Result;
    }

    public double getCost() {
        return cost;
    }

    public double getReq1Result() {
        return r1;
    }

    public double getReq2Result() {
        return r2;
    }

    public int getSensor1() {
        return sensor1;
    }

    public int getSensor2() {
        return sensor2;
    }

    public int getSensor3() {
        return sensor3;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        str.append("Sensor1:" + sensor1 + NEW_LINE);
        str.append("Sensor2:" + sensor2 + NEW_LINE);
        str.append("Sensor3:" + sensor3 + NEW_LINE);
        str.append("Speed:" + speed + NEW_LINE);
        str.append("Req1Result:" + r1 + NEW_LINE);
        str.append("Req2Result:" + r2 + NEW_LINE);
        return str.toString();
    }


}
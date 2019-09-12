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

package com.type2labs.undersea.controller.controller.uuv;

import java.util.ArrayList;
import java.util.List;

public class UUVSensor {
    /**
     * rate history
     */
    protected List<Double> ratesList;
    /**
     * states history as determined by the controller
     */
    protected List<Integer> statesList;
    /**
     * num of readings every time_window seconds
     */
    protected List<Integer> readingsList;
    /**
     * num of accurate readings every time_window seconds
     */
    protected List<Integer> accurateReadingsList;
    /**
     * name
     */
    private String name;
    /**
     * current rate
     */
    private double currentRate;
    /**
     * current state
     */
    private int currentState;


    public UUVSensor(String name, double rate) {
        this.name = name;
        this.currentRate = rate;
        this.currentState = -1;
        this.ratesList = new ArrayList<Double>();
        this.statesList = new ArrayList<Integer>();
        this.readingsList = new ArrayList<Integer>();
        this.accurateReadingsList = new ArrayList<Integer>();
    }

    public List<Integer> getAccurateReadingsList() {
        return this.accurateReadingsList;
    }

    public double getCurrentRate() {
        return this.currentRate;
    }

    public double getCurrentState() {
        return this.currentState;
    }

    public String getName() {
        return this.name;
    }

    public List<Double> getRatesList() {
        return this.ratesList;
    }

    public List<Integer> getReadingsList() {
        return this.readingsList;
    }

    public List<Integer> getStatesList() {
        return this.statesList;
    }

    public boolean rateSame() {
        int size = ratesList.size();
        return ((ratesList.get(size - 1)) == (ratesList.get(size - 2)));
    }

    public void setAccurateReadings(int readings) {
        this.accurateReadingsList.add(readings);
    }

    public void setRate(double rate) {
        this.currentRate = rate;
        this.ratesList.add(rate);
    }

    public void setReadings(int readings) {
        this.readingsList.add(readings);
    }

    public void setState(int state) {
        this.currentState = state;
        this.statesList.add(state);
    }

}
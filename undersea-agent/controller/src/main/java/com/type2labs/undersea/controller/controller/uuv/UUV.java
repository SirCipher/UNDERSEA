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

// TODO: Refactor to agent
public class UUV {
    /**
     * speed history as decided by the controller
     */
    protected List<Double> speedList;
    /**
     * name
     */
    private String name;
    /**
     * speed
     */
    private double speed;


    public UUV(String name) {
        this.name = name;
        this.speedList = new ArrayList<Double>();
    }

    public String getName() {
        return this.name;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double s) {
        this.speed = s;
        this.speedList.add(s);
    }

    public List<Double> getSpeedList() {
        return this.speedList;
    }

    public boolean speedSame() {
        int size = speedList.size();
        if (size > 1)
            return ((speedList.get(size - 1)) == (speedList.get(size - 2)));
        return false;
    }

}
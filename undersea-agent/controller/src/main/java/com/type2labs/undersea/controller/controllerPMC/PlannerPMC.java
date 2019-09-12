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

package com.type2labs.undersea.controller.controllerPMC;

import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Planner;
import com.type2labs.undersea.controller.controllerPMC.prism.PMCResult;


public class PlannerPMC extends Planner {

    public PlannerPMC(Knowledge knowledge) {
        super(knowledge);
    }


    @Override
    public void run() {
        int bestIndex = -1;
        double bestCost = Double.MAX_VALUE;
        double MIN_READINGS = 20;
        double MAX_ENERGY = 120;

        //analyse configuration
        for (Integer index : getKnowledge().PMCResultsMap.keySet()) {
            PMCResult result = getKnowledge().PMCResultsMap.get(index);
            if ((result.getReq1Result() > MIN_READINGS) &&
                    (result.getReq2Result() < MAX_ENERGY) &&
                    (result.getCost() < bestCost)) {
                bestCost = result.getCost();
                bestIndex = index;
            }
        }

        PMCResult pmcResult = getKnowledge().PMCResultsMap.get(bestIndex);

        if (pmcResult != null) {
            //set new speed
            double desiredSpeed = pmcResult.getSpeed();
            getKnowledge().setUUVspeed(desiredSpeed);

            //set new sensor configuration
//		for (UUVSensor uuvSensor : Knowledge.sensorsMap.values()){
            getKnowledge().setSensorState("SENSOR1",
                    getKnowledge().PMCResultsMap.get(bestIndex).getSensor1());
            getKnowledge().setSensorState("SENSOR2",
                    getKnowledge().PMCResultsMap.get(bestIndex).getSensor2());
            getKnowledge().setSensorState("SENSOR3",
                    getKnowledge().PMCResultsMap.get(bestIndex).getSensor3());
//		}
        }

    }

}

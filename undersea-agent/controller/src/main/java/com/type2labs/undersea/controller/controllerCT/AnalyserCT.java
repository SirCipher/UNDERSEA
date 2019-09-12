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

package com.type2labs.undersea.controller.controllerCT;

import com.type2labs.undersea.controller.controller.Analyser;
import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.uuv.UUVSensor;
import com.type2labs.undersea.controller.controllerCT.SimCA.SimCAAdaptationEngine;

import java.util.ArrayList;


public class AnalyserCT extends Analyser {

    static final int goalsN = 2;
    final int sensorNum = 5;
    final int adaptPeriod = 100;
    private final Knowledge knowledge;
    int chosenSensor;
    double[] consumed = new double[goalsN + 1];
    double consumedSpeed;
    private ArrayList<Sensor> sensors;
    private double currentStep;
    private SimCAAdaptationEngine simca;

    public AnalyserCT(Knowledge knowledge) {
        super(knowledge);
        this.knowledge = knowledge;
        currentStep = 0;

        chosenSensor = 0;
        consumedSpeed = 0;
        for (int k = 0; k < goalsN + 1; k++)
            consumed[k] = 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (currentStep == 0) {
            //initializing simca
            simca = new SimCAAdaptationEngine();
            simca.setNumberOfGoals(goalsN);
            simca.setNumberOfOptimGoals(1);
            simca.setSensorNum(5);
            simca.initGoals();

            sensors = new ArrayList<Sensor>(5);
//			sensors = Knowledge.getInstance().getSensors();

            for (int i = 0; i < sensorNum; i++) {
                String sensName = "SENSOR" + (i + 1);
                double rate = knowledge.getSensorRate(sensName);

                simca.setSensorProperty(0, sensors.get(i).ID, rate * sensors.get(i).energry);
                simca.setSensorProperty(1, sensors.get(i).ID, rate / sensors.get(i).accuracy);
                simca.setSensorOptimProperty(0, sensors.get(i).ID, sensors.get(i).energry);
            }

            simca.init();
            simca.setGoal(1, 7);//setting system goals: throughput (R1)
            simca.setGoal(0, 12);//setting system goals: energy cons (R2)
            simca.setPole(0, 0.9);
            simca.setPole(1, 0.9);

        }

        //emulating system operation, 10000 invocations
        else if (currentStep % adaptPeriod == 0) {
            double[] measured = new double[goalsN];
            for (int k = 0; k < goalsN + 1; k++)
                consumed[k] = consumed[k] / (double) adaptPeriod;
            consumedSpeed = consumedSpeed / (double) adaptPeriod;

            measured[0] = consumed[1] * consumed[0] / consumedSpeed;
            measured[1] = (consumed[1] / consumed[2]) / consumedSpeed;

            System.out.println("Av.energy = " + measured[0]);
            System.out.println("Av.throughput = " + measured[1]);
            //System.out.println("Av.accuracy = " + consumed[2]/(double)adaptPeriod);

            simca.adapt(measured);
            knowledge.setUUVspeed(simca.getUUVspeed());
            for (int k = 0; k < goalsN + 1; k++)
                consumed[k] = 0;
            consumedSpeed = 0;
        }
        chosenSensor = simca.chooseSensors();

        //set new sensor configurationsimca.getUUVspeed()
        for (UUVSensor uuvSensor : knowledge.sensorsMap.values())
            knowledge.setSensorState(uuvSensor.getName(), 0);
        String chosenSens = "SENSOR" + (chosenSensor + 1);
        knowledge.setSensorState(chosenSens, 1);

        //System.out.println("Chosen sensor = " + chosenSensor);
        consumedSpeed += knowledge.getUUVspeed();

        consumed[0] += sensors.get(chosenSensor).energry;
        //consumed[1]+=sensors.get(chosenSensor).rate;
        consumed[1] += knowledge.getSensorRate(chosenSens);//
        consumed[2] += sensors.get(chosenSensor).accuracy;

        if (currentStep == 6000)//goal change scenario
            simca.setGoal(0, 14);

        currentStep++;
    }

}

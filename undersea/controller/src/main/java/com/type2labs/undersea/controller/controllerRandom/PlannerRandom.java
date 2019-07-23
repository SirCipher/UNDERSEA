package com.type2labs.undersea.controller.controllerRandom;

import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Planner;
import com.type2labs.undersea.controller.controller.uuv.UUVSensor;

import java.util.Random;


public class PlannerRandom extends Planner {

    Random rand = new Random(System.currentTimeMillis());


    public PlannerRandom() {
    }

    @Override
    public void run() {
        Knowledge.getInstance().setUUVspeed(rand.nextDouble() + rand.nextInt(4));

        for (UUVSensor uuvSensor : Knowledge.getInstance().sensorsMap.values()) {
            Knowledge.getInstance().setSensorState(uuvSensor.getName(), rand.nextInt(2) - 1);
        }
    }

}

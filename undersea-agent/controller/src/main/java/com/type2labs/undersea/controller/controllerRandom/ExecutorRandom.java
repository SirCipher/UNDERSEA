package com.type2labs.undersea.controller.controllerRandom;

import com.type2labs.undersea.controller.controller.Executor;
import com.type2labs.undersea.controller.controller.Knowledge;

import java.util.Iterator;


public class ExecutorRandom extends Executor {


    public ExecutorRandom(Knowledge knowledge) {
        super(knowledge);
    }


    @Override
    public void run() {
        command = "SPEED=" + (getKnowledge().getUUVspeed()) + ",";

        Iterator<String> it = getKnowledge().sensorsMap.keySet().iterator();
        while (it.hasNext()) {
            String sensorName = it.next();
            command += sensorName + "=" + (getKnowledge().getSensorState(sensorName));

            if (it.hasNext())
                command += ",";
        }
    }
}

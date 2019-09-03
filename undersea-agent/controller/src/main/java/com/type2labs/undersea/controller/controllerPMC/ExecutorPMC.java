package com.type2labs.undersea.controller.controllerPMC;


import com.type2labs.undersea.controller.controller.Executor;
import com.type2labs.undersea.controller.controller.Knowledge;

public class ExecutorPMC extends Executor {


    public ExecutorPMC(Knowledge knowledge) {
        super(knowledge);
    }

    @Override
    public void run() {
        //construct command
        String sp = "SPEED=" + (getKnowledge().getUUVspeed());
        String s1 = "SENSOR1=" + (getKnowledge().getSensorState("SENSOR1"));
        String s2 = "SENSOR2=" + (getKnowledge().getSensorState("SENSOR2"));
        String s3 = "SENSOR3=" + (getKnowledge().getSensorState("SENSOR3"));
        command = sp + "," + s1 + "," + s2 + "," + s3;
//		System.out.println(command);
    }
}

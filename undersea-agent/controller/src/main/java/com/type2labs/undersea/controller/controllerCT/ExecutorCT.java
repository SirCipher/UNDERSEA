package com.type2labs.undersea.controller.controllerCT;


import com.type2labs.undersea.common.agent.AbstractAgent;
import com.type2labs.undersea.controller.controller.Executor;
import com.type2labs.undersea.controller.controller.Knowledge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecutorCT extends Executor {

    private static final Logger logger = LogManager.getLogger(ExecutorCT.class);

    private String command;

    public ExecutorCT(Knowledge knowledge) {
        super(knowledge);
    }


    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void run() {
//		System.out.println("Executor.run()");
        //construct command
        String sp = "SPEED=" +   (getKnowledge().getUUVspeed());
        String s1 = "SENSOR1=" + (getKnowledge().getSensorState("SENSOR1"));
        String s2 = "SENSOR2=" + (getKnowledge().getSensorState("SENSOR2"));
        String s3 = "SENSOR3=" + (getKnowledge().getSensorState("SENSOR3"));
        String s4 = "SENSOR4=" + (getKnowledge().getSensorState("SENSOR4"));
        String s5 = "SENSOR5=" + (getKnowledge().getSensorState("SENSOR5"));
        logger.trace(command);
        command = sp + "," + s1 + "," + s2 + "," + s3 + "," + s4 + "," + s5;
    }

}

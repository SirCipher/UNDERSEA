package com.type2labs.undersea.controller.controllerCT;


import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Monitor;

public class MonitorCT extends Monitor {

    public MonitorCT(Knowledge knowledge) {
        super(knowledge);
    }

    @Override
    public void run() {
        getKnowledge().analysisRequired = true; //always analyze
    }

}

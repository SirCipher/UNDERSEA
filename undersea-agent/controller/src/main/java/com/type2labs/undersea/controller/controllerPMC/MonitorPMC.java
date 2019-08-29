package com.type2labs.undersea.controller.controllerPMC;


import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Monitor;

public class MonitorPMC extends Monitor {


    public MonitorPMC(Knowledge knowledge) {
        super(knowledge);
    }

    @Override
    public void run() {
        getKnowledge().analysisRequired = getKnowledge().systemStateChanged();
    }

}

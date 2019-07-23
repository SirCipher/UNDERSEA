package com.type2labs.undersea.controller.controllerPMC;


import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Monitor;

public class MonitorPMC extends Monitor {

    public MonitorPMC() {
    }

    @Override
    public void run() {
        boolean analysisRequired = Knowledge.getInstance().systemStateChanged();
        Knowledge.getInstance().analysisRequired = analysisRequired;
    }

}

package com.type2labs.undersea.controller.controllerCT;


import com.type2labs.undersea.controller.controller.Knowledge;
import com.type2labs.undersea.controller.controller.Monitor;

public class MonitorCT extends Monitor {

    public MonitorCT() {
    }

    @Override
    public void run() {
        Knowledge.getInstance().analysisRequired = true; //always analyze
    }

}

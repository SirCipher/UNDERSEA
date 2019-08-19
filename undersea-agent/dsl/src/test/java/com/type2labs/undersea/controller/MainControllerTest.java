package com.type2labs.undersea.controller;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import org.junit.Test;

import java.util.ArrayList;

public class MainControllerTest {

    @Test
    public void test() {
        ServiceManager serviceManager = new ServiceManager();
        UnderseaAgent underseaAgent = new UnderseaAgent(
                new UnderseaRuntimeConfig(),
                "test",
                serviceManager,
                new AgentStatus("test", new ArrayList<>()));

        serviceManager.setAgent(underseaAgent);

        serviceManager.startRepeatingService(new ControllerImpl(
                underseaAgent,
                new MonitorPMC(),
                new AnalyserPMC(),
                new PlannerPMC(),
                new ExecutorPMC()
        ), 1);

        serviceManager.startServices();
    }

}
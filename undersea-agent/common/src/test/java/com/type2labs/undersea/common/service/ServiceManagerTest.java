package com.type2labs.undersea.common.service;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServiceManagerTest {

    private int i = 0;

    private UnderseaAgent getAgent() {
        String name = "test";

        ServiceManager serviceManager = new ServiceManager();
        Monitor monitor = new MonitorImpl();
        serviceManager.registerService(monitor);
        serviceManager.registerService(new ServiceSample());

        UnderseaAgent underseaAgent = new UnderseaAgent(new UnderseaRuntimeConfig(),
                name,
                serviceManager,
                new AgentStatus(name, new ArrayList<>()));

        serviceManager.setAgent(underseaAgent);

        return underseaAgent;
    }

    @Test
    public void testServiceCancellation() throws InterruptedException {
        UnderseaAgent agent = getAgent();
        ServiceManager serviceManager = agent.services();

        serviceManager.startServices();

        Thread.sleep(100);

        serviceManager.shutdownService(ServiceSample.class);

        assertFalse(serviceManager.serviceRunning(ServiceSample.class));
    }

    @Test(timeout = 5000)
    public void testServiceCompletion() {
        UnderseaAgent agent = getAgent();
        ServiceManager serviceManager = agent.services();


        serviceManager.startServices();

        while (serviceManager.serviceRunning(ServiceSample.class)) {

        }

        assertEquals(10, i);
    }

    private class ServiceSample implements AgentService {

        @Override
        public void shutdown() {

        }

        @Override
        public void run() {
            i = 0;

            System.out.println("Hello from service sample");

            for (; i < 10; i++) {
                try {
                    String tn = Thread.currentThread().getName();

                    System.out.println(tn + ": " + i);

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Sample service threw exception: ", e);
                }
            }
        }
    }


}
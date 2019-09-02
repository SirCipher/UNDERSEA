package com.type2labs.undersea.agent.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.impl.MonitorImpl;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
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

        return new UnderseaAgent(new UnderseaRuntimeConfig(),
                name,
                serviceManager,
                new AgentStatus(name, new ArrayList<>()),
                PeerId.newId());
    }
//
//    @Test
    public void testServiceCancellation() throws InterruptedException {
        UnderseaAgent agent = getAgent();
        ServiceManager serviceManager = agent.services();

        serviceManager.startServices();

        Thread.sleep(100);

        serviceManager.shutdownService(ServiceSample.class, null);

        assertFalse(serviceManager.serviceRunning(ServiceSample.class));

        serviceManager.shutdownServices();
    }


    private class ServiceSample implements AgentService {

        @Override
        public void shutdown() {

        }

        @Override
        public boolean started() {
            return true;
        }

        @Override
        public ListenableFuture<?> executeTransaction(Transaction transaction) {
            return null;
        }

        @Override
        public void registerCallback(ServiceCallback serviceCallback) {

        }

        @Override
        public void initialise(Agent parentAgent) {

        }

        @Override
        public Agent parent() {
            return null;
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
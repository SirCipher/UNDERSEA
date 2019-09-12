/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.agent.service;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;

import static org.junit.Assert.assertFalse;

public class ServiceManagerTest {

    private int i = 0;

    private UnderseaAgent getAgent() {
        String name = "test";

        ServiceManager serviceManager = new ServiceManager();
        SubsystemMonitor subsystemMonitor = new SubsystemMonitorImpl();
        serviceManager.registerService(subsystemMonitor);
        serviceManager.registerService(new ServiceSample());

        return new UnderseaAgent(new RuntimeConfig(),
                name,
                serviceManager,
                PeerId.newId());
    }

    //
//    @Test
    public void testServiceCancellation() throws InterruptedException {
        UnderseaAgent agent = getAgent();
        ServiceManager serviceManager = agent.serviceManager();

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
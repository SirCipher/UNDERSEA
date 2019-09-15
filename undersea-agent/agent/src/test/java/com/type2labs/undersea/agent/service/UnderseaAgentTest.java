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

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;
import org.junit.Test;

import java.net.InetSocketAddress;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class UnderseaAgentTest {

    @Test
    public void testServices() {
        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new RuntimeConfig()),
                new InetSocketAddress("localhost", 5000)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new BlockchainNetworkImpl());
        serviceManager.registerService(new SubsystemMonitorImpl());

        UnderseaAgent underseaAgent = new UnderseaAgent(new RuntimeConfig(),
                "test",
                serviceManager,
                PeerId.newId());

        assertNotNull(serviceManager.getService(SubsystemMonitor.class));
        assertNotNull(underseaAgent.getBlockchainNetwork());
    }

}
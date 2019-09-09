package com.type2labs.undersea.agent.service;

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.seachain.BlockchainNetworkImpl;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Thomas Klapwijk on 2019-08-08.
 */
public class UnderseaAgentTest {

    @Test
    public void testServices() {
        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new UnderseaRuntimeConfig()),
                "test",
                new InetSocketAddress("localhost", 5000)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new BlockchainNetworkImpl());
//        serviceManager.registerService(new VehicleRoutingOptimiser());
        serviceManager.registerService(new SubsystemMonitorImpl());

        UnderseaAgent underseaAgent = new UnderseaAgent(new UnderseaRuntimeConfig(),
                "test",
                serviceManager,
                new AgentStatus("test", new ArrayList<>()),
                PeerId.newId());

        assertNotNull(serviceManager.getService(SubsystemMonitor.class));
        assertNotNull(underseaAgent.getBlockchainNetwork());

        serviceManager.shutdownServices();
    }

}
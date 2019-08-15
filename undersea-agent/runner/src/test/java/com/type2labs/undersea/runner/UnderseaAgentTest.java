package com.type2labs.undersea.runner;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.monitor.NullVisualiser;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerEngine;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.common.networking.EndpointImpl;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
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
        EndpointImpl endpoint = new EndpointImpl("test", new InetSocketAddress("localhost",
                5000));
        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new UnderseaRuntimeConfig()),
                "test",
                endpoint,
                new RaftIntegrationImpl("test", endpoint)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new BlockchainNetworkImpl());
        serviceManager.registerService(new ControllerEngine());
        serviceManager.registerService(new VehicleRoutingOptimiser());

        Monitor monitor = new MonitorImpl();
        serviceManager.registerService(monitor);

        UnderseaAgent underseaAgent = new UnderseaAgent(new UnderseaRuntimeConfig(),
                "test",
                serviceManager,
                new AgentStatus("test", new ArrayList<>()),
                endpoint);

        monitor.setVisualiser(new NullVisualiser());
        serviceManager.setAgent(underseaAgent);

        assertNotNull(underseaAgent.getMonitor());
        assertNotNull(underseaAgent.getBlockchainNetwork());
        assertNotNull(underseaAgent.getMissionPlanner());
        assertNotNull(underseaAgent.getController());
    }

}
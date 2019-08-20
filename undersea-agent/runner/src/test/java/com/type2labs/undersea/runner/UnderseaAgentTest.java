package com.type2labs.undersea.runner;

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.controller.ControllerImpl;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.common.cluster.PeerId;
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
                new RaftIntegrationImpl("test"),
                new InetSocketAddress("localhost", 5000),
                PeerId.newId()
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new BlockchainNetworkImpl());
        serviceManager.registerService(new VehicleRoutingOptimiser());
        serviceManager.registerService(new MonitorImpl());

        UnderseaAgent underseaAgent = new UnderseaAgent(new UnderseaRuntimeConfig(),
                "test",
                serviceManager,
                new AgentStatus("test", new ArrayList<>()));



        serviceManager.startRepeatingService(new ControllerImpl(
                underseaAgent,
                new MonitorPMC(),
                new AnalyserPMC(),
                new PlannerPMC(),
                new ExecutorPMC()
        ), 1);

        assertNotNull(serviceManager.getService(Monitor.class));
        assertNotNull(underseaAgent.getBlockchainNetwork());
        assertNotNull(underseaAgent.getMissionPlanner());
    }

}
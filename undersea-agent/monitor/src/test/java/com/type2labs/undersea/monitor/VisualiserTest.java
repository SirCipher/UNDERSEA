package com.type2labs.undersea.monitor;

import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.agent.UnderseaAgent;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.impl.RaftPeerId;
import com.type2labs.undersea.prospect.networking.ClientImpl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.UUID;

/**
 * These tests are not for CI
 */
public class VisualiserTest {

    private UnderseaAgent createAgent(int port) {
        String name = UUID.randomUUID().toString();

        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new UnderseaRuntimeConfig()), name,
                new RaftIntegrationImpl(name),
                new InetSocketAddress("localhost", port),
                RaftPeerId.newId()
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new VehicleRoutingOptimiser());

        UnderseaRuntimeConfig config = new UnderseaRuntimeConfig();
        config.enableVisualiser(true);

        Monitor monitor = new MonitorImpl();
        UnderseaAgent underseaAgent = new UnderseaAgent(config,
                name,
                serviceManager,
                new AgentStatus(name, new ArrayList<>()));

        VisualiserClientImpl visualiserClient = new VisualiserClientImpl(underseaAgent);
        monitor.setVisualiser(visualiserClient);

        serviceManager.setAgent(underseaAgent);
        raftNode.setAgent(underseaAgent);

        serviceManager.registerService(monitor);
        serviceManager.startServices();

        return underseaAgent;
    }

    //    @Test
    public void testDataUpdate() throws InterruptedException {
        new Visualiser();

        UnderseaAgent agent = createAgent(0);

        for (int i = 0; i < 10; i++) {
            createAgent(0);
        }

        RaftNodeImpl node = (RaftNodeImpl) agent.getConsensusAlgorithm();

        Thread.sleep(3000);
        node.toLeader();
        Thread.sleep(30000);
    }

    //    @Test
    public void testConnection() throws InterruptedException {
        new Visualiser();

        for (int i = 0; i < 100; i++) {
            createAgent(0);
        }

        Thread.sleep(10000);
    }

}
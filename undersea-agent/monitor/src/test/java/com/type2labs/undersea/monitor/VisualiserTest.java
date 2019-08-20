package com.type2labs.undersea.monitor;

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.monitor.Monitor;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * These tests are not for CI
 */
public class VisualiserTest {

    private UnderseaAgent createAgent(int port) {
        String name = UUID.randomUUID().toString();

        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new UnderseaRuntimeConfig()),
                name,
                new RaftIntegrationImpl(name)
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


        raftNode.initialise(underseaAgent);

        serviceManager.registerService(monitor);

        underseaAgent.start();

        return underseaAgent;
    }

    //    @Test
    public void testDataUpdate() throws InterruptedException {
        new Visualiser();

        UnderseaAgent leader = createAgent(0);
        RaftNodeImpl raftNode = (RaftNodeImpl) leader.getConsensusAlgorithm();
        List<UnderseaAgent> agents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UnderseaAgent t = createAgent(0);
            agents.add(t);
            raftNode.state().discoverNode((RaftNode) t.getConsensusAlgorithm());
        }


        Thread.sleep(3000);
        raftNode.toLeader();
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
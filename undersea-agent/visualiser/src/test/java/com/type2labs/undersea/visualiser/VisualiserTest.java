package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.*;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.EndpointImpl;
import com.type2labs.undersea.prospect.impl.RaftIntegrationImpl;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VisualiserTest {

    private List<Agent> agentList = new ArrayList<>();

    @BeforeClass
    public static void initVisualiser() {
        new Visualiser();
    }

    private UnderseaAgent createAgent(int port) {
        String name = UUID.randomUUID().toString();

        EndpointImpl endpoint = new EndpointImpl(name, new InetSocketAddress("localhost",
                port));
        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(new UnderseaRuntimeConfig()),
                name,
                endpoint,
                new RaftIntegrationImpl(name, endpoint)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);
        serviceManager.registerService(new VehicleRoutingOptimiser());

        UnderseaAgent underseaAgent = new UnderseaAgent(new UnderseaRuntimeConfig(), name, serviceManager,
                new AgentStatus(name, new ArrayList<>()), null);
        VisualiserClientImpl visualiserClient = new VisualiserClientImpl(underseaAgent);

        underseaAgent.setVisualiser(visualiserClient);
        serviceManager.setAgent(underseaAgent);
        raftNode.setAgent(underseaAgent);

        underseaAgent.start();

        agentList.add(underseaAgent);

        return underseaAgent;
    }


    @Test
    public void testConnection() throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            createAgent(0);
        }

        Thread.sleep(30000);
    }

}
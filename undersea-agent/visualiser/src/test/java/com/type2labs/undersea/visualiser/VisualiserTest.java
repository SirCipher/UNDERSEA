package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.*;
import com.type2labs.undersea.common.impl.AgentImpl;
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

public class VisualiserTest {

    @BeforeClass
    public static void initVisualiser() {
        new Visualiser();
    }

    @Test
    public void testConnection() throws IOException, InterruptedException {
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
        serviceManager.registerService(new VehicleRoutingOptimiser());

        UnderseaAgent underseaAgent = new UnderseaAgent(serviceManager, new AgentStatus("test", new ArrayList<>()), null, null);
        VisualiserClientImpl client = new VisualiserClientImpl();

        client.setParent(underseaAgent);
        client.openConnection();

        Thread.sleep(5000);

        client.closeConnection();
    }

}
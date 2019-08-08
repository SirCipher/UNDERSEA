package com.type2labs.undersea.runner;

import com.type2labs.undersea.controller.ControllerEngine;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.models.AgentServices;
import com.type2labs.undersea.models.AgentStatus;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.EndpointImpl;
import com.type2labs.undersea.prospect.impl.GroupIdImpl;
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
                new RaftClusterConfig(),
                "test",
                endpoint,
                new GroupIdImpl("test", "0"),
                new RaftIntegrationImpl("test", endpoint)
        );

        AgentServices agentServices = new AgentServices();
        agentServices.registerService(raftNode);
        agentServices.registerService(new BlockchainNetworkImpl());
        agentServices.registerService(new ControllerEngine());
        agentServices.registerService(new VehicleRoutingOptimiser());

        UnderseaAgent underseaAgent = new UnderseaAgent(agentServices, new AgentStatus("test", new ArrayList<>()));

        assertNotNull(underseaAgent.getBlockchainNetwork());
        assertNotNull(underseaAgent.getMissionPlanner());
        assertNotNull(underseaAgent.getController());
    }

}
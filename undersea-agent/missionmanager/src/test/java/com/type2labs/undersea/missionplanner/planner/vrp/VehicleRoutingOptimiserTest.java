package com.type2labs.undersea.missionplanner.planner.vrp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VehicleRoutingOptimiserTest {

    @BeforeClass
    public static void testInit() {
        try {
            new VehicleRoutingOptimiser();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

    @Test
    public void testRun() {
        MissionPlanner missionPlanner = new VehicleRoutingOptimiser();
        MissionManager missionManager = new MoosMissionManagerImpl(missionPlanner);
        UnderseaRuntimeConfig config = new UnderseaRuntimeConfig();
        AgentFactory agentFactory = new AgentFactory();
        Agent agent = agentFactory.createWith(config);

        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        List<Agent> agents = agentFactory.createN(5);
        agentFactory.populateCluster(agent, agents);
        agents.add(agent);

        MissionParameters missionParameters = new MissionParametersImpl(1, area, 50);
        missionParameters.setClients(new ArrayList<>(agent.clusterClients().values()));
        missionParameters.getClients().add(new Client() {

            @Override
            public ClusterState.ClientState state() {
                return new ClusterState.ClientState(this, true);
            }

            @Override
            public PeerId peerId() {
                return agent.peerId();
            }

            @Override
            public InetSocketAddress socketAddress() {
                return new InetSocketAddress(0);
            }

            @Override
            public void shutdown() {

            }

            @Override
            public boolean isSelf() {
                return true;
            }
        });

        config.missionParameters(missionParameters);
        missionManager.initialise(agent);

        try {
            GeneratedMissionImpl mission = (GeneratedMissionImpl) missionPlanner.generate();

            ObjectMapper mapper = new ObjectMapper();

            try {
                String res = mapper.writeValueAsString(mission);

                System.out.println(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Assert.fail();
            }

            if (mission.getAssignment() == null) {
                Assert.fail("No solution found");
            }

            missionPlanner.print(mission);
        } catch (PlannerException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
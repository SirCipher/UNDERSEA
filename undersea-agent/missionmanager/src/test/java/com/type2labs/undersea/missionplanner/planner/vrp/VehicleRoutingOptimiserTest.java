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

package com.type2labs.undersea.missionplanner.planner.vrp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
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

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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
        RuntimeConfig config = new RuntimeConfig();
        AgentFactory agentFactory = new AgentFactory();
        Agent agent = agentFactory.createWith(config);

        String configurationFileLocation = "src/test/resources/case-study-1/runner.properties";
        Properties properties = Utility.getPropertiesByName(configurationFileLocation);
        properties.put("pwd", new File(configurationFileLocation).getAbsoluteFile().getParent());
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        List<Agent> agents = agentFactory.createN(5);
        agentFactory.populateCluster(agent, agents);
        agents.add(agent);

        MissionParameters missionParameters = new MissionParametersImpl(1, area, 50);
        missionParameters.setClients(new ArrayList<>(agent.clusterClients().values()));
        missionParameters.getClients().add(new Client() {

            @Override
            public String name() {
                return agent.name();
            }

            @Override
            public ClusterState.ClientState state() {
                return new ClusterState.ClientState(this, new Random().nextInt(100));
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
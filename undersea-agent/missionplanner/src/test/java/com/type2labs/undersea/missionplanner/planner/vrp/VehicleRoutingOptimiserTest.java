package com.type2labs.undersea.missionplanner.planner.vrp;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.missionplanner.PlannerException;
import com.type2labs.undersea.common.missionplanner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missionplanner.models.MissionParameters;
import com.type2labs.undersea.common.missionplanner.models.MissionPlanner;
import com.type2labs.undersea.missionplanner.model.GeneratedMissionImpl;
import com.type2labs.undersea.utilities.Utility;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
        UnderseaRuntimeConfig config = new UnderseaRuntimeConfig();
        AgentFactory agentFactory = new AgentFactory();
        Agent agent = agentFactory.createWith(config);

        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        List<Agent> agents = agentFactory.createN(5);
        agentFactory.populateCluster(agent, agents);
        agents.add(agent);

        MissionParameters missionParameters = new MissionParametersImpl(1, area, 20);

        config.missionParameters(missionParameters);
        missionPlanner.initialise(agent);

        try {
            GeneratedMissionImpl mission = (GeneratedMissionImpl) missionPlanner.generate();

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
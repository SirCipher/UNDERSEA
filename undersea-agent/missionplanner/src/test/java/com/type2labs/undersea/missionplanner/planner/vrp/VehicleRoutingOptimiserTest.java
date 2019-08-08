package com.type2labs.undersea.missionplanner.planner.vrp;

import com.type2labs.undersea.models.missionplanner.MissionPlanner;
import com.type2labs.undersea.models.missionplanner.PlannerException;
import com.type2labs.undersea.missionplanner.model.MissionImpl;
import com.type2labs.undersea.missionplanner.model.MissionParametersImpl;
import com.type2labs.undersea.models.factory.AgentFactory;
import com.type2labs.undersea.models.impl.AgentImpl;
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
        Properties properties = Utility.getPropertiesByName("../resources/runner.properties");
        double[][] area = Utility.propertyKeyTo2dDoubleArray(properties, "environment.area");

        List<AgentImpl> dslAgents = new AgentFactory().createN(5);
        MissionParametersImpl missionParametersImpl = new MissionParametersImpl(dslAgents, 1, area, 50);

        try {
            MissionImpl mission = (MissionImpl) missionPlanner.generate(missionParametersImpl);

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
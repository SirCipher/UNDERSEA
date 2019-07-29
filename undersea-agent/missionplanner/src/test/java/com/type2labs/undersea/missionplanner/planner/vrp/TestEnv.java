package com.type2labs.undersea.missionplanner.planner.vrp;

import com.type2labs.undersea.missionplanner.planner.tsp.TspMissionPlanner;
import org.junit.Assert;
import org.junit.Test;

public class TestEnv {

    @Test
    public void test() {
        try {
            new MultiVehicleRoutingOptimiser();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

}

package com.type2labs.undersea.missionplanner.planner.vrp;

import org.junit.Assert;
import org.junit.Test;

public class TestEnv {

    @Test
    public void test() {
        try {
            new VehicleRoutingOptimiser();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

}

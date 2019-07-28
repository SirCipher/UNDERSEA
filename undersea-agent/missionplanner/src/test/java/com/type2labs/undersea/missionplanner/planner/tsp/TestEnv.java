package com.type2labs.undersea.missionplanner.planner.tsp;

import org.junit.Assert;
import org.junit.Test;

public class TestEnv {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.library.path"));

        try {
            new TspMissionPlanner();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

}

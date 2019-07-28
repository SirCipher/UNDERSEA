package com.type2labs.undersea.missionplanner.planner.tsp;

import com.type2labs.undersea.missionplanner.exception.PlannerException;
import com.type2labs.undersea.missionplanner.model.Mission;
import com.type2labs.undersea.missionplanner.model.MissionParameters;
import com.type2labs.undersea.missionplanner.model.MissionPlanner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class TspMissionPlannerTest {

    @BeforeClass
    public static void testInit() {
        try {
            new TspMissionPlanner();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

    @Test
    public void testRun() {
        MissionPlanner missionPlanner = new TspMissionPlanner();
        MissionParameters missionParameters = new MissionParameters(1, 1,
                new double[][]{
                        {0, 0},
                        {0, 20},
                        {10, 50},
                        {20, 20},
                        {20, 0}});

        try {
            Mission mission = missionPlanner.generate(missionParameters);
            missionPlanner.print(mission);
        } catch (PlannerException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}


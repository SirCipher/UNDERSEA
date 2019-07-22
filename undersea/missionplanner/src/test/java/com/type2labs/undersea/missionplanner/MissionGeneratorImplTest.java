package com.type2labs.undersea.missionplanner;

import com.type2labs.undersea.missionplanner.model.MissionGenerator;
import com.type2labs.undersea.missionplanner.model.impl.MissionGeneratorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionGeneratorImplTest {

    @BeforeClass
    public static void testInit() {
        try {
            new MissionGeneratorImpl();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

    @Test
    public void simpleDecomposeTest() {
        int[][] polygon = {{0, 0}, {0, 20}, {10, 50}, {20, 20}, {20, 0}};
        MissionGeneratorImpl missionPlanner = new MissionGeneratorImpl();

        try {
            missionPlanner.decompose(polygon);
        } catch (PlannerException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRun() {
        MissionGenerator missionGenerator = new MissionGeneratorImpl();
        int[][] polygon = {{0, 0}, {0, 20}, {10, 50}, {20, 20}, {20, 0}};

        try {
            missionGenerator.run(polygon);
        } catch (PlannerException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}


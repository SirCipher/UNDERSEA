package com.type2labs.undersea.utilities;

import org.junit.Assert;
import org.junit.Test;

public class PlannerUtilsTest {

    @Test
    public void distanceBetweenCoordinates() {
        double result = PlannerUtils.distanceBetweenCoordinates(43.824855, -70.330388, 43.824855, -70.329775);
        Assert.assertEquals(49.23159285416409, result, 0.0);
    }

}
package com.type2labs.undersea.runner;

import org.junit.Test;

import java.io.IOException;

public class RunnerTest {

    @Test
    public void testRunner() throws IOException {
        String[] args = new String[]{
                "../resources/mission.config",
                "../resources/sensors.config",
                "../UNDERSEA_Controller",
                "../missions",
                "../resources/config.properties",
                "../missions",
                "../mission-includes",
                "../resources/moos.properties"
        };

        Runner.main(args, "../resources/runner.properties");
    }


}

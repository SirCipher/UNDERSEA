package com.type2labs.undersea.runner;

import org.junit.Test;

import java.io.IOException;

public class RunnerTest {

    @Test
    public void testRunner() throws IOException {
        Runner.main(new String[]{"../resources/runner.properties"});
    }

}

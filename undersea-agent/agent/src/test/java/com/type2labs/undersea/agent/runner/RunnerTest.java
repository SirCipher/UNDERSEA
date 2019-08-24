package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class RunnerTest {

    @Test
    public void testRunner() throws IOException {
        String testProperties = "src/test/resources/test.properties";

        Assert.assertTrue(new File(testProperties).exists());
        Runner.main(new String[]{testProperties});
    }

}

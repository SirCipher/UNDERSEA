package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(UnderseaRunner.class)
public class RunnerTest {

    @Test
    public void ignored() {

    }

    @After
    public void killMoos() {
        Utility.killMoos();
    }

    @Test
    @IgnoredOnCi
    public void testRunner() throws InterruptedException {
        String testProperties = "src/test/resources/test.properties";

        Runner runner = new Runner(testProperties);

        runner.setup();
        runner.start();
        runner.onParsed(testProperties);

        Thread.sleep(10000);

        runner.shutdown();
    }

}

package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.monitor.Visualiser;
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

        Visualiser visualiser = new Visualiser();

        Runner runner = new Runner(testProperties);

        runner.setup();
        runner.start();
        runner.onParsed(testProperties);

        Thread.sleep(1000);

        runner.shutdown();
        visualiser.shutdown();
    }

}

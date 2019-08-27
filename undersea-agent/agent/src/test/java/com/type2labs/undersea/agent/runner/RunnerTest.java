package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.lang.ThreadUtils;
import com.type2labs.undersea.utilities.process.ProcessBuilderUtil;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public void testRunner() {
        String testProperties = "src/test/resources/test.properties";

        Runner runner = new Runner(testProperties);
        runner.run();

        runner.shutdown();
    }

}

/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.agent.runner;

import com.type2labs.undersea.agent.Runner;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(UnderseaRunner.class)
public class CaseStudyRunner {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    @Test
    public void ignored() {

    }

    @After
    public void killMoos() {
        Utility.killMoos();
    }

    @Test
    @IgnoredOnCi
    public void runCaseStudy1() throws InterruptedException {
        String testProperties = "src/test/resources/case-study-1/runner.properties";
        Runner runner = new Runner(testProperties);
        Visualiser visualiser = new Visualiser();

        runner.setup();
        runner.start();
        runner.onParsed(testProperties);

        while (!runner.missionComplete()) {
            Thread.sleep(500);
        }

        System.out.println("RunnerTest: Shutting down");

        runner.shutdown();
        visualiser.shutdown();
    }

    @Test
    @IgnoredOnCi
    public void runCaseStudy2() throws InterruptedException {
        String testProperties = "src/test/resources/case-study-2/runner.properties";
        Runner runner = new Runner(testProperties);
        Visualiser visualiser = new Visualiser();

        runner.setup();
        runner.start();
        runner.onParsed(testProperties);

        while (!runner.missionComplete()) {
            Thread.sleep(500);
        }

        System.out.println("RunnerTest: Shutting down");

        runner.shutdown();
        visualiser.shutdown();
    }

}

package com.type2labs.undersea.benchmark;

import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Test;

import java.util.Arrays;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static org.junit.Assert.assertNotNull;

public class LeaderReelectionBenchmark {

    private static final Logger logger = LogManager.getLogger(LeaderReelectionBenchmark.class);

    private final int NO_RUNS = 5;

    static class Results {
        double min;
        double max;
        double average;

        public Results(double min, double max, double average) {
            this.min = min;
            this.max = max;
            this.average = average;
        }

        public String toString(int size, int noRuns) {
            return "Leader election results (size=" + size + ", runs =" + noRuns + ") {" +
                    "min time=" + min + "ms" +
                    ", max time=" + max + "ms" +
                    ", average time =" + average + "ms" +
                    '}';
        }
    }

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    //    @BeforeClass
    public static void warmup() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Warmup run: " + (i + 1));
            createAndWait(3);

            System.gc();
        }
    }

    private static void createAndWait(int size) {
        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(size)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);
        }
    }

    private Results benchmarkResults(int size, int runs) {
        double[] results = new double[runs];

        for (int i = 0; i < runs; i++) {
            System.out.println("Run: " + (i + 1));

            try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(size)) {
                localAgentGroup.doManualDiscovery();
                localAgentGroup.start();


//                if(true) while (true) ;
                while (localAgentGroup.getLeaderNode() == null) ;
                RaftNode raftNode = localAgentGroup.getLeaderNode();
                raftNode.parent().shutdown();

                localAgentGroup.getRaftNodes().remove(localAgentGroup.getLeaderNode());

                logger.info("Starting counting");
                long startTime = System.currentTimeMillis();

                while (localAgentGroup.getLeaderNode() == null) ;

                logger.info("Finished counting");
                long finishTime = System.currentTimeMillis();

                results[i] = finishTime - startTime;
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return compileResults(results);
    }

    private double[] benchmarkRaw(int size, int runs) {
        double[] results = new double[runs];

        for (int i = 0; i < runs; i++) {
            System.out.println("Run: " + (i + 1));

            try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(size)) {
                localAgentGroup.doManualDiscovery();
                localAgentGroup.start();

                logger.info("Starting counting");
                long startTime = System.currentTimeMillis();

                while (localAgentGroup.getLeaderNode() == null) ;

                logger.info("Finished counting");
                long finishTime = System.currentTimeMillis();

                results[i] = finishTime - startTime;
            }
        }

        return results;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Results compileResults(double[] results) {
        double min = Arrays.stream(results).min().getAsDouble();
        double max = Arrays.stream(results).max().getAsDouble();
        double average = Arrays.stream(results).average().getAsDouble();

        return new Results(min, max, average);
    }

    @Test
    @IgnoredOnCi
    public void test_3() {
        run(3);
    }

    @Test
    @IgnoredOnCi
    public void test_5() {
        run(5);
    }

    @Test
    @IgnoredOnCi
    public void test_7() {
        run(7);
    }

    @Test
    @IgnoredOnCi
    public void test_10() {
        run(10);
    }

    @Test
    @IgnoredOnCi
    public void test_15() {
        run(15);
    }

    @Test
    @IgnoredOnCi
    public void test_20() {
        run(20);
    }

    @Test
    @IgnoredOnCi
    public void test_30() {
        run(30);
    }

    @Test
    @IgnoredOnCi
    public void test_50() {
        run(50);
    }

//    @Test
    @IgnoredOnCi
    public void test_100() {
        run(100);
    }

//    @Test
    @IgnoredOnCi
    public void test_150() {
        run(150);
    }

    private void run(int size) {
//        warmup();

        System.out.println("Benchmarking with a cluster size of " + size + " over " + NO_RUNS + " runs");
//        double[] results = benchmarkRaw(size, NO_RUNS);
//        System.out.println(Arrays.toString(results));

        Results results = benchmarkResults(size, 1);
        System.out.println(results.toString(size, 1));

        System.out.println("------------------------------------------------------------------------------------");
    }

}

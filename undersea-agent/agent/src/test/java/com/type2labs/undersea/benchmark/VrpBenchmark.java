package com.type2labs.undersea.benchmark;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.missions.GeneratedMissionImpl;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VrpBenchmark {

    private static final Logger logger = LogManager.getLogger(VrpBenchmark.class);
    private static final int NO_RUNS = 20;
    private static final File file = new File("VrpBenchmark.txt");
    private static final FileWriter fr;
    private static final BufferedWriter br;
    private static final PrintWriter pr;

    static {
        try {
            fr = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        br = new BufferedWriter(fr);
        pr = new PrintWriter(br);
    }

    @BeforeClass
    public static void testInit() {
        try {
            new VehicleRoutingOptimiser();
        } catch (Exception e) {
            Assert.fail("Unable to initialise mission planner");
        }
    }

    @AfterClass
    public static void closeResources() {
        try {
            pr.close();
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_s3_r50() {
        run(3, 50);
    }

    @Test
    public void test_s5_r50() {
        run(5, 50);
    }

    @Test
    public void test_s7_r50() {
        run(7, 50);
    }

    @Test
    public void test_s3_r30() {
        run(3, 30);
    }

    @Test
    public void test_s5_r30() {
        run(5, 30);
    }

    @Test
    public void test_s7_r30() {
        run(7, 30);
    }

    @Test
    public void test_s3_r20() {
        run(3, 20);
    }

    @Test
    public void test_s5_r20() {
        run(100, 20);
    }

    @Test
    public void test_s7_r20() {
        run(7, 20);
    }

    @Test
    public void test_s10_r20() {
        run(10, 20);
    }

    @Test
    public void test_s15_r20() {
        run(15, 20);
    }

    @Test
    public void test_s20_r20() {
        run(20, 20);
    }

    private void warmup() {
        benchmark(5, 50);
    }

    private void run(int clusterSize, int sensorRange) {
        warmup();

        logger.info("Benchmarking with a cluster size of " + clusterSize + " with a maximum sensor range of " + sensorRange + " over " + NO_RUNS + " runs");

        double[] results = benchmark(clusterSize, sensorRange);
//        System.out.println(results.toString(clusterSize, NO_RUNS, sensorRange));

//        pr.println(ZonedDateTime.now().toString() + ": " + results.toString(clusterSize, NO_RUNS, sensorRange));

        pr.println("Leader election results (size=" + clusterSize + ", runs=" + NO_RUNS + ", sensorRange= " + sensorRange + ")" + Arrays.toString(results));
        pr.flush();

        System.out.println("------------------------------------------------------------------------------------");
    }

    private double[] benchmark(int clusterSize, int sensorRange) {
        if (clusterSize == 1) {
            throw new IllegalArgumentException("Cluster size must be greater than 1");
        }

        MissionPlanner missionPlanner = new VehicleRoutingOptimiser();
        MissionManager missionManager = new MoosMissionManagerImpl(missionPlanner);
        RuntimeConfig config = new RuntimeConfig();
        AgentFactory agentFactory = new AgentFactory();
        Agent agent = agentFactory.createWith(config);

        double[][] area = Utility.stringTo2dDoubleArray("0 0; 150 0; 150 -140; 0 -140;");

        List<Agent> agents = agentFactory.createN(clusterSize - 1);
        agentFactory.populateCluster(agent, agents);
        agents.add(agent);

        MissionParameters missionParameters = new MissionParametersImpl(1, area, sensorRange);
        missionParameters.setClients(new ArrayList<>(agent.clusterClients().values()));
        missionParameters.getClients().add(new Client() {

            @Override
            public String name() {
                return agent.name();
            }

            @Override
            public ClusterState.ClientState state() {
                return new ClusterState.ClientState(this, new Random().nextInt(100));
            }

            @Override
            public PeerId peerId() {
                return agent.peerId();
            }

            @Override
            public InetSocketAddress socketAddress() {
                return new InetSocketAddress(0);
            }

            @Override
            public void shutdown() {

            }

            @Override
            public boolean isSelf() {
                return true;
            }
        });

        config.missionParameters(missionParameters);
        missionManager.initialise(agent);

        double[] results = new double[NO_RUNS];

        try {
            for (int i = 0; i < NO_RUNS; i++) {
                logger.info("Run: " + (i + 1));

                long startTime = System.currentTimeMillis();

                GeneratedMissionImpl mission = (GeneratedMissionImpl) missionPlanner.generate();

                long finishTime = System.currentTimeMillis();
                long runDuration = finishTime - startTime;

                logger.info("Run: " + (i + 1) + " duration: " + runDuration + "ms");

                results[i] = runDuration;

                if (mission.getAssignment() == null) {
                    Assert.fail("No solution found");
                }

                missionPlanner.print(mission);
            }
        } catch (PlannerException e) {
            e.printStackTrace();
            Assert.fail();
        }

//        return compileResults(results);
        return results;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Results compileResults(double[] results) {
        double min = Arrays.stream(results).min().getAsDouble();
        double max = Arrays.stream(results).max().getAsDouble();
        double average = Arrays.stream(results).average().getAsDouble();

        return new Results(min, max, average);
    }

    static class Results {
        double min;
        double max;
        double average;

        public Results(double min, double max, double average) {
            this.min = min;
            this.max = max;
            this.average = average;
        }

        public String toString(int size, int noRuns, int sensorRange) {
            return "Leader election results (size=" + size + ", runs=" + noRuns + ", sensorRange= " + sensorRange + ") {" +
                    "min time=" + min + "ms" +
                    ", max time=" + max + "ms" +
                    ", average time=" + average + "ms" +
                    '}';
        }
    }

}

package com.type2labs.undersea.agent.monitor;

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.agent.AgentStatus;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.UnderseaRuntimeConfig;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorImpl;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.utilities.testing.IgnoredOnCi;
import com.type2labs.undersea.utilities.testing.UnderseaRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * These tests are not for CI
 */
@RunWith(UnderseaRunner.class)
public class VisualiserTest {

    private UnderseaAgent createAgent(int port) {
        String name = UUID.randomUUID().toString();

        UnderseaRuntimeConfig config = new UnderseaRuntimeConfig();
        RaftNodeImpl raftNode = new RaftNodeImpl(
                new RaftClusterConfig(config)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(raftNode);

        MissionManager missionManager = new MoosMissionManagerImpl(new VehicleRoutingOptimiser());
        serviceManager.registerService(missionManager);

        config.missionParameters(new MissionParametersImpl(0, new double[0][0], 10));
        config.enableVisualiser(true);

        SubsystemMonitor subsystemMonitor = new SubsystemMonitorImpl();
        UnderseaAgent underseaAgent = new UnderseaAgent(config,
                name,
                serviceManager,
                new AgentStatus(name, new ArrayList<>()),
                PeerId.newId());

        VisualiserClientImpl visualiserClient = new VisualiserClientImpl();
        subsystemMonitor.setVisualiser(visualiserClient);
        visualiserClient.initialise(underseaAgent);

        raftNode.initialise(underseaAgent);

        serviceManager.registerService(subsystemMonitor);

        underseaAgent.start();

        return underseaAgent;
    }

    @Test
    public void ignored() {

    }

    @Test
    @IgnoredOnCi
    public void testConnection() throws InterruptedException {
        new Visualiser();

        for (int i = 0; i < 100; i++) {
            createAgent(0);
        }

        Thread.sleep(10000);
    }

    @Test
    @IgnoredOnCi
    public void testDataUpdate() throws InterruptedException {
        new Visualiser();

        UnderseaAgent leader = createAgent(0);
        RaftNodeImpl raftNode = (RaftNodeImpl) leader.getConsensusAlgorithm();
        List<UnderseaAgent> agents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UnderseaAgent t = createAgent(0);
            agents.add(t);
            raftNode.state().discoverNode((RaftNode) t.getConsensusAlgorithm());
        }

        Thread.sleep(3000);
        raftNode.toLeader(0);
        Thread.sleep(30000);
        System.out.println("Shutting down");
    }

}
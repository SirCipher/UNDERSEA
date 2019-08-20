package com.type2labs.undersea.agent.service;

import com.google.common.collect.Sets;
import com.type2labs.undersea.common.monitor.MonitorImpl;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.junit.Test;

import java.util.Set;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MissionDistributionTest {

    @Test
    public void test() {
        int count = 3;
        Set<AgentService> services = Sets.newHashSet(
                new MonitorImpl(),
                new VehicleRoutingOptimiser()
        );

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count, services)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> {
                for (RaftNodeImpl node : localAgentGroup.getRaftNodes()) {
                    ClusterState clusterState = node.state().clusterState();

                    assertNotNull(clusterState);
                    assertEquals(count-1, clusterState.getMembers().size());
                }
            }, 5);

            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


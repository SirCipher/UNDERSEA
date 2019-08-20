package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.junit.Test;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GroupTest {

    @Test
    public void testAcquireStatusTask() {
        int count = 3;

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> {
                for (RaftNodeImpl node : localAgentGroup.getRaftNodes()) {
                    ClusterState clusterState = node.state().clusterState();

                    assertNotNull(clusterState);
                    assertEquals(count, clusterState.getMembers().size());
                }
            }, 5);


            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // TODO: 19/08/2019
    @Test
    public void testElection() {
        int count = 3;

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            RaftNodeImpl raftNode = (RaftNodeImpl) localAgentGroup.getLeaderNode();
            raftNode.toLeader();
        }
    }

}

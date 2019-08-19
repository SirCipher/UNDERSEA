package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.junit.Test;

import static com.type2labs.undersea.prospect.util.TestUtil.assertTrueEventually;
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
                    assertNotNull(node.poolInfo());
                    assertEquals(count - 1, node.poolInfo().getMembers().size());
                }
            }, 5);
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

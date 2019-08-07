package com.type2labs.undersea.agent.consensus;

import com.type2labs.undersea.agent.consensus.impl.RaftNodeImpl;
import org.junit.Test;

import static com.type2labs.undersea.agent.consensus.util.TestUtil.assertTrueEventually;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GroupTest {


    @Test
    public void testAcquireStatusTask() {
        int count = 10;

        LocalAgentGroup localAgentGroup = new LocalAgentGroup(count);
        localAgentGroup.initDiscovery();
        localAgentGroup.start();

        assertTrueEventually(() -> {
            for (RaftNodeImpl node : localAgentGroup.getRaftNodes()) {
                assertNotNull(node.poolInfo());
                assertEquals(count - 1, node.poolInfo().getAgentInfo().size());
            }

        }, 5);

        localAgentGroup.shutdown();
    }

    @Test
    public void testElection() {
        int count = 10;

        LocalAgentGroup localAgentGroup = new LocalAgentGroup(count);
        localAgentGroup.initDiscovery();
        localAgentGroup.start();

        localAgentGroup.shutdown();
    }

}

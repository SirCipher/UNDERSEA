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

package com.type2labs.undersea.prospect;

import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Test;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static org.junit.Assert.*;

public class GroupTest {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

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

    @Test
    public void testElection() {
        int count = 3;

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);
        }
    }

    @Test
    public void testElection_2() {
        int count = 2;

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);
        }
    }

    @Test
    public void testReelection() throws InterruptedException {
        int count = 3;

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);
            Thread.sleep(1000);

            RaftNodeImpl leaderNode = (RaftNodeImpl) localAgentGroup.getLeaderNode();
            System.out.println("Removing leader: " + leaderNode.parent().peerId());

            localAgentGroup.removeNode(leaderNode);

            leaderNode.parent().serviceManager().shutdownServices();

            assertTrueEventually(() -> {
                RaftNodeImpl newLeader = (RaftNodeImpl) localAgentGroup.getLeaderNode();

                assertNotNull(newLeader);
                assertNotEquals(newLeader, leaderNode);
            }, 120);

            Thread.sleep(10000);
        }
    }

}

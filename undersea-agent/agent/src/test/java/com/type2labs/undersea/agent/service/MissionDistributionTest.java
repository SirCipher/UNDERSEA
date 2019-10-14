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

package com.type2labs.undersea.agent.service;

import com.google.common.collect.Sets;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Test;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;
import static junit.framework.TestCase.assertNotNull;

public class MissionDistributionTest {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    @Test
    public void test() throws InterruptedException {
        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(5, Sets.newHashSet(SubsystemMonitorSpoofer.class),
                false, true)) {
            for (RaftNode raftNode : localAgentGroup.getRaftNodes()) {
                Agent agent = raftNode.parent();
                agent.serviceManager().registerService(new MoosMissionManagerImpl(new VehicleRoutingOptimiser()));
            }

            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> assertNotNull(localAgentGroup.getLeaderNode()), 120);

            Thread.sleep(5000);
        }
    }
}



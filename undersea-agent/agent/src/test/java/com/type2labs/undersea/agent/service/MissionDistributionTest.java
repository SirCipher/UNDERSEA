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

import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.impl.LocalAgentGroup;
import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;

import static com.type2labs.undersea.utilities.testing.TestUtil.assertTrueEventually;

public class MissionDistributionTest {

    //    @Test
    public void test() {

        int count = 5;
//        Set<Class<? extends AgentService>> services = Sets.newHashSet(
//                new MoosMissionManagerImpl(new VehicleRoutingOptimiser()),
//                new NoNetworkInterfaceImpl()
//        );

        try (LocalAgentGroup localAgentGroup = new LocalAgentGroup(count, null, true, false)) {
            localAgentGroup.doManualDiscovery();
            localAgentGroup.start();

            assertTrueEventually(() -> {
                for (ConsensusNodeImpl node : localAgentGroup.getNodes()) {
                    ClusterState clusterState = node.state().clusterState();
                }
            }, 5);

            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



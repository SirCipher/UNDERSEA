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

package com.type2labs.undersea.agent.monitor;

import com.type2labs.undersea.agent.impl.UnderseaAgent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.config.RuntimeConfig;
import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorImpl;
import com.type2labs.undersea.common.monitor.impl.VisualiserClientImpl;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.monitor.Visualiser;
import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;
import com.type2labs.undersea.prospect.model.ConsensusNode;
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

        RuntimeConfig config = new RuntimeConfig();
        ConsensusNodeImpl consensusNode = new ConsensusNodeImpl(
                new ConsensusClusterConfig(config)
        );

        ServiceManager serviceManager = new ServiceManager();
        serviceManager.registerService(consensusNode);
        serviceManager.registerService(new LogServiceImpl());

        MissionManager missionManager = new MoosMissionManagerImpl(new VehicleRoutingOptimiser());
        serviceManager.registerService(missionManager);

        config.missionParameters(new MissionParametersImpl(0, new double[0][0], 10));
        config.enableVisualiser(true);

        SubsystemMonitor subsystemMonitor = new SubsystemMonitorImpl();
        UnderseaAgent underseaAgent = new UnderseaAgent(config,
                name,
                serviceManager,
                PeerId.newId());

        VisualiserClientImpl visualiserClient = new VisualiserClientImpl();
        subsystemMonitor.setVisualiser(visualiserClient);
        visualiserClient.initialise(underseaAgent);

        consensusNode.initialise(underseaAgent);

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
        ConsensusNodeImpl consensusNode = (ConsensusNodeImpl) leader.getConsensusAlgorithm();
        List<UnderseaAgent> agents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UnderseaAgent t = createAgent(0);
            agents.add(t);
            consensusNode.state().discoverNode((ConsensusNode) t.getConsensusAlgorithm());
        }

        Thread.sleep(3000);
        consensusNode.toLeader(0);
        Thread.sleep(30000);
        System.out.println("Shutting down");
    }

}
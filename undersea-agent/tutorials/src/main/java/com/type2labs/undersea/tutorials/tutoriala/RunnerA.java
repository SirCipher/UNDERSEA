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

package com.type2labs.undersea.tutorials.tutoriala;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.agent.AgentState;
import com.type2labs.undersea.common.consensus.RaftClusterConfig;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class RunnerA {

    static {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
        loggerConfig.setLevel(Level.INFO);
        ctx.updateLoggers();
    }

    public static void main(String[] args) {
        AgentFactory agentFactory = new AgentFactory();
        List<Agent> agents = agentFactory.createN(5);

        for (Agent agent : agents) {
            ServiceManager serviceManager = agent.serviceManager();
            RaftNodeImpl raftNode = new RaftNodeImpl(new RaftClusterConfig());
            raftNode.registerCallback(new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> {
                Transaction transaction = new Transaction.Builder(agent)
                        .forService(MissionManager.class)
                        .withStatus(LifecycleEvent.ELECTED_LEADER)
                        .usingExecutorService(raftNode.getListeningExecutorService())
                        .invokedBy(raftNode)
                        .build();

                Set<ListenableFuture<?>> futures = agent.serviceManager().commitTransaction(transaction);

                for (ListenableFuture<?> future : futures) {
                    Futures.addCallback(future, new FutureCallback<Object>() {
                        @Override
                        public void onSuccess(@Nullable Object result) {
                            raftNode.distributeMission((GeneratedMission) result);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            throw new RuntimeException(t);
                        }

                    }, raftNode.getSingleThreadScheduledExecutor());
                }
            }));

            serviceManager.registerService(raftNode);
            serviceManager.registerService(new SubsystemMonitorSpoofer());
            serviceManager.registerService(new MissionManagerSample());
            serviceManager.registerService(new LogServiceImpl());

            serviceManager.startServices();

            agent.state().setState(AgentState.State.ACTIVE);
        }

        for (Agent a : agents) {
            for (Agent b : agents) {
                if (a != b) {
                    RaftNodeImpl raftNodeA = a.serviceManager().getService(RaftNodeImpl.class);
                    RaftNodeImpl raftNodeB = b.serviceManager().getService(RaftNodeImpl.class);

                    raftNodeA.state().discoverNode(raftNodeB);
                }
            }
        }

    }

}

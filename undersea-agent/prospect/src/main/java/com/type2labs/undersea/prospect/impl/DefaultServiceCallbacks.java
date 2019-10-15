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

package com.type2labs.undersea.prospect.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
<<<<<<< HEAD
=======
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
>>>>>>> c35af36b162c0461ecfe03f2eb037933002a9cd0
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Set;

public class DefaultServiceCallbacks {

    private static final Logger logger = LogManager.getLogger(DefaultServiceCallbacks.class);

<<<<<<< HEAD
    public static ServiceCallback defaultMissionCallback(Agent agent, ConsensusNodeImpl consensusNode, ConsensusClusterConfig config) {
=======
    public static ServiceCallback defaultMissionCallback(Agent agent, RaftNodeImpl raftNode, RaftClusterConfig config) {
>>>>>>> c35af36b162c0461ecfe03f2eb037933002a9cd0
        return new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> {
            MissionParameters parameters = config.getRuntimeConfig().missionParameters();

            parameters.setClients(new ArrayList<>(agent.clusterClients().values()));
            parameters.getClients().add(consensusNode.self());

            Transaction transaction = new Transaction.Builder(agent)
                    .forService(MissionManager.class)
                    .withStatus(LifecycleEvent.ELECTED_LEADER)
<<<<<<< HEAD
                    .usingExecutorService(consensusNode.getListeningExecutorService())
                    .invokedBy(consensusNode)
=======
                    .usingExecutorService(MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(agent, logger)))
                    .invokedBy(raftNode)
>>>>>>> c35af36b162c0461ecfe03f2eb037933002a9cd0
                    .build();

            Set<ListenableFuture<?>> futures = agent.serviceManager().commitTransaction(transaction);

            for (ListenableFuture<?> future : futures) {
                Futures.addCallback(future, new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                        consensusNode.distributeMission((GeneratedMission) result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        logger.error(t);
                        throw new RuntimeException(t);
                    }

<<<<<<< HEAD
                }, consensusNode.getSingleThreadScheduledExecutor());
=======
                }, MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(raftNode.parent(),
                        logger)));
>>>>>>> c35af36b162c0461ecfe03f2eb037933002a9cd0
            }
        });
    }

}

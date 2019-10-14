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

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.consensus.NoConsensusAlgorithm;
import com.type2labs.undersea.common.logger.LogServiceImpl;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.missionplanner.manager.MoosMissionManagerImpl;
import com.type2labs.undersea.missionplanner.planner.vrp.VehicleRoutingOptimiser;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.Set;

public class ThreadTest {

    private static final Logger logger = LogManager.getLogger(ThreadTest.class);


    @Test
    public void doTest() throws InterruptedException {
        Agent agent = new AgentFactory().create();
        ServiceManager serviceManager = agent.serviceManager();
        LogServiceImpl logService = new LogServiceImpl();

        serviceManager.registerService(new NoConsensusAlgorithm());
        serviceManager.registerService(new MoosMissionManagerImpl(new VehicleRoutingOptimiser()));
        serviceManager.registerService(logService);
        serviceManager.startServices();

        Transaction transaction = new Transaction.Builder(agent)
                .forService(LogServiceImpl.class)
                .withStatus(LifecycleEvent.ELECTED_LEADER)
                .usingExecutorService(MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(agent, logger)))
                .invokedBy(logService)
                .build();

        Set<ListenableFuture<?>> futures = agent.serviceManager().commitTransaction(transaction);

        for (ListenableFuture<?> future : futures) {
            Futures.addCallback(future, new FutureCallback<Object>() {
                @Override
                public void onSuccess(@Nullable Object result) {
                    logger.info(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    logger.error(t);
                }

            }, MoreExecutors.listeningDecorator(MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(agent, logger))));
        }

        Thread.sleep(5000);
    }


}

package com.type2labs.undersea.prospect.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Set;

public class DefaultCallbacks {

    public static ServiceCallback defaultMissionCallback(Agent agent, RaftNodeImpl raftNode, RaftClusterConfig config) {
        return new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> {
            MissionParameters parameters = config.getUnderseaRuntimeConfig().missionParameters();

            parameters.setClients(new ArrayList<>(agent.clusterClients().values()));
            parameters.getClients().add(raftNode.self());

            Transaction transaction = new Transaction.Builder(agent)
                    .forService(MissionManager.class)
                    .withStatus(LifecycleEvent.ELECTED_LEADER)
                    .usingExecutorService(raftNode.getListeningExecutorService())
                    .invokedBy(raftNode)
                    .build();

            Set<ListenableFuture<?>> futures = agent.services().commitTransaction(transaction);

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
        });
    }

}

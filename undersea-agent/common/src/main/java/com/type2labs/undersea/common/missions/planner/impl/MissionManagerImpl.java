package com.type2labs.undersea.common.missions.planner.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionStatusCode;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class MissionManagerImpl implements MissionManager {

    private static final Logger logger = LogManager.getLogger(MissionManagerImpl.class);
    private final MissionPlanner missionPlanner;
    private Agent agent;
    private List<Task> tasks = new ArrayList<>();
    private GeneratedMission missionAssigned;

    public MissionManagerImpl(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
    }

    @Override
    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean missionHasBeenAssigned() {
        return tasks != null && tasks.size() > 0;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return missionPlanner;
    }

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {

    }

    /**
     * Execues a given {@link Transaction} on the mission planner. Supported {@link TransactionStatusCode}s are:
     * {@link TransactionStatusCode#ELECTED_LEADER} - which will generate a mission in accordance with the current
     * cluster state.
     *
     * @param transaction to execute on the mission planner
     * @return a {@link ListenableFuture} returned by the provided {@link java.util.concurrent.ExecutorService}
     */
    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        logger.info("Received transaction: " + transaction);
        TransactionStatusCode statusCode = (TransactionStatusCode) transaction.getStatusCode();

        if (statusCode == TransactionStatusCode.ELECTED_LEADER) {
            return transaction.getExecutorService().submit(() -> {
                try {
                    GeneratedMission generatedMission = missionPlanner.generate();
                    missionPlanner.print(generatedMission);
                    return generatedMission;
                } catch (PlannerException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            throw new NotSupportedException(MissionManagerImpl.class.getSimpleName()
                    + " does not support status code: " + statusCode);
        }
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        missionPlanner.initialise(parentAgent);
    }

    @Override
    public Agent parent() {
        return agent;
    }
}

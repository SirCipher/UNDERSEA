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

    public MissionManagerImpl(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return missionPlanner;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        logger.info("Received transaction: " + transaction);
        TransactionStatusCode statusCode = (TransactionStatusCode) transaction.getStatusCode();

        if (statusCode == TransactionStatusCode.ELECTED_LEADER) {
            return transaction.getExecutorService().submit(() -> {
                try {
                    GeneratedMission mission = missionPlanner.generate();
                    missionPlanner.print(mission);
                    return mission;
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

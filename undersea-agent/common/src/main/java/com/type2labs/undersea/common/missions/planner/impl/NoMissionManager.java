package com.type2labs.undersea.common.missions.planner.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.service.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class NoMissionManager implements MissionManager {

    @Override
    public MissionPlanner missionPlanner() {
        return new NoMissionPlanner();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    @Override
    public boolean missionHasBeenAssigned() {
        return false;
    }

    @Override
    public void addTasks(List<Task> tasks) {

    }

    @Override
    public void cancelAllTasks() {

    }

    @Override
    public void run() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }
}
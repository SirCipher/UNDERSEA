package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.service.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class NoMissionPlanner implements MissionPlanner {

    @Override
    public Mission generate(MissionParameters missionParameters) throws PlannerException {
        return null;
    }

    @Override
    public void print(Mission mission) {

    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public ScheduledFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void run() {

    }

}

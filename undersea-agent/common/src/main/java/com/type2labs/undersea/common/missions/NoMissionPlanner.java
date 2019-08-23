package com.type2labs.undersea.common.missions;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.service.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NoMissionPlanner implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(NoMissionPlanner.class);



    @Override
    public GeneratedMission generate() throws PlannerException {
        return null;
    }

    @Override
    public void print(GeneratedMission generatedMission) {

    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        logger.info("Received transaction: " + transaction);
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public Agent parent() {
        return null;
    }

    @Override
    public void run() {

    }

}

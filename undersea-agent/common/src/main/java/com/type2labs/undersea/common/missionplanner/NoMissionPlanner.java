package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.service.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class NoMissionPlanner implements MissionPlanner {

    private static final Logger logger = LogManager.getLogger(NoMissionPlanner.class);

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
        logger.info("Received transaction: " + transaction);
        return null;
    }

    @Override
    public void initialise(Agent parentAgent) {

    }

    @Override
    public void run() {

    }

}

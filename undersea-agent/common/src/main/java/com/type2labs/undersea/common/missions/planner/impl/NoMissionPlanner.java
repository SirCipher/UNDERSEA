package com.type2labs.undersea.common.missions.planner.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void initialise(Agent parentAgent) {

    }


}

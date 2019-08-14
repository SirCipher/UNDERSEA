package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

public interface MissionPlanner extends AgentService {

    /**
     * Generates a mission meeting the given criteria
     *
     * @param missionParameters that the mission must conform to
     * @return a generated mission
     * @throws PlannerException if the arguments do not match the expected MATLAB parameters
     */
    Mission generate(MissionParameters missionParameters) throws PlannerException;

    /**
     * Print a given mission
     *
     * @param mission to print
     */
    void print(Mission mission);

    List<Task> getTasks();

}

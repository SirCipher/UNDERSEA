package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

public interface MissionPlanner extends AgentService {

    /**
     * Generates a mission meeting the given criteria. Mission parameters are retrieved from the
     * {@link com.type2labs.undersea.common.config.UnderseaRuntimeConfig}
     *
     * @return a generated mission
     * @throws PlannerException if the arguments do not match the expected MATLAB parameters
     */
    GeneratedMission generate() throws PlannerException;

    /**
     * Print a given mission
     *
     * @param generatedMission to print
     */
    void print(GeneratedMission generatedMission);

    List<Task> getTasks();

}

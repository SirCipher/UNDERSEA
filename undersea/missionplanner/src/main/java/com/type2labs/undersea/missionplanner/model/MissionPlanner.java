package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.missionplanner.exception.PlannerException;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public interface MissionPlanner {

    /**
     * Generates a mission meeting the given criteria
     *
     * @param missionParameters that the mission must conform to
     * @param polygon           the shape to cover
     * @return a generated mission
     * @throws PlannerException if the arguments do not match the expected MATLAB parameters
     */
    Mission generate(MissionParameters missionParameters, int[][] polygon) throws PlannerException;

    /**
     * Print a given mission
     *
     * @param mission to print
     */
    void print(Mission mission);

}

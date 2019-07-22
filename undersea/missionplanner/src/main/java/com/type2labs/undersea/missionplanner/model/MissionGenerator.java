package com.type2labs.undersea.missionplanner.model;

import com.type2labs.undersea.missionplanner.PlannerException;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public interface MissionGenerator {

    List<?> run(int[][] polygon) throws PlannerException;

    List<?> createPlan(int[][] polygon) throws PlannerException;

    double[][] decompose(int[][] polygon) throws PlannerException;

    List<?> replanMission() throws PlannerException;

}

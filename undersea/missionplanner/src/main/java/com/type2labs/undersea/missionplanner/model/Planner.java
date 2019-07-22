package com.type2labs.undersea.missionplanner.model;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public interface Planner {

    void generate(PlanDataModel model);

    void printPlan(RoutingModel routing, RoutingIndexManager manager, Assignment solution);

}

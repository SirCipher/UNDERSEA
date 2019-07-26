package com.type2labs.undersea.missionplanner.model;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class Mission {

    private final PlanDataModel planDataModel;
    private final Assignment assignment;
    private final RoutingModel routingModel;
    private final RoutingIndexManager routingIndexManager;
    // TODO: Add node type
    // Each node should have an assignee, coordinates, and state (visited, POI, etc)
    private final List<?> nodes;
    private final MissionParameters missionParameters;

    public MissionParameters getMissionParameters() {
        return missionParameters;
    }

    public Mission(PlanDataModel planDataModel, Assignment assignment, RoutingModel routingModel,
                   RoutingIndexManager routingIndexManager, List<?> nodes, MissionParameters missionParameters) {
        this.planDataModel = planDataModel;
        this.assignment = assignment;
        this.routingModel = routingModel;
        this.routingIndexManager = routingIndexManager;
        this.nodes = nodes;
        this.missionParameters = missionParameters;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public List<?> getNodes() {
        return nodes;
    }

    public PlanDataModel getPlanDataModel() {
        return planDataModel;
    }

    public RoutingIndexManager getRoutingIndexManager() {
        return routingIndexManager;
    }

    public RoutingModel getRoutingModel() {
        return routingModel;
    }
}
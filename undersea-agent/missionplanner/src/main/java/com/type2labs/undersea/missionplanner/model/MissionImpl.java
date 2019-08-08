package com.type2labs.undersea.missionplanner.model;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.models.missionplanner.Mission;
import com.type2labs.undersea.models.missionplanner.Task;
import com.type2labs.undersea.models.missionplanner.TaskStatus;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionImpl implements Mission {

    private final PlanDataModel planDataModel;
    private final Assignment assignment;
    private final RoutingModel routingModel;
    private final RoutingIndexManager routingIndexManager;
    // TODO: Add node type
    // Each node should have an assignee, coordinates, and state (visited, POI, etc)
    private final List<Task> tasks;
    private final MissionParametersImpl missionParametersImpl;

    public MissionImpl(PlanDataModel planDataModel, Assignment assignment, RoutingModel routingModel,
                       RoutingIndexManager routingIndexManager, List<Task> tasks,
                       MissionParametersImpl missionParametersImpl) {
        this.planDataModel = planDataModel;
        this.assignment = assignment;
        this.routingModel = routingModel;
        this.routingIndexManager = routingIndexManager;
        this.tasks = tasks;
        this.missionParametersImpl = missionParametersImpl;
    }

    public MissionParametersImpl getMissionParametersImpl() {
        return missionParametersImpl;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public List<?> getTasks() {
        return tasks;
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

    @Override
    public List<Task> tasks() {
        return tasks;
    }

    @Override
    public double progress() {
        return tasks.stream().filter(t -> t.taskStatus() == TaskStatus.COMPLETED).count() / tasks.size();
    }

}

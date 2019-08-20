package com.type2labs.undersea.missionplanner.model;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.common.missionplanner.GeneratedMission;
import com.type2labs.undersea.common.missionplanner.MissionParameters;
import com.type2labs.undersea.common.missionplanner.Task;
import com.type2labs.undersea.common.missionplanner.TaskStatus;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class GeneratedMissionImpl implements GeneratedMission {

    private final PlanDataModel planDataModel;
    private final Assignment assignment;
    private final RoutingModel routingModel;
    private final RoutingIndexManager routingIndexManager;
    // TODO: Add node type
    // Each node should have an assignee, coordinates, and state (visited, POI, etc)
    private final List<Task> tasks;
    private final MissionParameters missionParameters;


    public GeneratedMissionImpl(PlanDataModel planDataModel, Assignment assignment, RoutingModel routingModel,
                                RoutingIndexManager routingIndexManager, List<Task> tasks,
                                MissionParameters missionParameters) {
        this.planDataModel = planDataModel;
        this.assignment = assignment;
        this.routingModel = routingModel;
        this.routingIndexManager = routingIndexManager;
        this.tasks = tasks;
        this.missionParameters = missionParameters;
    }

    public MissionParameters getMissionParameters() {
        return missionParameters;
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

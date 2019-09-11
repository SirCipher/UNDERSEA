package com.type2labs.undersea.common.missions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;
import com.type2labs.undersea.common.missions.task.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class GeneratedMissionImpl implements GeneratedMission {

    @JsonIgnore
    private PlanDataModel planDataModel;
    @JsonIgnore
    private Assignment assignment;
    @JsonIgnore
    private RoutingModel routingModel;
    @JsonIgnore
    private RoutingIndexManager routingIndexManager;
    @JsonIgnore
    private MissionParameters missionParameters;
    private List<AgentMission> missions = new ArrayList<>();

    @SuppressWarnings("unused")
    public GeneratedMissionImpl() {

    }

    public GeneratedMissionImpl(PlanDataModel planDataModel, Assignment assignment, RoutingModel routingModel,
                                RoutingIndexManager routingIndexManager,
                                MissionParameters missionParameters) {
        this.planDataModel = planDataModel;
        this.assignment = assignment;
        this.routingModel = routingModel;
        this.routingIndexManager = routingIndexManager;
        this.missionParameters = missionParameters;
    }

    @Override
    public void addAgentMission(AgentMission agentMission) {
        this.missions.add(agentMission);
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public MissionParameters getMissionParameters() {
        return missionParameters;
    }

    public void setMissionParameters(MissionParameters missionParameters) {
        this.missionParameters = missionParameters;
    }

    public PlanDataModel getPlanDataModel() {
        return planDataModel;
    }

    public void setPlanDataModel(PlanDataModel planDataModel) {
        this.planDataModel = planDataModel;
    }

    public RoutingIndexManager getRoutingIndexManager() {
        return routingIndexManager;
    }

    public void setRoutingIndexManager(RoutingIndexManager routingIndexManager) {
        this.routingIndexManager = routingIndexManager;
    }

    public RoutingModel getRoutingModel() {
        return routingModel;
    }

    public void setRoutingModel(RoutingModel routingModel) {
        this.routingModel = routingModel;
    }

    @Override
    public double progress() {
//        return missions.stream().filter(t -> t.taskStatus() == TaskStatus.COMPLETED).count() / tasks.size();
        return 0;
    }

    public void setMissions(List<AgentMission> missions) {
        this.missions = missions;
    }

    @Override
    @JsonProperty("missions")
    public List<AgentMission> subMissions() {
        return missions;
    }

    @Override
    public List<Task> allTasks() {
        List<Task> allTasks = new ArrayList<>();

        for (AgentMission agentMission : missions) {
            allTasks.addAll(agentMission.getTasks());
        }

        return allTasks;
    }

    @Override
    public double[][] polygon() {
        return missionParameters.getPolygon();
    }


}

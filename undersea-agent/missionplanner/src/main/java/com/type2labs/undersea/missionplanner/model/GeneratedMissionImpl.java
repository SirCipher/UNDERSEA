package com.type2labs.undersea.missionplanner.model;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class GeneratedMissionImpl implements GeneratedMission {

    private final PlanDataModel planDataModel;
    private final Assignment assignment;
    private final RoutingModel routingModel;
    private final RoutingIndexManager routingIndexManager;
    private final MissionParameters missionParameters;
    private List<AgentMission> missions = new ArrayList<>();

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

    @Override
    public List<AgentMission> subMissions() {
        return missions;
    }

    @Override
    public double progress() {
//        return missions.stream().filter(t -> t.taskStatus() == TaskStatus.COMPLETED).count() / tasks.size();
        return 0;
    }

    public MissionParameters getMissionParameters() {
        return missionParameters;
    }

    public Assignment getAssignment() {
        return assignment;
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

package com.type2labs.undersea.common.missionplanner.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missionplanner.models.AgentMission;
import com.type2labs.undersea.common.missionplanner.models.Task;

import java.util.Arrays;
import java.util.List;

public class AgentMissionImpl implements AgentMission {

    private List<TaskImpl> tasks;
    @JsonIgnore
    private Client assignee;
    private double progress = 0;

    public AgentMissionImpl() {

    }

    public AgentMissionImpl(Client assignee, List<TaskImpl> tasks) {
        this.assignee = assignee;
        this.tasks = tasks;
    }

    public void setTasks(List<TaskImpl> tasks) {
        this.tasks = tasks;
    }

    public void setAssignee(Client assignee) {
        this.assignee = assignee;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public Client getAssignee() {
        return assignee;
    }

    @Override
    public List<TaskImpl> getTasks() {
        return tasks;
    }

    @Override
    public double getProgress() {
        return 0;
    }

    @Override
    public String toString() {
        return "AgentMissionImpl{" +
                "tasks=" + Arrays.toString(tasks.toArray()) +
                ", progress=" + progress +
                '}';
    }
}

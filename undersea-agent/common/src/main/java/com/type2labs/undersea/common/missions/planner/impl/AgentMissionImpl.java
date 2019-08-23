package com.type2labs.undersea.common.missions.planner.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;

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

    @Override
    public Client getAssignee() {
        return assignee;
    }

    public void setAssignee(Client assignee) {
        this.assignee = assignee;
    }

    @Override
    public List<TaskImpl> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskImpl> tasks) {
        this.tasks = tasks;
    }

    @Override
    public double getProgress() {
        return 0;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "AgentMissionImpl{" +
                "tasks=" + Arrays.toString(tasks.toArray()) +
                ", progress=" + progress +
                '}';
    }
}

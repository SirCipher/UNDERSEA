package com.type2labs.undersea.common.missions.planner.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.task.model.Task;

import java.util.Arrays;
import java.util.List;

public class AgentMissionImpl implements AgentMission {

    private List<Task> tasks;
    @JsonIgnore
    private Client assignee;
    private double progress = 0;
    private String points;
    private boolean started = false;

    public AgentMissionImpl() {

    }


    public AgentMissionImpl(Client assignee, List<Task> tasks) {
        this.assignee = assignee;
        this.tasks = tasks;
    }

    @Override
    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public Client getAssignee() {
        return assignee;
    }

    public void setAssignee(Client assignee) {
        this.assignee = assignee;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public double getProgress() {
        return 0;
    }

    @Override
    public boolean started() {
        return started;
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

package com.type2labs.undersea.common.missionplanner.impl;

import com.type2labs.undersea.common.missionplanner.models.Task;
import com.type2labs.undersea.common.missionplanner.models.TaskStatus;
import com.type2labs.undersea.common.missionplanner.models.TaskType;

import java.util.Arrays;

public class TaskImpl implements Task {

    private double[] coordinates;
    private double progress;
    private TaskType taskType;
    private TaskStatus taskStatus = TaskStatus.TODO;

    public TaskImpl(){

    }

    public TaskImpl(double[] coordinates, TaskType taskType) {
        this.coordinates = coordinates;
        this.taskType = taskType;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "TaskImpl{" +
                "coordinates=" + Arrays.toString(coordinates) +
                ", progress=" + progress +
                ", taskType=" + taskType +
                ", taskStatus=" + taskStatus +
                '}';
    }

    @Override
    public double[] getCoordinates() {
        return coordinates;
    }

}

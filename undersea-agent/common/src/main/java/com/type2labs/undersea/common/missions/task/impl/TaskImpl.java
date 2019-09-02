package com.type2labs.undersea.common.missions.task.impl;

import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.missions.task.model.TaskType;

import java.util.Arrays;

public class TaskImpl implements Task {

    // TODO: 02/09/2019 split to class
    private double[] coordinates;
    private double progress;
    private TaskType taskType;
    private TaskStatus taskStatus = TaskStatus.TODO;
    private String points;

    public TaskImpl() {

    }

    public String getPoints() {
        return points;
    }

    public TaskImpl(String points, TaskType taskType) {
        this.points = points;
        this.taskType = taskType;
    }

    public TaskImpl(double[] coordinates, TaskType taskType) {
        this.coordinates = coordinates;
        this.taskType = taskType;
    }

    @Override
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;

        if (taskStatus == TaskStatus.COMPLETED) {
            this.progress = 100;
        }
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
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

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

}

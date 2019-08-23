package com.type2labs.undersea.common.missions.task.model;

public interface Task {

    TaskType getTaskType();

    double getProgress();

    TaskStatus getTaskStatus();

    double[] getCoordinates();

}

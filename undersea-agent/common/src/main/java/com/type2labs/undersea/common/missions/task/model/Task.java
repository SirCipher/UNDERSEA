package com.type2labs.undersea.common.missions.task.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.type2labs.undersea.common.missions.task.impl.TaskImpl;

import java.util.UUID;

@JsonDeserialize(as = TaskImpl.class)
public interface Task {

    double[] getCoordinates();

    double getProgress();

    TaskStatus getTaskStatus();

    void setTaskStatus(TaskStatus taskStatus);

    TaskType getTaskType();

    UUID getUuid();

}

package com.type2labs.undersea.common.missionplanner.models;

import com.type2labs.undersea.common.cluster.Client;

public interface Task {

    TaskType getTaskType();

    double getProgress();

    TaskStatus getTaskStatus();

    double[] getCoordinates();

}

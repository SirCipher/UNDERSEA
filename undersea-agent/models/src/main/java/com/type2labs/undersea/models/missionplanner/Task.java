package com.type2labs.undersea.models.missionplanner;

public interface Task {

    TaskType taskType();

    double progress();

    TaskStatus taskStatus();

}

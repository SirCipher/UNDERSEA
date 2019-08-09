package com.type2labs.undersea.models.missionplanner;

public interface Task extends Runnable {

    TaskType taskType();

    double progress();

    TaskStatus taskStatus();



}

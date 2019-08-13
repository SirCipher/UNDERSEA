package com.type2labs.undersea.common.missionplanner;

public interface Task extends Runnable {

    TaskType taskType();

    double progress();

    TaskStatus taskStatus();


}

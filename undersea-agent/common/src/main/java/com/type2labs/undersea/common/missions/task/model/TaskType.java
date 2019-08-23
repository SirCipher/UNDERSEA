package com.type2labs.undersea.common.missions.task.model;

import com.type2labs.undersea.common.missions.task.executor.MeasureExecutor;
import com.type2labs.undersea.common.missions.task.executor.SurveyExecutor;
import com.type2labs.undersea.common.missions.task.executor.TaskExecutor;
import com.type2labs.undersea.common.missions.task.executor.WaypointExecutor;

public enum TaskType {

    WAYPOINT(new WaypointExecutor()),

    SURVEY(new SurveyExecutor()),

    MEASURE(new MeasureExecutor());

    private TaskExecutor taskExecutor;

    TaskType(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

}

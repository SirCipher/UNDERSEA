package com.type2labs.undersea.missionplanner.task.executor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.missions.task.model.TaskType;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class SurveyExecutor implements TaskExecutor {

    private Agent agent;

    public SurveyExecutor(Task task) {
        if (task.getTaskType() != TaskType.SURVEY) {
            throw new IllegalArgumentException("Only task type measure is supported");
        }
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }
}

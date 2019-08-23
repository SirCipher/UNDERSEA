package com.type2labs.undersea.common.missions.planner.model;

import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public interface MissionManager extends AgentService {

    void addTasks(List<Task> tasks);

    List<Task> getTasks();

    MissionPlanner missionPlanner();

}

package com.type2labs.undersea.common.missions.planner.model;

import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.service.AgentService;

import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public interface MissionManager extends AgentService {

    /**
     * Adds a task to the {@link com.type2labs.undersea.common.agent.Agent}
     *
     * @param tasks to assign to this agent
     */
    void addTasks(List<Task> tasks);

    /**
     * Returns all tasks that have been assigned to this agent
     *
     * @return the {@link Task}s assigned to the {@link com.type2labs.undersea.common.agent.Agent}
     */
    List<Task> getTasks();

    /**
     * Checks whether or not the agent has been assigned a mission
     *
     * @return
     */
    boolean missionHasBeenAssigned();

    /**
     * Returns the {@link MissionPlanner} implementation
     *
     * @return the assigned {@link MissionManager}
     */
    MissionPlanner missionPlanner();

}

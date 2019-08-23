package com.type2labs.undersea.common.missions.planner.model;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.task.model.impl.TaskImpl;

import java.util.List;

public interface AgentMission {

    Client getAssignee();

    List<TaskImpl> getTasks();

    double getProgress();

}

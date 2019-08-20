package com.type2labs.undersea.common.missionplanner.models;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missionplanner.impl.TaskImpl;

import java.util.List;

public interface AgentMission {

    Client getAssignee();

    List<TaskImpl> getTasks();

    double getProgress();

}

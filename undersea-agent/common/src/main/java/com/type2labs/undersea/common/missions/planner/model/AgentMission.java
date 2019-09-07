package com.type2labs.undersea.common.missions.planner.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.task.model.Task;

import javax.annotation.Nonnull;
import java.util.List;

@JsonDeserialize(as = AgentMissionImpl.class)
public interface AgentMission {

    Client getAssignee();

    List<Task> getTasks();

    double getProgress();

    boolean started();

    void setStarted(boolean started);

    String peerId();

}

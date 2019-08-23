package com.type2labs.undersea.common.missions.planner.model;


import java.util.List;

public interface GeneratedMission {

    void addAgentMission(AgentMission agentMission);

    List<AgentMission> subMissions();

    double progress();

}

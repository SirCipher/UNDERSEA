package com.type2labs.undersea.common.missionplanner.models;


import java.util.List;

public interface GeneratedMission {

    void addAgentMission(AgentMission agentMission);

    List<AgentMission> subMissions();

    double progress();

}

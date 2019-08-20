package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.agent.Agent;

import java.util.List;

public interface MissionParameters {

    int getStartingNode();

    List<Agent> getAgents();

    double[][] getPolygon();

    double getMinimumSensorRange();

    double[][] getCentroids();

}

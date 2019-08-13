package com.type2labs.undersea.common.missionplanner;

import com.type2labs.undersea.common.impl.AgentImpl;

import java.util.List;

public interface MissionParameters {

    int getStartingNode();

    List<AgentImpl> getAgents();

    double[][] getPolygon();

    double getMinimumSensorRange();

    double[][] getCentroids();

}

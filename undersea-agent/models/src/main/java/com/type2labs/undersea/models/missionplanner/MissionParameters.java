package com.type2labs.undersea.models.missionplanner;

import com.type2labs.undersea.models.impl.AgentImpl;

import java.util.List;

public interface MissionParameters {

    int getStartingNode();

    List<AgentImpl> getAgents();

    double[][] getPolygon();

    double getMinimumSensorRange();

    double[][] getCentroids();

}

package com.type2labs.undersea.common.missions.planner.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.missions.planner.impl.MissionParametersImpl;

import java.util.List;

@JsonDeserialize(as = MissionParametersImpl.class)
public interface MissionParameters {

    int getStartingNode();

    List<Client> getClients();

    void setClients(List<Client> clients);

    double[][] getPolygon();

    void setPolygon(double[][] polygon);

    double getMinimumSensorRange();

    double[][] getCentroids();

    void setCentroids(double[][] centroids);
}

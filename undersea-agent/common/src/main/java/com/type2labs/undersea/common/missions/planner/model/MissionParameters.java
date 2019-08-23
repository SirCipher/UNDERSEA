    package com.type2labs.undersea.common.missions.planner.model;

import com.type2labs.undersea.common.cluster.Client;

import java.util.List;

public interface MissionParameters {

    int getStartingNode();

    List<Client> getClients();

    double[][] getPolygon();

    double getMinimumSensorRange();

    double[][] getCentroids();

    void setCentroids(double[][] centroids);

    void setClients(List<Client> clients);
}

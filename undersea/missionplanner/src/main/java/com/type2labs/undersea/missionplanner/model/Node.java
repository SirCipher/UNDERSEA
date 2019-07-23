package com.type2labs.undersea.missionplanner.model;

//import com.type2labs.undersea.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Node {

    private Vector vector;
    private NodeStatus nodeStatus;
//    private List<Agent> agentsAssigned = new ArrayList<>();

    public enum NodeStatus {
        UNVISITED, VISITED, POINT_OF_INTEREST
    }

}

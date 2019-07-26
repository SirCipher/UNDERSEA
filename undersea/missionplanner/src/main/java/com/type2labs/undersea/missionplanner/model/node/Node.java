package com.type2labs.undersea.missionplanner.model.node;

import com.type2labs.undersea.missionplanner.model.Vector;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Node {

    private Vector vector;
    private NodeStatus nodeStatus;

    /**
     * Creates a new node for a given x and y position
     *
     * @param x coordinate
     * @param y coordinate
     */
    public Node(double x, double y) {
        this.vector = new Vector(x, y);
        this.nodeStatus = NodeStatus.UNVISITED;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public enum NodeStatus {
        UNVISITED, VISITED, POINT_OF_INTEREST
    }

}

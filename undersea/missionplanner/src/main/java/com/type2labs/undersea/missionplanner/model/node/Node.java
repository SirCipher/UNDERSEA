package com.type2labs.undersea.missionplanner.model.node;

import com.type2labs.undersea.Agent;
import com.type2labs.undersea.missionplanner.model.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Node {

    private Vector vector;
    private NodeStatus nodeStatus;
    private List<Agent> agentsAssigned = new ArrayList<>();

    /**
     * Creates a new node for a given x and y position
     *
     * @param x        coordinate
     * @param y        coordinate
     * @param assigned agent
     */
    public void Node(double x, double y, Agent assigned) {
        this.vector = new Vector(x, y);
        this.nodeStatus = NodeStatus.UNVISITED;
        this.agentsAssigned.add(assigned);
    }

    public void assignAgent(Agent agent) {
        this.agentsAssigned.add(agent);
    }

    public List<Agent> getAgentsAssigned() {
        return agentsAssigned;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public void unassignAgent(Agent agent) {
        this.agentsAssigned.remove(agent);
    }

    public enum NodeStatus {
        UNVISITED, VISITED, POINT_OF_INTEREST
    }

}

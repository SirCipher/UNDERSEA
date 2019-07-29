package com.type2labs.undersea.missionplanner.model.node;

import com.type2labs.undersea.utilities.PlannerUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Thomas Klapwijk on 2019-07-23.
 */
public class Node {

    private Node.Vector vector;
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

    public Vector getVector() {
        return vector;
    }

    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public enum NodeStatus {
        UNVISITED, VISITED, POINT_OF_INTEREST
    }

    public static Node randomLocalNode(double latLowerBound, double latUpperBound, double longLowerBound, double longUpperBound){
        double destLat = ThreadLocalRandom.current().nextDouble(latLowerBound, latUpperBound);
        double destLong = ThreadLocalRandom.current().nextDouble(longLowerBound, longUpperBound);

        return new Node(destLat, destLong);
    }

    public class Vector {

        private double x;
        private double y;

        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}

package com.type2labs.undersea.agent.model;

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

    public static Node randomNode(double latLowerBound, double latUpperBound, double longLowerBound,
                                  double longUpperBound) {
        double destLat = ThreadLocalRandom.current().nextDouble(latLowerBound, latUpperBound);
        double destLong = ThreadLocalRandom.current().nextDouble(longLowerBound, longUpperBound);

        return new Node(destLat, destLong);
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

    @Override
    public String toString() {
        return "Node{" +
                "vector=" + vector +
                ", nodeStatus=" + nodeStatus +
                '}';
    }

    public enum NodeStatus {
        UNVISITED, VISITED, POINT_OF_INTEREST
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

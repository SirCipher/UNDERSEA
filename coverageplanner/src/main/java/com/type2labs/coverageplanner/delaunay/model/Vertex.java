package com.type2labs.coverageplanner.delaunay.model;

import com.google.common.collect.Sets;

import java.util.Set;

public class Vertex extends Vector {
    private final Set<Vertex> neighborVertices = Sets.newLinkedHashSet();
    private final Set<Triangle> neighborTriangles = Sets.newLinkedHashSet();
    private Integer hilbertIndex;

    public Vertex(double x, double y) {
        super(x, y);
    }

    public Set<Vertex> getNeighborVertices() {
        return neighborVertices;
    }

    public Set<Triangle> getNeighborTriangles() {
        return neighborTriangles;
    }

    public void addTriangle(Triangle tri) {
        neighborTriangles.add(tri);
        neighborVertices.addAll(tri.getVertices());
        neighborVertices.remove(this);
        for (Triangle t : neighborTriangles) {
            t.invalidateNeighbors();
        }
    }

    public void removeTriangle(Triangle tri) {
        neighborTriangles.remove(tri);
        neighborVertices.removeAll(tri.getVertices());
        for (Triangle t : neighborTriangles) {
            t.invalidateNeighbors();
        }
    }

    public Integer getHilbertIndex() {
        return hilbertIndex;
    }

    public void setHilbertIndex(Integer hilbertIndex) {
        this.hilbertIndex = hilbertIndex;
    }
}

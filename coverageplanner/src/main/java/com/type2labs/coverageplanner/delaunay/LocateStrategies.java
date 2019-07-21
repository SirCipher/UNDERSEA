package com.type2labs.coverageplanner.delaunay;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.type2labs.coverageplanner.delaunay.model.Vector;
import com.type2labs.coverageplanner.delaunay.model.Vertex;

import java.awt.geom.Rectangle2D;
import java.util.List;

public class LocateStrategies {
    public static interface LocateStrategy {
        void initialize(Iterable<Vector> samples, Rectangle2D bounds);

        boolean addSample(Vector v);

        Vector getNearest(Vector v);
    }

    public static class NaiveLocateStrategy implements LocateStrategy {
        private List<Vector> locatable = Lists.newArrayList();

        public void initialize(Iterable<Vector> samples, Rectangle2D bounds) {
            locatable = Lists.newArrayList(samples);
        }

        public boolean addSample(Vector v) {
            locatable.add(v);
            return true;
        }

        public Vector getNearest(final Vector v) {
            return Utils.minObject(locatable, new Function<Vector, Double>() {
                public Double apply(Vector vert) {
                    return vert.subtract(v).lengthSquared();
                }
            });
        }
    }

    public static class TriangulationLocateStrategy implements LocateStrategy {
        private Triangulation triangulation = new Triangulation();

        public void initialize(Iterable<Vector> samples, Rectangle2D bounds) {
            triangulation = new Triangulation();
            triangulation.createSuperTriangle(bounds);
            for (Vector v : samples) {
                addSample(v);
            }
        }

        public boolean addSample(Vector v) {
            try {
                triangulation.addVertexToTriangulation(new Vertex(v.x, v.y));
                return true;
            } catch (Triangulation.InvalidVertexException e) {
                return false;
            }
        }

        public Vector getNearest(final Vector v) {
            return triangulation.locateNearestVertex(v);
        }
    }
}

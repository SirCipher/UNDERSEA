package com.type2labs.coverageplanner.delaunay.dtfe;

import com.google.common.collect.Sets;
import com.type2labs.coverageplanner.delaunay.Triangulation;
import com.type2labs.coverageplanner.delaunay.dtfe.interpolation.InterpolationStrategy;
import com.type2labs.coverageplanner.delaunay.model.Vertex;

import java.util.LinkedHashSet;

public class Dtfes {
    public static <T extends BasicDensityModel> DtfeTriangulationMap<DifferenceDensityModel<T>> difference(
            DtfeTriangulationMap<T> a,
            DtfeTriangulationMap<T> b,
            InterpolationStrategy interpolation) throws Triangulation.InvalidVertexException {

        // Compute vertex partitions
        LinkedHashSet<Vertex> averts = a.getTriangulation().getVertices();
        LinkedHashSet<Vertex> bverts = b.getTriangulation().getVertices();

        LinkedHashSet<Vertex> aOnly = Sets.newLinkedHashSet(averts);
        aOnly.removeAll(bverts);

        LinkedHashSet<Vertex> bOnly = Sets.newLinkedHashSet(bverts);
        bOnly.removeAll(averts);

        LinkedHashSet<Vertex> aAndB = Sets.newLinkedHashSet(averts);
        aAndB.retainAll(bverts);

        // Generate difference map
        DtfeTriangulationMap<DifferenceDensityModel<T>> diff = new DtfeTriangulationMap<DifferenceDensityModel<T>>();
        for (Vertex v : aOnly) {
            T aChild = a.get(v);
            double bDense = b.getInterpolatedDensity(v, interpolation);
            diff.put(v.x, v.y, new DifferenceDensityModel<T>(
                    aChild,
                    null,
                    aChild.getDensity() - bDense));
        }
        for (Vertex v : bOnly) {
            T bChild = b.get(v);
            double aDense = a.getInterpolatedDensity(v, interpolation);
            diff.put(v.x, v.y, new DifferenceDensityModel<T>(
                    null,
                    bChild,
                    aDense - bChild.getDensity()));
        }
        for (Vertex v : aAndB) {
            T aChild = a.get(v);
            T bChild = b.get(v);
            diff.put(v.x, v.y, new DifferenceDensityModel<T>(
                    aChild,
                    bChild,
                    aChild.getDensity() - bChild.getDensity()));
        }
        diff.triangulate();
        return diff;
    }

    public static class DifferenceDensityModel<T extends BasicDensityModel> extends BasicDensityModel {
        private final T aChild;
        private final T bChild;
        private final double densityDiff;

        public DifferenceDensityModel(T aChild, T bChild, double density) {
            this.aChild = aChild;
            this.bChild = bChild;
            this.densityDiff = density;
        }

        @Override
        public double getDensity() {
            return densityDiff;
        }

        public T getAChild() {
            return aChild;
        }

        public T getBChild() {
            return bChild;
        }
    }
}

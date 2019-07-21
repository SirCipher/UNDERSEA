package com.type2labs.coverageplanner.delaunay.dtfe.interpolation;


import com.type2labs.coverageplanner.delaunay.Triangulation;
import com.type2labs.coverageplanner.delaunay.dtfe.DensityModel;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.model.Vector;
import com.type2labs.coverageplanner.delaunay.model.Vertex;

final class NearestNeighborInterpolationStrategy implements
        InterpolationStrategy {
    public double getDensity(DtfeTriangulationMap<? extends DensityModel> dtfe, Vector v) {
        Vertex vert;
        try {
            vert = dtfe.getTriangulation().locateNearestVertex(v);
        } catch (Triangulation.NonDelaunayException e) {
            return 0;
        }

        // Do not report density for triangles outside the convex hull of
        // map vertices.
        if (vert == null || dtfe.getTriangulation().neighborsSuperVertex(vert)) {
            return 0.0;
        }

        return dtfe.getDensity(vert);
    }
}
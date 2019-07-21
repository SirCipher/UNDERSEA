package com.type2labs.coverageplanner.delaunay.dtfe.interpolation;


import com.type2labs.coverageplanner.delaunay.dtfe.DensityModel;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.model.Triangle;
import com.type2labs.coverageplanner.delaunay.model.Vector;
import com.type2labs.coverageplanner.delaunay.model.Vertex;

public class MeanInterpolationStrategy implements InterpolationStrategy {
    public double getDensity(DtfeTriangulationMap<? extends DensityModel> dtfe, Vector v) {
        Triangle tri = dtfe.getTriangulation().locate(v);

        // Do not report density for triangles outside the convex hull of
        // map vertices.
        if (tri == null || dtfe.getTriangulation().touchesSuperVertex(tri)) {
            return 0.0;
        }

        double sum = 0.0;
        for (Vertex vert : tri.getVertices()) {
            sum += dtfe.getDensity(vert);
        }
        return sum / 3.0;
    }
}

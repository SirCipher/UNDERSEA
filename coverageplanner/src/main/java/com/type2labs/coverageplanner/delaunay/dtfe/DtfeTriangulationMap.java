package com.type2labs.coverageplanner.delaunay.dtfe;

import com.google.common.base.Function;
import com.type2labs.coverageplanner.delaunay.Triangulation;
import com.type2labs.coverageplanner.delaunay.Utils;
import com.type2labs.coverageplanner.delaunay.dtfe.interpolation.InterpolationStrategy;
import com.type2labs.coverageplanner.delaunay.model.Triangle;
import com.type2labs.coverageplanner.delaunay.model.TriangulationMap;
import com.type2labs.coverageplanner.delaunay.model.Vector;
import com.type2labs.coverageplanner.delaunay.model.Vertex;


/**
 * Computes the Delaunay Tesselation Field Estimator (DTFE):
 * http://en.wikipedia.org/wiki/Delaunay_tessellation_field_estimator
 * <p>
 * This method produces very good heatmaps from discrete data.
 *
 * @param <T> a model that implements the DensityModel interface
 */
public class DtfeTriangulationMap<T extends DensityModel> extends TriangulationMap<T> {
    private Double maxDensity = null;

    @Override
    public void triangulate() throws Triangulation.InvalidVertexException {
        super.triangulate();
        computeDtfe();
    }

    /**
     * Returns the density of the vertex.
     */
    public double getDensity(Vertex v) {
        T model = get(v);
        if (model == null) {
            return 0.0;
        } else if (getTriangulation().neighborsSuperVertex(v)) {
            return 0.0;
        } else {
            return model.getDensity();
        }
    }

    public double getInterpolatedDensity(Vector v, InterpolationStrategy strategy) {
        return strategy.getDensity(this, v);
    }

    /**
     * Returns the maximum density value for all vertices
     */
    public double getMaxDensity() {
        if (maxDensity == null) {
            maxDensity = Utils.maxValue(getTriangulation().getVertices(),
                    new Function<Vertex, Double>() {
                        public Double apply(Vertex v) {
                            return Math.abs(getDensity(v));
                        }
                    });
        }
        return maxDensity;
    }

    /**
     * Returns a value of 0.0 to 1.0, where 1.0 represents the maximum density
     * value. This can be plugged directly into a {@link ColorScale} object.
     */
    public double getRelativeDensity(double d, ScaleType scaleType) {
        if (d == 0) return 0;
        boolean neg = (d < 0);
        d = Math.abs(d);
        double relativeDensity = scaleType == ScaleType.LOG ?
                Math.log10(1 + d) / Math.log10(1 + getMaxDensity()) :
                d / getMaxDensity();
        return relativeDensity * (neg ? -1 : 1);
    }

    private void computeDtfe() {
        for (Vertex v : getTriangulation().getVertices()) {
            double area = 0.0;
            for (Triangle tri : v.getNeighborTriangles()) {
                area += tri.getArea();
            }
            T model = get(v);
            model.setDensity(area == 0 ? 0.0 : model.getWeight() / area);
        }
    }

    public static enum ScaleType {
        LINEAR, LOG;
    }
}
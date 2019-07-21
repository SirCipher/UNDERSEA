package com.type2labs.coverageplanner.delaunay.dtfe.interpolation;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.type2labs.coverageplanner.delaunay.Triangulation;
import com.type2labs.coverageplanner.delaunay.dtfe.DensityModel;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.model.Triangle;
import com.type2labs.coverageplanner.delaunay.model.Vector;
import com.type2labs.coverageplanner.delaunay.model.Vertex;
import com.type2labs.coverageplanner.delaunay.model.Voronoi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NaturalNeighborInterpolationStrategy implements InterpolationStrategy {
    private final Map<Vertex, Voronoi> voronoi;

    public NaturalNeighborInterpolationStrategy() {
        this.voronoi = Maps.newLinkedHashMap();
    }

    public Voronoi getVoronoi(Vertex v) {
        if (voronoi.containsKey(v)) {
            return voronoi.get(v);
        }

        Voronoi cell = Voronoi.createFromTriangulation(v);
        voronoi.put(v, cell);
        return cell;
    }

    public Voronoi getSecondOrderVoronoi(Triangulation triangulation, Vertex v) throws Triangulation.NonDelaunayException, Triangulation.InvalidVertexException {
        Collection<Triangle> cavity = triangulation.getCircumcircleTriangles(v);
        List<Triangle> tris = triangulation.createTriangles(triangulation.getEdgeSet(cavity), v);
        Set<Vertex> verts = Sets.newHashSet();
        for (Triangle tri : tris) {
            verts.addAll(tri.getVertices());
        }
        verts.remove(v);
        return Voronoi.create(v, verts, tris);
    }

    public double getDensity(DtfeTriangulationMap<? extends DensityModel> dtfe, Vector v) {
        Triangle tri = dtfe.getTriangulation().locate(v);
        if (tri == null || dtfe.getTriangulation().touchesSuperVertex(tri)) {
            return 0.0;
        }

        try {
            Voronoi vor = getSecondOrderVoronoi(dtfe.getTriangulation(), new Vertex(v.x, v.y));
            double area = 0;
            for (Vertex vert : vor.getNeighborVertices()) {
                if (dtfe.getTriangulation().neighborsSuperVertex(vert)) {
                    continue;
                }
                Voronoi vertVor = getVoronoi(vert);
                if (vertVor != null) {
                    area += vor.intersect(vertVor).getArea() * dtfe.getDensity(vert);
                }
            }
            return area / vor.getArea();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}

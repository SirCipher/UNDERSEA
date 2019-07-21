package com.type2labs.coverageplanner.delaunay.dtfe.interpolation;


import com.type2labs.coverageplanner.delaunay.dtfe.DensityModel;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.model.Vector;

public interface InterpolationStrategy {
    public double getDensity(DtfeTriangulationMap<? extends DensityModel> dtfe, Vector v);
}
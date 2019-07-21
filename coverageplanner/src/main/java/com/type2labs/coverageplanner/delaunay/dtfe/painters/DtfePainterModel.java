package com.type2labs.coverageplanner.delaunay.dtfe.painters;


import com.type2labs.coverageplanner.delaunay.dtfe.ColorScale;
import com.type2labs.coverageplanner.delaunay.dtfe.ColorScales;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.dtfe.interpolation.InterpolationStrategies;
import com.type2labs.coverageplanner.delaunay.dtfe.interpolation.InterpolationStrategy;

import java.awt.*;

public class DtfePainterModel {
    private ColorScale colorScale = ColorScales.PURPLE_TO_GREEN_LINEAR;
    private double densityScalar = 1.0;
    private Color edgeColor = null;
    private InterpolationStrategy interpolationStrategy = InterpolationStrategies.createNaturalNeighbor();
    private float edgeStrokeWidth = 1.0f;
    private DtfeTriangulationMap.ScaleType scaleType = DtfeTriangulationMap.ScaleType.LINEAR;

    public DtfeTriangulationMap.ScaleType getScaleType() {
        return scaleType;
    }

    public DtfePainterModel setScaleType(DtfeTriangulationMap.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public float getEdgeStrokeWidth() {
        return edgeStrokeWidth;
    }

    public DtfePainterModel setEdgeStrokeWidth(float edgeStrokeWidth) {
        this.edgeStrokeWidth = edgeStrokeWidth;
        return this;
    }

    public Color getEdgeColor() {
        return edgeColor;
    }

    public DtfePainterModel setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
        return this;
    }

    public ColorScale getColorScale() {
        return colorScale;
    }

    public DtfePainterModel setColorScale(ColorScale colorScale) {
        this.colorScale = colorScale;
        return this;
    }

    public double getDensityScalar() {
        return densityScalar;
    }

    public DtfePainterModel setDensityScalar(double densityScalar) {
        this.densityScalar = densityScalar;
        return this;
    }

    public InterpolationStrategy getInterpolationStrategy() {
        return interpolationStrategy;
    }

    public DtfePainterModel setInterpolationStrategy(InterpolationStrategy interpolationStrategy) {
        this.interpolationStrategy = interpolationStrategy;
        return this;
    }
}
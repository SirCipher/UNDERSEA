package com.type2labs.coverageplanner.delaunay;


import com.type2labs.coverageplanner.delaunay.dtfe.ColorScales;
import com.type2labs.coverageplanner.delaunay.dtfe.DensityModel;
import com.type2labs.coverageplanner.delaunay.dtfe.DtfeTriangulationMap;
import com.type2labs.coverageplanner.delaunay.dtfe.interpolation.InterpolationStrategies;
import com.type2labs.coverageplanner.delaunay.dtfe.painters.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This class demonstrates various ways of creating triangulations and DTFEs,
 * and how to generate images from them.
 */
public class TriangulationDemo {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public static void drawTriangulation(Triangulation t, int w, int h, String filename)
            throws IOException {

        TriangulationPainter painter = new TriangulationPainter(new TriangulationPainterModel()
                .setEdgeColor(new Color(0x2222AA))
                .setEdgeStrokeWidth(1.5f));

        BufferedImage img = painter.paint(t, new PaintTransform(w, h));
        ImageIO.write(img, "png", new File(filename));
    }

    public static void drawDtfe(DtfeTriangulationMap<? extends DensityModel> dtfe, int w, int h, String filename)
            throws IOException {

        DtfePainter painter = new DtfePainter(new DtfePainterModel()
                .setInterpolationStrategy(InterpolationStrategies.createNaturalNeighbor())
                .setDensityScalar(50)
                .setEdgeColor(new Color(0x10000000, true))
                .setColorScale(ColorScales.PURPLE_TO_GREEN_LINEAR));

        BufferedImage img = painter.paint(dtfe, new PaintTransform(w, h));
        ImageIO.write(img, "png", new File(filename));
    }

    public static void fourLiner() throws Exception {
        Triangulation t = new Triangulation();
        t.addAllVertices(Triangulations.randomVertices(1000, 400, 400));
        t.triangulate();
        TriangulationDemo.drawTriangulation(t, 400, 400, "triangulation.png");
    }

    public static void createTriangulationAndDtfeDemo() throws Triangulation.InvalidVertexException, IOException {
        // Generate vertices

        // Triangulate
        long start = System.nanoTime();
        Triangulation t = new Triangulation();
        t.addAllVertices(Triangulations.randomVertices(10000, WIDTH, HEIGHT));
        t.setDebugLogger(new Triangulation.DebugLogger() {
            public void debug(String str) {
                System.out.println(str);
            }
        });
        t.triangulate();
        System.out.println(String.format("Time to triangulate %,d vertices: %d msec.", 10000,
                TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS)));
        System.out.println(String.format("Average hops per locate: %.2f",
                (float) t.getHopCount() / t.getLocateCount()));

        // Draw Results
        System.out.println("Creating images");
        drawTriangulation(t, WIDTH, HEIGHT, "triangulation.png");

        System.out.println("Done");
    }

    public static void main(String[] args) throws Exception {
        //threeLiner();
        createTriangulationAndDtfeDemo();
    }
}

package com.type2labs.coverageplanner.delaunay;


import com.type2labs.coverageplanner.delaunay.dtfe.painters.PaintTransform;
import com.type2labs.coverageplanner.delaunay.dtfe.painters.TriangulationPainter;
import com.type2labs.coverageplanner.delaunay.dtfe.painters.TriangulationPainterModel;
import com.type2labs.coverageplanner.delaunay.model.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Generates an image of point samples.
 * <p>
 * Each new sample is computed using a poisson disc distribution around an
 * existing sample point.
 *
 * @see http://en.wikipedia.org/wiki/Supersampling
 */
public class PoissonDiscSamplesDemo {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        // Create poisson disc sample function
        Rectangle bounds = new Rectangle(0, 0, 500, 500);
        SampleFunctions.SampleFunction poissonFunction = new SampleFunctions.PoissonDiscSampleFunction(bounds, 10);

        // Generate samples to fill bounds
        Iterable<Vector> samples = new SampleBuilder()
                .setLocateStrategy(new LocateStrategies.TriangulationLocateStrategy())
                .fill(poissonFunction)
                .getSamples();

        // Triangulate and paint
        Triangulation t = new Triangulation();
        t.addAllVertices(Utils.toVertices(samples));
        t.triangulate();


        TriangulationPainter painter = new TriangulationPainter(
                new TriangulationPainterModel().setVertexDotColor(Color.BLACK));
        BufferedImage img = painter.paint(t, new PaintTransform(bounds.width, bounds.height));
//		ImageIO.write(img, "png", new File("poisson.png"));
        TriangulationDemo.drawTriangulation(t, 500, 500, "poisson.png");
    }
}

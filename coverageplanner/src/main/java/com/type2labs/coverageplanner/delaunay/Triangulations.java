package com.type2labs.coverageplanner.delaunay;

import com.google.common.collect.Lists;
import com.type2labs.coverageplanner.delaunay.model.Vertex;

import java.util.List;
import java.util.Random;

public class Triangulations {
    public static List<Vertex> randomVertices(int n, int width, int height) {
        Random random = new Random(System.currentTimeMillis());
        List<Vertex> rands = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            x = x * x;
            y = y * y;
            rands.add(new Vertex(x * width, y * height));
        }
        return rands;
    }

    public static List<Vertex> randomGaussian(int n, int width, int height) {
        Random random = new Random(System.currentTimeMillis());
        List<Vertex> rands = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            double x = random.nextGaussian();
            double y = random.nextGaussian();
            rands.add(new Vertex(x * width / 8 + width / 2, y * width / 8 + height / 2));
        }
        return rands;
    }
}

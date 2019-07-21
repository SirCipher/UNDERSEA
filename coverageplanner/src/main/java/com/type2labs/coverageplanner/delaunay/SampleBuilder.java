package com.type2labs.coverageplanner.delaunay;

import com.google.common.collect.Lists;
import com.type2labs.coverageplanner.delaunay.model.Vector;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

public class SampleBuilder {
    private final List<Vector> samples = Lists.newArrayList();
    private int maxTries = 5;
    private LocateStrategies.LocateStrategy locateStrategy = new LocateStrategies.NaiveLocateStrategy();

    public int getMaxTries() {
        return maxTries;
    }

    public SampleBuilder setMaxTries(int maxTries) {
        this.maxTries = maxTries;
        return this;
    }

    public LocateStrategies.LocateStrategy getLocateStrategy() {
        return locateStrategy;
    }

    public SampleBuilder setLocateStrategy(LocateStrategies.LocateStrategy locateStrategy) {
        this.locateStrategy = locateStrategy;
        return this;
    }

    public List<Vector> getSamples() {
        return samples;
    }

    public SampleBuilder fill(SampleFunctions.SampleFunction function) {
        List<Vector> queue = Lists.newArrayList();
        Random random = new Random(System.currentTimeMillis());
        locateStrategy.initialize(samples, function.getBoundingShape().getBounds2D());

        // Generate and add first sample
        Vector firstSample = null;
        while (firstSample == null || !locateStrategy.addSample(firstSample)) {
            firstSample = function.createSampleIn(function.getBoundingShape());
        }
        queue.add(firstSample);
        samples.add(firstSample);

        while (!queue.isEmpty()) {
            // Get random element from the queue.
            int queueIndex = random.nextInt(queue.size());
            Vector sample = queue.get(queueIndex);

            // Attempt to a create new valid sample near the existing sample.
            Vector newValidSample = createNewValidSample(function, sample);

            // Add the new valid sample or remove the existing sample from the queue.
            if (newValidSample != null && locateStrategy.addSample(newValidSample)) {
                queue.add(newValidSample);
                samples.add(newValidSample);
            } else {
                queue.remove(queueIndex);
            }
        }
        return this;
    }

    private Vector createNewValidSample(SampleFunctions.SampleFunction function, Vector sample) {
        for (int i = 0; i < maxTries; i++) {
            Vector newSample = function.createSampleNear(sample);
            if (isValid(function, newSample)) {
                return newSample;
            }
        }
        return null;
    }

    private boolean isValid(SampleFunctions.SampleFunction function, final Vector v) {
        Vector nearest = locateStrategy.getNearest(v);
        if (nearest == null) {
            return false;
        }

        Rectangle2D bounds = function.getBoundingShape().getBounds2D();
        if (!bounds.contains(v.x, v.y)) {
            return false;
        }

        double minDist = function.getMimimumDistance(nearest);
        return (nearest.subtract(v).lengthSquared() > (minDist * minDist));
    }
}

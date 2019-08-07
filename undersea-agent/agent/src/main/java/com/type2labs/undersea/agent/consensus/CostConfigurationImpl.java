package com.type2labs.undersea.agent.consensus;

import static com.type2labs.undersea.utilities.Preconditions.inRange;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CostConfigurationImpl implements CostConfiguration {

    private double accuracyWeighting;
    private double speedWeighting;

    public double getAccuracyWeighting() {
        return accuracyWeighting;
    }

    public double getSpeedWeighting() {
        return speedWeighting;
    }

    public CostConfigurationImpl withAccuracyWeighting(double weighting) {
        inRange(0.0, 100.0, weighting, "Accuracy weighting must be between 0 and 100");
        this.accuracyWeighting = weighting;

        return this;
    }

    public CostConfigurationImpl withSpeedWeighting(double weighting) {
        inRange(0.0, 100.0, weighting, "Speed weighting must be between 0 and 100");
        this.accuracyWeighting = weighting;

        return this;
    }

}

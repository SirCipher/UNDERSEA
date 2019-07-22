package com.type2labs.undersea.missionplanner.model.impl;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.type2labs.undersea.missionplanner.PlannerException;
import com.type2labs.undersea.missionplanner.PlannerUtils;
import com.type2labs.undersea.missionplanner.decomposer.Decomposer;
import com.type2labs.undersea.missionplanner.model.MissionGenerator;
import com.type2labs.undersea.missionplanner.model.PlanDataModel;
import com.type2labs.undersea.missionplanner.model.Planner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class MissionGeneratorImpl implements MissionGenerator {

    private static Decomposer decomposer = Decomposer.getInstance();

    @Override
    public List<?> run(int[][] polygon) throws PlannerException {
        double[][] centroids = decompose(polygon);
        double[][] distanceMatrix = PlannerUtils.computeEuclideanDistanceMatrix(centroids);

        PlanDataModel model = new PlanDataModel(distanceMatrix, 1,1 );
        Planner planner = new TspPlanner();
        planner.generate(model);

        return null;
    }

    @Override
    public List<?> createPlan(int[][] polygon) {
        return null;
    }

    @Override
    public double[][] decompose(int[][] polygon) throws PlannerException {
        MWNumericArray numericArray = new MWNumericArray(polygon, MWClassID.DOUBLE);

        try {
            numericArray = (MWNumericArray) decomposer.decompose(1, numericArray)[0];
        } catch (MWException e) {
            throw new PlannerException(e);
        }

        return (double[][]) numericArray.toDoubleArray();
    }

    @Override
    public List<?> replanMission() {
        return null;
    }
}

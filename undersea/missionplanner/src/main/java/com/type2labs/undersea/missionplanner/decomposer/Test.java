package com.type2labs.undersea.missionplanner.decomposer;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import java.util.Arrays;

/**
 * Created by Thomas Klapwijk on 2019-07-22.
 */
public class Test {

    public static void main(String[] args) throws MWException {
        Decomposer decomposer = new Decomposer();
        int[][] polygon = {{0, 0}, {0, 20}, {10, 50}, {20, 20}, {20, 0}};

        MWNumericArray numericArray = new MWNumericArray(polygon, MWClassID.INT32);

        Object[] result = decomposer.decompose(1, numericArray);
        System.out.println(Arrays.toString(result));
    }

}

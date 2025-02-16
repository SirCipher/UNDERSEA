/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.controller.controllerCT.simplex.implementation;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optimization.GoalType;

/**
 * An objective function for a {@link LinearModel}.
 *
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
public class LinearObjectiveFunction {

    private final RealVector coefficients;
    private final GoalType goalType;
    private final double constantTerm;

    /**
     * @param coefficients The coefficients for the linear equation being optimized
     * @param constantTerm The constant term of the linear equation
     * @param goalType     The type of optimization to perform
     */
    public LinearObjectiveFunction(double[] coefficients, double constantTerm, GoalType goalType) {
        this(new ArrayRealVector(coefficients), constantTerm, goalType);
    }

    /**
     * @param coefficients The coefficients for the linear equation being optimized
     * @param constantTerm The constant term of the linear equation
     * @param goalType     The type of optimization to perform
     */
    public LinearObjectiveFunction(RealVector coefficients, double constantTerm, GoalType goalType) {
        this.coefficients = coefficients;
        this.constantTerm = constantTerm;
        this.goalType = goalType;
    }

    public RealVector getCoefficients() {
        return coefficients;
    }

    public double getConstantTerm() {
        return constantTerm;
    }

    public GoalType getGoalType() {
        return goalType;
    }

}

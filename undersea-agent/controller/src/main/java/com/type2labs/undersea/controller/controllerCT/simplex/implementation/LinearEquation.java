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


/**
 * A constraint for a {@link LinearModel}.
 *
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
public class LinearEquation {

    private final RealVector leftHandSide;
    private final Relationship relationship;
    private final double rightHandSide;

    public LinearEquation(double[] coefficients, Relationship relationship, double rightHandSide) {
        this(new ArrayRealVector(coefficients), relationship, rightHandSide);
    }

    public LinearEquation(RealVector leftHandSide, Relationship relationship, double rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.relationship = relationship;
        this.rightHandSide = rightHandSide;
    }

    public LinearEquation(RealVector leftHandSide, Relationship relationship, RealVector rightHandSide) {
        this.leftHandSide = leftHandSide.subtract(rightHandSide);
        this.relationship = relationship;
        this.rightHandSide = 0;
    }

    public RealVector getCoefficients() {
        return leftHandSide;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public double getRightHandSide() {
        return rightHandSide;
    }

}

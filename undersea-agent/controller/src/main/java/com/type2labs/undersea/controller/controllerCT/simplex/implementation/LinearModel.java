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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A model for linear optimization.
 *
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
public class LinearModel {

    private static final Logger logger = LogManager.getLogger(LinearModel.class);

    private final LinearObjectiveFunction objectiveFunction;
    private final List<LinearEquation> constraints;

    /**
     * @param numVariables The number of decision variables in the model.
     */
    public LinearModel(LinearObjectiveFunction objectiveFunction) {
        this.objectiveFunction = objectiveFunction;
        this.constraints = new ArrayList<LinearEquation>();
    }

    /**
     * Adds the given constraint to the model.
     *
     * @param constraint The {@link LinearEquation} to add to the model.
     */
    public void addConstraint(LinearEquation constraint) {
        if (constraint.getCoefficients().getDimension() != getNumVariables()) {
            logger.info(constraint.getCoefficients().getDimension() + "sdFSDf " + getNumVariables());
            throw new IndexOutOfBoundsException();
        }
        constraints.add(constraint);
    }

    /**
     * Returns a map from constraint type to count of the corresponding constraint type.
     */
    public Map<Relationship, Integer> getConstraintTypeCounts() {
        Map<Relationship, Integer> counts = new HashMap<Relationship, Integer>();
        for (Relationship relationship : Relationship.values()) {
            counts.put(relationship, 0);
        }
        for (LinearEquation constraint : getConstraints()) {
            counts.put(constraint.getRelationship(), counts.get(constraint.getRelationship()) + 1);
        }
        return counts;
    }

    public List<LinearEquation> getConstraints() {
        return constraints;
    }

    /**
     * Returns new versions of the constraints which have positive right hand sides.
     */
    public List<LinearEquation> getNormalizedConstraints() {
        List<LinearEquation> normalized = new ArrayList<LinearEquation>();
        for (LinearEquation constraint : constraints) {
            normalized.add(normalize(constraint));
        }
        return normalized;
    }

    public int getNumVariables() {
        return objectiveFunction.getCoefficients().getDimension();
    }

    public LinearObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    /**
     * Returns a new equation equivalent to this one with a positive right hand side.
     */
    private LinearEquation normalize(LinearEquation constraint) {
        if (constraint.getRightHandSide() < 0) {
            return new LinearEquation(constraint.getCoefficients().mapMultiply(-1),
                    constraint.getRelationship().oppositeRelationship(),
                    -1 * constraint.getRightHandSide());
        }
        return new LinearEquation(constraint.getCoefficients(),
                constraint.getRelationship(), constraint.getRightHandSide());
    }

}

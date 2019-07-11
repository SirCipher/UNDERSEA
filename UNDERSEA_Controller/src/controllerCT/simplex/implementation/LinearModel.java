// Copyright 2009 Google Inc.

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllerCT.simplex.implementation;

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
    	System.out.println(constraint.getCoefficients().getDimension() + "sdFSDf " + getNumVariables());
      throw new IndexOutOfBoundsException();
    }
    constraints.add(constraint);
  }

  public int getNumVariables() {
    return objectiveFunction.getCoefficients().getDimension();
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

  public LinearObjectiveFunction getObjectiveFunction() {
    return objectiveFunction;
  }

  /**
   * Returns a map from constraint type to count of the corresponding constraint type.
   */
  public Map<Relationship,Integer> getConstraintTypeCounts() {
    Map<Relationship,Integer> counts = new HashMap<Relationship,Integer>();
    for (Relationship relationship : Relationship.values()) {
      counts.put(relationship, 0);
    }
    for (LinearEquation constraint : getConstraints()) {
      counts.put(constraint.getRelationship(), counts.get(constraint.getRelationship()) + 1);
    }
    return counts;
  }

}

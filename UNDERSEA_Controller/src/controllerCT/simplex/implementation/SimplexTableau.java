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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optimization.GoalType;

/**
 * A tableau for use in the Simplex method.
 *
 * Example:
 *   W |  Z |  x1 |  x2 |  x- | s1 |  s2 |  a1 |  RHS
 * ---------------------------------------------------
 *  -1    0    0     0     0     0     0     1     0   <= phase 1 objective
 *   0    1   -15   -10    0     0     0     0     0   <= phase 2 objective
 *   0    0    1     0     0     1     0     0     2   <= constraint 1
 *   0    0    0     1     0     0     1     0     3   <= constraint 2
 *   0    0    1     1     0     0     0     1     4   <= constraint 3
 *
 * W: Phase 1 objective function
 * Z: Phase 2 objective function
 * x1 & x2: Decision variables
 * x-: Extra decision variable to allow for negative values
 * s1 & s2: Slack/Surplus variables
 * a1: Artificial variable
 * RHS: Right hand side
 *
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
class SimplexTableau {

  protected RealMatrix tableau;
  protected final boolean nonNegative;
  protected final int numDecisionVariables;
  protected final int numSlackVariables;
  protected int numArtificialVariables;

  SimplexTableau(LinearModel model) {
    this(model, false);
  }

  SimplexTableau(LinearModel model, boolean restrictToNonNegative) {
    Map<Relationship,Integer> counts = model.getConstraintTypeCounts();
    this.nonNegative = restrictToNonNegative;
    this.numDecisionVariables = model.getNumVariables() + (nonNegative ? 0 : 1);
    this.numSlackVariables = counts.get(Relationship.LEQ) + counts.get(Relationship.GEQ);
    this.numArtificialVariables = counts.get(Relationship.EQ) + counts.get(Relationship.GEQ);
    this.tableau = new Array2DRowRealMatrix(createTableau(model));
    initialize();
  }

  protected double[][] createTableau(LinearModel model) {

    // create a matrix of the correct size
    List<LinearEquation> constraints = model.getNormalizedConstraints();
    int width = this.numDecisionVariables + this.numSlackVariables
        + this.numArtificialVariables + getNumObjectiveFunctions() + 1; // + 1 is for RHS
    int height = model.getConstraints().size() + getNumObjectiveFunctions();
    double[][] matrix = new double[height][width];
    for (int i = 1; i < height; i++) {
      Arrays.fill(matrix[i], 0);
    }

    // initialize the objective function rows
    LinearObjectiveFunction objectiveFunction = model.getObjectiveFunction();
    if (getNumObjectiveFunctions() == 2) {
      matrix[0][0] = -1;
    }
    int zIndex = getNumObjectiveFunctions() == 1 ? 0 : 1;
    boolean maximize = objectiveFunction.getGoalType() == GoalType.MAXIMIZE;
    matrix[zIndex][zIndex] = maximize ? 1 : -1;
    RealVector objectiveCoefficients = maximize
        ? model.getObjectiveFunction().getCoefficients().mapMultiply(-1)
        : model.getObjectiveFunction().getCoefficients();
    copyArray(objectiveCoefficients.toArray(), matrix[zIndex], getDecisionVariableOffset()); //getdata
    matrix[zIndex][width - 1] = maximize ? model.getObjectiveFunction().getConstantTerm()
        : -1 * model.getObjectiveFunction().getConstantTerm();

    if (!nonNegative) {
      matrix[zIndex][getSlackVariableOffset() - 1] = getInvertedCoeffiecientSum(objectiveCoefficients);
    }

    // initialize the constraint rows
    int slackVar = 0;
    int artificialVar = 0;
    for (int i = 0; i < constraints.size(); i++) {
      LinearEquation constraint = constraints.get(i);
      int row = getNumObjectiveFunctions() + i;

      // decision variable coefficients
      copyArray(constraint.getCoefficients().toArray(), matrix[row], 1);//getdata

      // x-
      if (!nonNegative) {
        matrix[row][getSlackVariableOffset() - 1] =
            getInvertedCoeffiecientSum(constraint.getCoefficients());
      }

      // RHS
      matrix[row][width - 1] = constraint.getRightHandSide();

      // slack variables
      if (constraint.getRelationship() == Relationship.LEQ) {
        matrix[row][getSlackVariableOffset() + slackVar++] = 1;  // slack
      } else if (constraint.getRelationship() == Relationship.GEQ) {
        matrix[row][getSlackVariableOffset() + slackVar++] = -1; // excess
      }

      // artificial variables
      if (constraint.getRelationship() == Relationship.EQ
          || constraint.getRelationship() == Relationship.GEQ) {
        matrix[0][getArtificialVariableOffset() + artificialVar] = 1;
        matrix[row][getArtificialVariableOffset() + artificialVar++] = 1;
      }
    }

    return matrix;
  }

  /**
   * Returns the number of objective functions in this tableau.
   *
   * @return 2 for Phase 1.  1 for Phase 2.
   */
  protected final int getNumObjectiveFunctions() {
    return this.numArtificialVariables > 0 ? 2 : 1;
  }

  /**
   * Puts the tableau in proper form by zeroing out the artificial variables
   * in the objective function via elementary row operations.
   */
  private void initialize() {
    for (int artificialVar = 0; artificialVar < numArtificialVariables; artificialVar++) {
      int row = getBasicRow(getArtificialVariableOffset() + artificialVar);
      subtractRow(0, row, 1.0);
    }
  }

  /**
   * Returns the -1 times the sum of all coefficients in the given array.
   */
  protected static double getInvertedCoeffiecientSum(RealVector coefficients) {
    double sum = 0;
    for (double coefficient : coefficients.toArray()) {//getdata
      sum -= coefficient;
    }
    return sum;
  }

  /**
   * Checks whether the given column is basic.
   *
   * @return the row that the variable is basic in.  null if the column is not basic
   */
  private Integer getBasicRow(int col) {
    Integer row = null;
    for (int i = getNumObjectiveFunctions(); i < getHeight(); i++) {
      if (getEntry(i, col) != 0.0) {
        if (row == null) {
          row = i;
        } else {
          return null;
        }
      }
    }
    return row;
  }

  /**
   * Removes the phase 1 objective function and artificial variables from this tableau.
   */
  protected void discardArtificialVariables() {
    if (numArtificialVariables == 0) {
      return;
    }
    int width = getWidth() - numArtificialVariables - 1;
    int height = getHeight() - 1;
    double[][] matrix = new double[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 1; j++) {
        matrix[i][j] = getEntry(i + 1, j + 1);
      }
      matrix[i][width - 1] = getEntry(i + 1, getRhsOffset());
    }
    this.tableau = new Array2DRowRealMatrix(matrix);
    this.numArtificialVariables = 0;
  }


  /**
   * @param src the source array
   * @param dest the destination array
   * @param destPos the destination position
   */
  private void copyArray(double[] src, double[] dest, int destPos) {
    System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);
  }

  /**
   * Returns the current solution.
   * {@link #solve} should be called first for this to be the optimal solution.
   */
  protected LinearEquation getSolution() {
    double[] coefficients = new double[getOriginalNumDecisionVariables()];
    double mostNegative = getDecisionVariableValue(getOriginalNumDecisionVariables());
    for (int i = 0; i < coefficients.length; i++) {
      coefficients[i] = nonNegative ? getDecisionVariableValue(i)
          : getDecisionVariableValue(i) - mostNegative;
    }
    return new LinearEquation(coefficients, Relationship.EQ,
        tableau.getEntry(0, 0) * tableau.getEntry(0, getRhsOffset()));
  }

  /**
   * Returns the value of the given decision variable.  This is not the actual
   * value as it is guaranteed to be >= 0 and thus must be corrected before
   * being returned to the user.
   *
   * @param decisionVariable The index of the decision variable
   * @return The value of the given decision variable.
   */
  protected double getDecisionVariableValue(int decisionVariable) {
    Integer basicRow = getBasicRow(getNumObjectiveFunctions() + decisionVariable);
    return basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
  }

  /**
   * Subtracts a multiple of one row from another.
   * After application of this operation, the following will hold:
   *   minuendRow = minuendRow - multiple * subtrahendRow
   */
  protected void divideRow(int dividendRow, double divisor) {
    for (int j = 0; j < getWidth(); j++) {
      tableau.setEntry(dividendRow, j, tableau.getEntry(dividendRow, j) / divisor);
    }
  }

  /**
   * Subtracts a multiple of one row from another.
   * After application of this operation, the following will hold:
   *   minuendRow = minuendRow - multiple * subtrahendRow
   */
  protected void subtractRow(int minuendRow, int subtrahendRow, double multiple) {
    for (int j = 0; j < getWidth(); j++) {
      tableau.setEntry(minuendRow, j, tableau.getEntry(minuendRow, j)
          - multiple * tableau.getEntry(subtrahendRow, j));
    }
  }

  protected final int getWidth() {
    return tableau.getColumnDimension();
  }

  protected final int getHeight() {
    return tableau.getRowDimension();
  }

  protected final double getEntry(int row, int column) {
    return tableau.getEntry(row, column);
  }

  protected final void setEntry(int row, int column, double value) {
    tableau.setEntry(row, column, value);
  }

  protected final int getDecisionVariableOffset() {
    return getNumObjectiveFunctions();
  }

  protected final int getSlackVariableOffset() {
    return getNumObjectiveFunctions() + numDecisionVariables;
  }

  protected final int getArtificialVariableOffset() {
    return getNumObjectiveFunctions() + numDecisionVariables + numSlackVariables;
  }

  protected final int getRhsOffset() {
    return getWidth() - 1;
  }

  /**
   * If variables are not restricted to positive values, this will include 1
   * extra decision variable to represent the absolute value of the most
   * negative variable.
   */
  protected final int getNumDecisionVariables() {
    return numDecisionVariables;
  }

  protected final int getOriginalNumDecisionVariables() {
    return nonNegative ? numDecisionVariables : numDecisionVariables - 1;
  }

  protected final int getNumSlackVariables() {
    return numSlackVariables;
  }

  protected final int getNumArtificialVariables() {
    return numArtificialVariables;
  }

  protected final double[][] getData() {
    return tableau.getData();
  }

}

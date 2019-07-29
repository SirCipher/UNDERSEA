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

package com.type2labs.undersea.controller.controllerCT.simplex.implementation;

/**
 * Types of relationships between two cells in a Solver {@link LinearEquation}.
 *
 * @author <a href="http://www.benmccann.com">Ben McCann</a>
 */
public enum Relationship {

    EQ("="),
    LEQ("<="),
    GEQ(">=");

    private String stringValue;

    Relationship(String stringValue) {
        this.stringValue = stringValue;
    }

    public Relationship oppositeRelationship() {
        switch (this) {
            case LEQ:
                return GEQ;
            case GEQ:
                return LEQ;
        }
        return EQ;
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
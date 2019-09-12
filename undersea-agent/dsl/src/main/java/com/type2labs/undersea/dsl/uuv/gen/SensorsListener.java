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

// Generated from Sensors.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;
  import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SensorsParser}.
 */
public interface SensorsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SensorsParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(SensorsParser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link SensorsParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(SensorsParser.ModelContext ctx);
	/**
	 * Enter a parse tree produced by {@link SensorsParser#sensor}.
	 * @param ctx the parse tree
	 */
	void enterSensor(SensorsParser.SensorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SensorsParser#sensor}.
	 * @param ctx the parse tree
	 */
	void exitSensor(SensorsParser.SensorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SensorsParser#change}.
	 * @param ctx the parse tree
	 */
	void enterChange(SensorsParser.ChangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SensorsParser#change}.
	 * @param ctx the parse tree
	 */
	void exitChange(SensorsParser.ChangeContext ctx);
}
// Generated from Sensors.g4 by ANTLR 4.7.2

  package uuv.dsl.gen;
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
	 * Enter a parse tree produced by {@link SensorsParser#simulation}.
	 * @param ctx the parse tree
	 */
	void enterSimulation(SensorsParser.SimulationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SensorsParser#simulation}.
	 * @param ctx the parse tree
	 */
	void exitSimulation(SensorsParser.SimulationContext ctx);
}
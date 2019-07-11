// Generated from Sensors.g4 by ANTLR 4.7.2

  package uuv.dsl.gen;
  import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SensorsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SensorsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SensorsParser#model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel(SensorsParser.ModelContext ctx);
	/**
	 * Visit a parse tree produced by {@link SensorsParser#simulation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimulation(SensorsParser.SimulationContext ctx);
}
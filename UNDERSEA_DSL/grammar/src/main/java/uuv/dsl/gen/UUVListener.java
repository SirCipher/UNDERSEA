// Generated from UUV.g4 by ANTLR 4.7.2

package uuv.dsl.gen;
import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UUVParser}.
 */
public interface UUVListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UUVParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(UUVParser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(UUVParser.ModelContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#simulation}.
	 * @param ctx the parse tree
	 */
	void enterSimulation(UUVParser.SimulationContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#simulation}.
	 * @param ctx the parse tree
	 */
	void exitSimulation(UUVParser.SimulationContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#invocation}.
	 * @param ctx the parse tree
	 */
	void enterInvocation(UUVParser.InvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#invocation}.
	 * @param ctx the parse tree
	 */
	void exitInvocation(UUVParser.InvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#host}.
	 * @param ctx the parse tree
	 */
	void enterHost(UUVParser.HostContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#host}.
	 * @param ctx the parse tree
	 */
	void exitHost(UUVParser.HostContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(UUVParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(UUVParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#speed}.
	 * @param ctx the parse tree
	 */
	void enterSpeed(UUVParser.SpeedContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#speed}.
	 * @param ctx the parse tree
	 */
	void exitSpeed(UUVParser.SpeedContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(UUVParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(UUVParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#elems}.
	 * @param ctx the parse tree
	 */
	void enterElems(UUVParser.ElemsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#elems}.
	 * @param ctx the parse tree
	 */
	void exitElems(UUVParser.ElemsContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#elem}.
	 * @param ctx the parse tree
	 */
	void enterElem(UUVParser.ElemContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#elem}.
	 * @param ctx the parse tree
	 */
	void exitElem(UUVParser.ElemContext ctx);
	/**
	 * Enter a parse tree produced by {@link UUVParser#uuv}.
	 * @param ctx the parse tree
	 */
	void enterUuv(UUVParser.UuvContext ctx);
	/**
	 * Exit a parse tree produced by {@link UUVParser#uuv}.
	 * @param ctx the parse tree
	 */
	void exitUuv(UUVParser.UuvContext ctx);
}
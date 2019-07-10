// Generated from UUV.g4 by ANTLR 4.7.2

package uuv.dsl.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link UUVParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface UUVVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModel(UUVParser.ModelContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimulation(UUVParser.SimulationContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInvocation(UUVParser.InvocationContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHost(UUVParser.HostContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#port}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPort(UUVParser.PortContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSpeed(UUVParser.SpeedContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#sensor}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSensor(UUVParser.SensorContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUuv(UUVParser.UuvContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#change}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitChange(UUVParser.ChangeContext ctx);
}
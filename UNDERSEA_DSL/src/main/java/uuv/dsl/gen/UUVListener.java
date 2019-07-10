// Generated from UUV.g4 by ANTLR 4.7.2

package uuv.dsl.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UUVParser}.
 */
public interface UUVListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parse tree
     */
    void enterModel(UUVParser.ModelContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parse tree
     */
    void exitModel(UUVParser.ModelContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parse tree
     */
    void enterSimulation(UUVParser.SimulationContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parse tree
     */
    void exitSimulation(UUVParser.SimulationContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parse tree
     */
    void enterInvocation(UUVParser.InvocationContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parse tree
     */
    void exitInvocation(UUVParser.InvocationContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parse tree
     */
    void enterHost(UUVParser.HostContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parse tree
     */
    void exitHost(UUVParser.HostContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#port}.
     *
     * @param ctx the parse tree
     */
    void enterPort(UUVParser.PortContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#port}.
     *
     * @param ctx the parse tree
     */
    void exitPort(UUVParser.PortContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parse tree
     */
    void enterSpeed(UUVParser.SpeedContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parse tree
     */
    void exitSpeed(UUVParser.SpeedContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#sensor}.
     *
     * @param ctx the parse tree
     */
    void enterSensor(UUVParser.SensorContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#sensor}.
     *
     * @param ctx the parse tree
     */
    void exitSensor(UUVParser.SensorContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parse tree
     */
    void enterUuv(UUVParser.UuvContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parse tree
     */
    void exitUuv(UUVParser.UuvContext ctx);

    /**
     * Enter a parse tree produced by {@link UUVParser#change}.
     *
     * @param ctx the parse tree
     */
    void enterChange(UUVParser.ChangeContext ctx);

    /**
     * Exit a parse tree produced by {@link UUVParser#change}.
     *
     * @param ctx the parse tree
     */
    void exitChange(UUVParser.ChangeContext ctx);
}
// Generated from UUV.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parseMission tree produced
 * by {@link UUVParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
interface UUVVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parseMission tree produced by {@link UUVParser#elem}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitElem(UUVParser.ElemContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#elems}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitElems(UUVParser.ElemsContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitHost(UUVParser.HostContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitInvocation(UUVParser.InvocationContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#list}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitList(UUVParser.ListContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#missionName}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitMissionName(UUVParser.MissionNameContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitModel(UUVParser.ModelContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#portStart}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitPortStart(UUVParser.PortStartContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#sensorPort}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitSensorPort(UUVParser.SensorPortContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitSimulation(UUVParser.SimulationContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitSpeed(UUVParser.SpeedContext ctx);

    /**
     * Visit a parseMission tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitUuv(UUVParser.UuvContext ctx);
}
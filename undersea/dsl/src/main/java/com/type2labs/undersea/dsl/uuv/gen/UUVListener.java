// Generated from UUV.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parseMission tree produced by
 * {@link UUVParser}.
 */
interface UUVListener extends ParseTreeListener {
    /**
     * Enter a parseMission tree produced by {@link UUVParser#elem}.
     *
     * @param ctx the parseMission tree
     */
    void enterElem(UUVParser.ElemContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#elems}.
     *
     * @param ctx the parseMission tree
     */
    void enterElems(UUVParser.ElemsContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parseMission tree
     */
    void enterHost(UUVParser.HostContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parseMission tree
     */
    void enterInvocation(UUVParser.InvocationContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#list}.
     *
     * @param ctx the parseMission tree
     */
    void enterList(UUVParser.ListContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#missionName}.
     *
     * @param ctx the parseMission tree
     */
    void enterMissionName(UUVParser.MissionNameContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parseMission tree
     */
    void enterModel(UUVParser.ModelContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#portStart}.
     *
     * @param ctx the parseMission tree
     */
    void enterPortStart(UUVParser.PortStartContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#sensorPort}.
     *
     * @param ctx the parseMission tree
     */
    void enterSensorPort(UUVParser.SensorPortContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parseMission tree
     */
    void enterSimulation(UUVParser.SimulationContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parseMission tree
     */
    void enterSpeed(UUVParser.SpeedContext ctx);

    /**
     * Enter a parseMission tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parseMission tree
     */
    void enterUuv(UUVParser.UuvContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#elem}.
     *
     * @param ctx the parseMission tree
     */
    void exitElem(UUVParser.ElemContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#elems}.
     *
     * @param ctx the parseMission tree
     */
    void exitElems(UUVParser.ElemsContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#host}.
     *
     * @param ctx the parseMission tree
     */
    void exitHost(UUVParser.HostContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#invocation}.
     *
     * @param ctx the parseMission tree
     */
    void exitInvocation(UUVParser.InvocationContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#list}.
     *
     * @param ctx the parseMission tree
     */
    void exitList(UUVParser.ListContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#missionName}.
     *
     * @param ctx the parseMission tree
     */
    void exitMissionName(UUVParser.MissionNameContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#model}.
     *
     * @param ctx the parseMission tree
     */
    void exitModel(UUVParser.ModelContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#portStart}.
     *
     * @param ctx the parseMission tree
     */
    void exitPortStart(UUVParser.PortStartContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#sensorPort}.
     *
     * @param ctx the parseMission tree
     */
    void exitSensorPort(UUVParser.SensorPortContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parseMission tree
     */
    void exitSimulation(UUVParser.SimulationContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parseMission tree
     */
    void exitSpeed(UUVParser.SpeedContext ctx);

    /**
     * Exit a parseMission tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parseMission tree
     */
    void exitUuv(UUVParser.UuvContext ctx);
}
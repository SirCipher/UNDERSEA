// Generated from Sensors.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parseMission tree produced by
 * {@link SensorsParser}.
 */
interface SensorsListener extends ParseTreeListener {
    /**
     * Enter a parseMission tree produced by {@link SensorsParser#change}.
     *
     * @param ctx the parseMission tree
     */
    void enterChange(SensorsParser.ChangeContext ctx);

    /**
     * Enter a parseMission tree produced by {@link SensorsParser#model}.
     *
     * @param ctx the parseMission tree
     */
    void enterModel(SensorsParser.ModelContext ctx);

    /**
     * Enter a parseMission tree produced by {@link SensorsParser#sensor}.
     *
     * @param ctx the parseMission tree
     */
    void enterSensor(SensorsParser.SensorContext ctx);

    /**
     * Exit a parseMission tree produced by {@link SensorsParser#change}.
     *
     * @param ctx the parseMission tree
     */
    void exitChange(SensorsParser.ChangeContext ctx);

    /**
     * Exit a parseMission tree produced by {@link SensorsParser#model}.
     *
     * @param ctx the parseMission tree
     */
    void exitModel(SensorsParser.ModelContext ctx);

    /**
     * Exit a parseMission tree produced by {@link SensorsParser#sensor}.
     *
     * @param ctx the parseMission tree
     */
    void exitSensor(SensorsParser.SensorContext ctx);
}
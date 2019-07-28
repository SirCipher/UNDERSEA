// Generated from Sensors.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parseMission tree produced
 * by {@link SensorsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
interface SensorsVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parseMission tree produced by {@link SensorsParser#change}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitChange(SensorsParser.ChangeContext ctx);

    /**
     * Visit a parseMission tree produced by {@link SensorsParser#model}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitModel(SensorsParser.ModelContext ctx);

    /**
     * Visit a parseMission tree produced by {@link SensorsParser#sensor}.
     *
     * @param ctx the parseMission tree
     * @return the visitor result
     */
    T visitSensor(SensorsParser.SensorContext ctx);
}
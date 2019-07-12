// Generated from Sensors.g4 by ANTLR 4.7.2

package uuv.dsl.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SensorsParser}.
 */
public interface SensorsListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link SensorsParser#model}.
     *
     * @param ctx the parse tree
     */
    void enterModel(SensorsParser.ModelContext ctx);

    /**
     * Exit a parse tree produced by {@link SensorsParser#model}.
     *
     * @param ctx the parse tree
     */
    void exitModel(SensorsParser.ModelContext ctx);

    /**
     * Enter a parse tree produced by {@link SensorsParser#sensor}.
     *
     * @param ctx the parse tree
     */
    void enterSensor(SensorsParser.SensorContext ctx);

    /**
     * Exit a parse tree produced by {@link SensorsParser#sensor}.
     *
     * @param ctx the parse tree
     */
    void exitSensor(SensorsParser.SensorContext ctx);

    /**
     * Enter a parse tree produced by {@link SensorsParser#change}.
     *
     * @param ctx the parse tree
     */
    void enterChange(SensorsParser.ChangeContext ctx);

    /**
     * Exit a parse tree produced by {@link SensorsParser#change}.
     *
     * @param ctx the parse tree
     */
    void exitChange(SensorsParser.ChangeContext ctx);
}
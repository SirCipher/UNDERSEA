/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Generated from UUV.g4 by ANTLR 4.7.2

package com.type2labs.undersea.dsl.uuv.gen;

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
     * Visit a parse tree produced by {@link UUVParser#sensorPort}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSensorPort(UUVParser.SensorPortContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#missionName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMissionName(UUVParser.MissionNameContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#portStart}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPortStart(UUVParser.PortStartContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#simulation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimulation(UUVParser.SimulationContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#speed}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSpeed(UUVParser.SpeedContext ctx);

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
     * Visit a parse tree produced by {@link UUVParser#list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitList(UUVParser.ListContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#elems}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElems(UUVParser.ElemsContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#elem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitElem(UUVParser.ElemContext ctx);

    /**
     * Visit a parse tree produced by {@link UUVParser#uuv}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUuv(UUVParser.UuvContext ctx);
}
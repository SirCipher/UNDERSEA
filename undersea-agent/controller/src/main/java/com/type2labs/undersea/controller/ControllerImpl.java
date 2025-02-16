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

package com.type2labs.undersea.controller;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.controller.controller.*;
import com.type2labs.undersea.controller.controllerPMC.AnalyserPMC;
import com.type2labs.undersea.controller.controllerPMC.ExecutorPMC;
import com.type2labs.undersea.controller.controllerPMC.MonitorPMC;
import com.type2labs.undersea.controller.controllerPMC.PlannerPMC;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("DuplicatedCode")
public class ControllerImpl implements Controller {

    private static final Logger logger = LogManager.getLogger(ControllerImpl.class);
    private static long simulationSpeed;
    private final Knowledge knowledge;
    protected Monitor monitor;
    protected Planner planner;
    private Agent agent;
    private Analyser analyser;
    private Executor executor;
    private Sensor sensor;
    private Effector effector;
    private NetworkInterface networkInterface;
    private long start = System.currentTimeMillis();

    private ControllerImpl(Knowledge knowledge, Monitor monitor, Analyser analyser, Planner planner,
                           Executor executor) {
        this.knowledge = knowledge;
        this.monitor = monitor;
        this.analyser = analyser;
        this.planner = planner;
        this.executor = executor;
    }

    public static Controller PMCimpl() {
        Knowledge knowledge = new Knowledge();

        return new ControllerImpl(knowledge,
                new MonitorPMC(knowledge),
                new AnalyserPMC(knowledge),
                new PlannerPMC(knowledge),
                new ExecutorPMC(knowledge)
        );
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;

        networkInterface = agent.serviceManager().getService(NetworkInterface.class);

        sensor = new Sensor(networkInterface, knowledge);
        effector = new Effector(networkInterface);

        AgentMetaData metaData = agent.metadata();
        simulationSpeed = Long.parseLong((String) metaData.getProperty(AgentMetaData.PropertyKey.SIMULATION_SPEED));
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {
        double initTime = (System.currentTimeMillis() - start) / 1000.0 * simulationSpeed;
        knowledge.addToInitTimeList(initTime);
        logger.info(initTime + "\tRequested UUV state");

        sensor.run();
        double sensorTime = (System.currentTimeMillis() - start) / 1000.0 * simulationSpeed;
        logger.info(sensorTime + "\tReceived:\t" + sensor.getReply());

        monitor.run();
        analyser.run();
        planner.run();
        executor.run();

        double controllerTime = (System.currentTimeMillis() - start) / 1000.0 * simulationSpeed;
        logger.info(controllerTime + "\tNew config:\t" + executor.getCommand());

        effector.setCommand(executor.getCommand());
        effector.run();

        double endTime = (System.currentTimeMillis() - start) / 1000.0 * simulationSpeed;
        logger.info(endTime + "\tApplied?\t" + effector.getReply());
        knowledge.addToEndTimeList(endTime);
    }

    @Override
    public void shutdown() {
        if (networkInterface != null) {
            networkInterface.shutdown();
        }
    }

    @Override
    public boolean started() {
        return true;
    }

    @Override
    public Object executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

    }

}

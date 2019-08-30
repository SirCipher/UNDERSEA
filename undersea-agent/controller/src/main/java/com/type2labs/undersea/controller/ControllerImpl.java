package com.type2labs.undersea.controller;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.common.service.hardware.NetworkInterface;
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

    public static Controller PMCimpl() {
        Knowledge knowledge = new Knowledge();

        return new ControllerImpl(knowledge,
                new MonitorPMC(knowledge),
                new AnalyserPMC(knowledge),
                new PlannerPMC(knowledge),
                new ExecutorPMC(knowledge)
        );
    }


    private ControllerImpl(Knowledge knowledge, Monitor monitor, Analyser analyser, Planner planner,
                           Executor executor) {
        this.knowledge = knowledge;
        this.monitor = monitor;
        this.analyser = analyser;
        this.planner = planner;
        this.executor = executor;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;

        networkInterface = agent.services().getService(NetworkInterface.class);

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
        logger.info(endTime + "\tApplied?\t" + effector.getReply() + "\n");
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
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        throw new NotSupportedException();
    }

}

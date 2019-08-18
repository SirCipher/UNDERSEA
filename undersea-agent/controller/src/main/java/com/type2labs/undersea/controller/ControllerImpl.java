package com.type2labs.undersea.controller;

import com.type2labs.undersea.common.agent.AbstractAgent;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.controller.Controller;
import com.type2labs.undersea.controller.controller.*;
import com.type2labs.undersea.controller.controller.comms.Client;
import com.type2labs.undersea.utilities.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("DuplicatedCode")
public class ControllerImpl implements Controller {

    // TODO: Pass properties to controller
    private static final Properties properties = new Properties(); //Utility.getPropertiesByName("config.properties");
    private static long SIMULATION_SPEED;
    private static final Logger logger = LogManager.getLogger(ControllerImpl.class);

    protected Monitor monitor;
    protected Analyser analyser;
    protected Planner planner;
    protected Executor executor;
    private Sensor sensor;
    private Effector effector;
    private Client client;
    private String host;//	= "localhost";
    private int port;// 		= 8888;
    private long start = System.currentTimeMillis();
    private final Agent agent;

    public ControllerImpl(Agent agent, Monitor monitor, Analyser analyser, Planner planner, Executor executor) {
        this.agent = agent;
        //init comms client
        host = "localhost";

        Client client = new Client();

        //init MAPE
        sensor = new Sensor(client);
        effector = new Effector(client);

        this.monitor = monitor;
        this.analyser = analyser;
        this.planner = planner;
        this.executor = executor;

        //init simulation speed
        SIMULATION_SPEED = 100;//Math.round(Double.parseDouble(Utility.getProperty(properties, "SIMULATION_SPEED")));
    }

    @Override
    public void shutdown() {
        boolean closed = false;

        try {
            closed = client.shutDown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (closed) {
                System.out.println("Comms terminated correctly!");
            } else {
                System.out.println("Something was wrong with terminating comms!");
            }
        }
    }

    @Override
    public void run() {
        double initTime = (System.currentTimeMillis() - start) / 1000.0 * SIMULATION_SPEED;
        Knowledge.getInstance().addToInitTimeList(initTime);
        logger.info(initTime + "\tRequested UUV state");

        sensor.run();
        double sensorTime = (System.currentTimeMillis() - start) / 1000.0 * SIMULATION_SPEED;
        logger.info(sensorTime + "\tReceived:\t" + sensor.getReply());

        monitor.run();
        analyser.run();
        planner.run();
        executor.run();

        double controllerTime = (System.currentTimeMillis() - start) / 1000.0 * SIMULATION_SPEED;
        logger.info(controllerTime + "\tNew config:\t" + executor.getCommand());

        effector.setCommand(executor.getCommand());
        effector.run();

        double endTime = (System.currentTimeMillis() - start) / 1000.0 * SIMULATION_SPEED;
        logger.info(endTime + "\tApplied?\t" + effector.getReply() + "\n");
        Knowledge.getInstance().addToEndTimeList(endTime);
    }

    @Override
    public Agent agent() {
        return agent;
    }
}

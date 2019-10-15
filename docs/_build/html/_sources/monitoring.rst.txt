.. _monitoring:


Subsystem Monitoring
==================

UNDERSEA features a subsystem monitoring system that users can register callbacks with to perform various activities against. This may include logging their current state or validating their health. This functionality is demonstrated in this tutorial.

Subsystems in UNDERSEA are an important part of how the consensus algorithm achieves a deterministic consensus. The consensus is a function of the UUV's current battery level, speed and all the subsystem's current state. Users may wish to alter the system's representation of a subsystem based on signals not received through UNDERSEA itself. To achieve this, users can utilise monitor callbacks.


Subsystems are registered against a :code:`SubsystemMonitor` instance. For the purposes of local development, a :code:`SubsystemMonitorSpoofer` instance is used which decays a subsystem's performance over time. Lets create a simple sensor and register it:


.. code-block:: java

    SubsystemMonitorSpoofer monitorSpoofer = new SubsystemMonitorSpoofer();
    monitorSpoofer.monitorSubsystem(new Sensor("test", 50, 50, Sensor.SensorType.SIDESCAN_SONAR));

From here, we can register a monitor callback that will shutdown the system if the sensor's health drops below 10%:

.. code-block:: java

    monitorSpoofer.setMonitorCallback((subsystem) -> {
        if (subsystem.health() < 10) {
            throw new UnderseaException(ErrorCode.SERVICE_FAILED, monitorSpoofer, subsystem.name() + " health is too low to continue");
        }
    });

Now we can spoof this sensor's health degrading using the :code:`ServiceManager`'s task scheduling system:

.. code-block:: java

    serviceManager.scheduleTask(() -> {
        for (Subsystem s : monitorSpoofer.getSubsystems()) {
            Sensor sensor = (Sensor) s;
            sensor.setHealth(0);
        }
    }, 5);

This creates a task that will start in five seconds time and drops the sensor's health to zero. Subsystem monitoring occurs every 500 milliseconds and will call the callback that we registered against the :code:`monitorSpoofer` instance. This will cause our agent to fail and initiate shutdown procedure.

Complete code
+++++++++++++


.. code-block:: java

    package com.type2labs.undersea.tutorials.tutorialc;

    import com.type2labs.undersea.common.agent.*;
    import com.type2labs.undersea.common.consensus.ConsensusClusterConfig;
    import com.type2labs.undersea.common.logger.LogServiceImpl;
    import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
    import com.type2labs.undersea.common.service.ServiceManager;
    import com.type2labs.undersea.prospect.impl.ConsensusNodeImpl;
    import com.type2labs.undersea.tutorials.tutoriala.MissionManagerSample;
    import com.type2labs.undersea.utilities.exception.ErrorCode;
    import com.type2labs.undersea.utilities.exception.UnderseaException;
    import org.apache.logging.log4j.Level;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.core.LoggerContext;
    import org.apache.logging.log4j.core.config.Configuration;
    import org.apache.logging.log4j.core.config.LoggerConfig;

    import java.util.List;

    public class RunnerC {

        static {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig("io.netty");
            loggerConfig.setLevel(Level.INFO);
            ctx.updateLoggers();
        }

        public static void main(String[] args) {
            AgentFactory agentFactory = new AgentFactory();
            List<Agent> agents = agentFactory.createN(5);

            for (Agent agent : agents) {
                ServiceManager serviceManager = agent.serviceManager();
                serviceManager.registerService(new ConsensusNodeImpl(new ConsensusClusterConfig()));

                SubsystemMonitorSpoofer monitorSpoofer = new SubsystemMonitorSpoofer();
                monitorSpoofer.monitorSubsystem(new Sensor("test", 50, 50, Sensor.SensorType.SIDESCAN_SONAR));

                monitorSpoofer.setMonitorCallback((subsystem) -> {
                    if (subsystem.health() < 10) {
                        throw new UnderseaException(ErrorCode.SERVICE_FAILED, monitorSpoofer, subsystem.name() + " health is too low to continue");
                    }
                });

                serviceManager.registerService(monitorSpoofer);
                serviceManager.registerService(new MissionManagerSample());
                serviceManager.registerService(new LogServiceImpl());

                serviceManager.scheduleTask(() -> {
                    for (Subsystem s : monitorSpoofer.getSubsystems()) {
                        Sensor sensor = (Sensor) s;
                        sensor.setHealth(0);
                    }
                }, 5);

                serviceManager.startServices();

                agent.state().transitionTo(AgentState.State.ACTIVE);
            }

            for (Agent a : agents) {
                for (Agent b : agents) {
                    if (a != b) {
                        ConsensusNodeImpl consensusNodeA = a.serviceManager().getService(ConsensusNodeImpl.class);
                        ConsensusNodeImpl consensusNodeB = b.serviceManager().getService(ConsensusNodeImpl.class);

                        consensusNodeA.state().discoverNode(consensusNodeB);
                    }
                }
            }

        }

    }

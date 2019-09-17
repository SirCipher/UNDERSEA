.. _tutorial1:


Creating A Cluster
==================

Local clusters can be defined easily through using the :code:`AgentFactory` class. The :code:`AgentFactory` will initialise `n` (provided) agents with the functionality required for an :code:`Agent` to run; initialising a :code:`ServiceManager` and other core functionality. Let's dive in and create five agents:


.. code-block:: java

    AgentFactory agentFactory = new AgentFactory();
    List<Agent> agents = agentFactory.createN(5);

The above initialises each agent with a unique, human-readable, name, a :code:`PeerId` (which uniquely identifies each :code:`Agent`), and a :code:`ServiceManager` that has no registered services. This is as bare-bones that you can get to a default template for creating an :code:`Agent`. 

The Service Manager
+++++++++++++++++++

The :code:`ServiceManager` is the backbone of each :code:`Agent`. Each :code:`Agent` is assigned one and can have a number of :code:`AgentService` instances registered against it. The :code:`ServiceManager` will manage initialising each registered service, ensuring that they start correctly and transition to a running state, or, if the service fails to start or fails at a later time, notifies all running services that a particular service has failed. Lets initialise all of our :code:`Agents` with a few services:

.. code-block:: java

       for (Agent agent : agents) {
            ServiceManager serviceManager = agent.serviceManager();

            serviceManager.registerService(new RaftNodeImpl(new RaftClusterConfig()));
            serviceManager.registerService(new SubsystemMonitorSpoofer());
            serviceManager.registerService(new MissionManagerSample());
            serviceManager.registerService(new LogServiceImpl());

            serviceManager.startServices();

            agent.state().transitionTo(AgentState.State.ACTIVE);
        }

All services must implement the :code:`AgentService` interface in order to be able to be registered. In this interface, exists much of the functionality that the :code:`ServiceManager` uses to manage services effectively. For example, the service :code:`RaftNodeImpl` requires a number of services in order for itself to function correctly and these are defined by overriding the :code:`AgentService#requiredServices` method which the :code:`ServiceManager` will ensure are registered during its startup procedure. In addition to this, some services may require a long time to startup and transition to a running state but the :code:`ServiceManager` only allows for a certain transition timeout period to elapse, this can be overridden by :code:`AgentService#transitionTimeout`. In order to initiate the aforementioned process, the :code:`Agent` is started by :code:`serviceManager.startServices()` and requires transitioning to an active state: :code:`agent.state().transitionTo(AgentState.State.ACTIVE)`

Clients
+++++++
The UNDERSEA project has been developed with the view that the end-product will be a number of active robots in some unknown environment and as such, no automatic peer discovery is implemented at this time. However, local node discovery can be performed as follows:

.. code-block:: java

        for (Agent a : agents) {
            for (Agent b : agents) {
                if (a != b) {
                    RaftNodeImpl raftNodeA = a.serviceManager().getService(RaftNodeImpl.class);
                    RaftNodeImpl raftNodeB = b.serviceManager().getService(RaftNodeImpl.class);

                    raftNodeA.state().discoverNode(raftNodeB);
                }
            }
        }

This will inform the :code:`Agent` of another client and build a gRPC client in order to perform the required Raft tasks throughout its availability. Once an :code:`Agent` discovers another, an automatic voting round is started and a leader is elected. 

Raft
++++

The UNDERSEA project uses a custom implementation of the Raft consensus algorithm in order deterministically elect a leader within a cluster. This leader will perform a number of tasks and manage the cluster state and work towards ensuring that a mission is completed successfully. This follows a three step process:

- Each node finds out the current state of every client in the cluster and works out an overall cost associated with that client; comprised of, the client's battery level, number of subsystems that are present on it and their accuracy. This can be configured in your implementation. We wouldn't want a leader to be elected and to perform more tasks when they have low battery.
- Every node votes for who they calculated to have the lowest cost. 
- The leader is elected and transitions to a leader state.

This process happens every time a client joins and leaves a cluster to ensure that the most suitable leader is always elected. Following this, there are two further principles that can be introduced: :code:`Transactions` and :code:`ServiceCallbacks`.

Transactions and Callbacks
++++++++++++++++++++++++++

:code:`Transactions` lie at the core of how an :code:`AgentService` can communicate with one another in a simple fashion. If, for example, a service wishes to propagate a particular life cycle event to all other services, then a :code:`Transaction` is the most suitable choice. In the example below, the :code:`Transaction` and handling the response, is registered as a :code:`ServiceCallback` against the :code:`RaftNodeImpl` which will fire automatically when the node is elected as a leader.

.. code-block:: java

    raftNode.registerCallback(new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> {
        Transaction transaction = new Transaction.Builder(agent)
                .forService(MissionManager.class)
                .withStatus(LifecycleEvent.ELECTED_LEADER)
                .usingExecutorService(raftNode.getListeningExecutorService())
                .invokedBy(raftNode)
                .build();

        Set<ListenableFuture<?>> futures = agent.serviceManager().commitTransaction(transaction);

        for (ListenableFuture<?> future : futures) {
            Futures.addCallback(future, new FutureCallback<Object>() {
                @Override
                public void onSuccess(@Nullable Object result) {
                    raftNode.distributeMission((GeneratedMission) result);
                }

                @Override
                public void onFailure(Throwable t) {
                    throw new RuntimeException(t);
                }

            }, raftNode.getSingleThreadScheduledExecutor());
        }
    }));

The above will construct a :code:`Transaction` and commit it to the registered :code:`MissionManager` service and notify it that the :code:`RaftNode` has been elected the cluster leader and the :code:`ServiceManager` will commit the transaction to that service and return a set of futures that we can add callbacks. This, however, requires that the :code:`MissionManager` has overridden the :code:`AgentService#executeTransaction`. If not, the transaction will be lost and nothing will be returned. The destination service can switch on the status that the transaction has marked on it to ensure that it executes the correct path. The sample implementation for MOOS uses this system to fire a transaction to all services and transitions the system state to a leader elected state. This causes the registered mission manager to decompose the target polygon to over, generate mission paths for each client and distribute them respectively.

Complete code
-------------

.. code-block:: java

    package com.type2labs.undersea.tutorials.tutoriala;


    import com.google.common.util.concurrent.FutureCallback;
    import com.google.common.util.concurrent.Futures;
    import com.google.common.util.concurrent.ListenableFuture;
    import com.type2labs.undersea.common.agent.Agent;
    import com.type2labs.undersea.common.agent.AgentFactory;
    import com.type2labs.undersea.common.agent.AgentState;
    import com.type2labs.undersea.common.consensus.RaftClusterConfig;
    import com.type2labs.undersea.common.logger.LogServiceImpl;
    import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
    import com.type2labs.undersea.common.missions.planner.model.MissionManager;
    import com.type2labs.undersea.common.monitor.impl.SubsystemMonitorSpoofer;
    import com.type2labs.undersea.common.service.ServiceManager;
    import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
    import com.type2labs.undersea.common.service.transaction.ServiceCallback;
    import com.type2labs.undersea.common.service.transaction.Transaction;
    import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
    import org.apache.logging.log4j.Level;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.core.LoggerContext;
    import org.apache.logging.log4j.core.config.Configuration;
    import org.apache.logging.log4j.core.config.LoggerConfig;
    import org.checkerframework.checker.nullness.qual.Nullable;

    import java.util.List;
    import java.util.Set;

    public class RunnerA {

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
                RaftNodeImpl raftNode = new RaftNodeImpl(new RaftClusterConfig());
                raftNode.registerCallback(new ServiceCallback(LifecycleEvent.ELECTED_LEADER, () -> {
                    Transaction transaction = new Transaction.Builder(agent)
                            .forService(MissionManager.class)
                            .withStatus(LifecycleEvent.ELECTED_LEADER)
                            .usingExecutorService(raftNode.getListeningExecutorService())
                            .invokedBy(raftNode)
                            .build();

                    Set<ListenableFuture<?>> futures = agent.serviceManager().commitTransaction(transaction);

                    for (ListenableFuture<?> future : futures) {
                        Futures.addCallback(future, new FutureCallback<Object>() {
                            @Override
                            public void onSuccess(@Nullable Object result) {
                                raftNode.distributeMission((GeneratedMission) result);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                throw new RuntimeException(t);
                            }

                        }, raftNode.getSingleThreadScheduledExecutor());
                    }
                }));

                serviceManager.registerService(raftNode);
                serviceManager.registerService(new SubsystemMonitorSpoofer());
                serviceManager.registerService(new MissionManagerSample());
                serviceManager.registerService(new LogServiceImpl());

                serviceManager.startServices();

                agent.state().transitionTo(AgentState.State.ACTIVE);
            }

            for (Agent a : agents) {
                for (Agent b : agents) {
                    if (a != b) {
                        RaftNodeImpl raftNodeA = a.serviceManager().getService(RaftNodeImpl.class);
                        RaftNodeImpl raftNodeB = b.serviceManager().getService(RaftNodeImpl.class);

                        raftNodeA.state().discoverNode(raftNodeB);
                    }
                }
            }

        }

    }

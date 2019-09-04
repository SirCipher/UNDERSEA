package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.monitor.VisualiserData;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class VisualiserClientImpl implements VisualiserClient {

    private static final Logger logger = LogManager.getLogger(VisualiserClientImpl.class);
    private static boolean enabled = false;
    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private Agent parent;

    private synchronized void _write(Object data) {
        ObjectOutputStream oos;
        SocketChannel channel;

        try {
            channel = SocketChannel.open();

            channel.configureBlocking(true);
            channel.socket().setKeepAlive(false);
            channel.connect(visualiserAddress);
            channel.finishConnect();

            oos = new ObjectOutputStream(channel.socket().getOutputStream());
        } catch (IOException e) {
            enabled = false;
            logger.error(parent.name() + ": failed to connect to visualiser", e);
            return;
        }

        try {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            logger.error(parent.name() + ": failed to send message {" + data + "} to visualiser. Disabling", e);
        }

        try {
            oos.close();
            channel.close();
        } catch (IOException e) {
            logger.error(parent.name() + ": failed to close channel", e);
        }
    }

    private VisualiserData agentState() {
        ServiceManager serviceManager = parent.services();

        ConsensusAlgorithm consensusAlgorithm = serviceManager.getService(ConsensusAlgorithm.class);

        String raftRole = "";
        String multiRoleStatus = "";

        if (consensusAlgorithm != null) {
            raftRole = String.valueOf(consensusAlgorithm.getRaftRole());
            multiRoleStatus = String.valueOf(consensusAlgorithm.multiRole().getStatus());
        }

        MissionManager missionPlanner = serviceManager.getService(MissionManager.class);
        List<Task> assignedTasks = new ArrayList<>();

        if(missionPlanner!=null){
            assignedTasks = missionPlanner.getAssignedTasks();
        }

        String serviceManagerStatus;

        if (serviceManager.isStarting()) {
            serviceManagerStatus = "STARTING";
        } else {
            serviceManagerStatus = serviceManager.isHealthy() ? "HEALTHY" : "UNHEALTHY";
        }

        int completedTasks =
                Math.toIntExact(assignedTasks.stream().filter(t -> t.getTaskStatus() == TaskStatus.COMPLETED).count());

        return new VisualiserData(parent.name(),
                parent.peerId().toString(),
                multiRoleStatus,
                serviceManagerStatus,
                raftRole,
                assignedTasks.size(),
                completedTasks);
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.parent = parentAgent;
        enabled = parent.config().isVisualiserEnabled();
        run();
    }

    @Override
    public Agent parent() {
        return parent;
    }

    @Override
    public void run() {
        if (!enabled) {
            return;
        }

        sendVisualiserData();
    }

    private void sendVisualiserData() {
        if (!enabled) {
            return;
        }

        if (parent == null) {
            logger.error("Cannot start connection to monitor without a parent");
            return;
        }

        _write(agentState());
    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean started() {
        return true;
    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public long transitionTimeout() {
        return 0;
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

    }

    @Override
    public void write(String data) {
        VisualiserMessage visualiserMessage = new VisualiserMessage(parent.name(), data + "\n");
        write(visualiserMessage);
    }

    @Override
    public void write(VisualiserMessage data) {
        if (!enabled) {
            return;
        }

        _write(data);
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void update() {
        sendVisualiserData();
    }
}

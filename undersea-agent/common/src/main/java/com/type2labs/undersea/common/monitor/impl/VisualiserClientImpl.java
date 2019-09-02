package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.monitor.VisualiserData;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class VisualiserClientImpl implements VisualiserClient {

    private static final Logger logger = LogManager.getLogger(VisualiserClientImpl.class);

    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private Agent parent;
    private static boolean enabled = false;

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
        ConsensusAlgorithm raftNode = parent.services().getService(ConsensusAlgorithm.class);
        String raftRole = String.valueOf(raftNode.getRaftRole());

        MissionManager missionPlanner = parent.services().getService(MissionManager.class);
        List<Task> assignedTasks = missionPlanner.getAssignedTasks();
        int completedTasks = Math.toIntExact(assignedTasks.stream().filter(t -> t.getTaskStatus() == TaskStatus.COMPLETED).count());

        return new VisualiserData(parent.name(), parent.peerId().toString(), raftRole, assignedTasks.size(), completedTasks);
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
        LogMessage logMessage = new LogMessage(parent.name(), data + "\n");
        write(logMessage);
    }

    @Override
    public void write(LogMessage data) {
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

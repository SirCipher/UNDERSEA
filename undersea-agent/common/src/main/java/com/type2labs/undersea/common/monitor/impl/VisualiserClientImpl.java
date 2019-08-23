package com.type2labs.undersea.common.monitor.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.monitor.VisualiserData;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class VisualiserClientImpl implements VisualiserClient {

    private static final Logger logger = LogManager.getLogger(VisualiserClientImpl.class);

    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private Agent parent;
    private SocketChannel channel;
    private boolean enabled = false;

    public VisualiserClientImpl() {

    }

    private boolean openConnection() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            channel.socket().setKeepAlive(true);
            channel.connect(visualiserAddress);

            while (!channel.finishConnect()) {
                // TODO: Set timeout
            }

            return true;
        } catch (IOException e) {
            // TODO
            return false;
        }
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

    private void _write(Object data) {
        if(!openConnection()){
            return;
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());

            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VisualiserData agentState() {
        ConsensusAlgorithm raftNode = parent.services().getService(ConsensusAlgorithm.class);
        String raftRole = String.valueOf(raftNode.getRaftRole());

        MissionPlanner missionPlanner = parent.services().getService(MissionPlanner.class);
        int noTasks = missionPlanner.getTasks().size();

        return new VisualiserData(parent.name(), parent.peerId().toString(), raftRole, noTasks,
                new double[]{0, 0});
    }

    private void sendVisualiserData() {
        if (parent == null) {
            throw new IllegalStateException("Cannot start connection to monitor without a parent");
        }

        if (!enabled) {
            return;
        }


        _write(agentState());
    }

    @Override
    public void closeConnection() throws IOException {
        channel.close();
    }

    @Override
    public void update() {
        sendVisualiserData();
    }


    @Override
    public void shutdown() {

    }

    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        return null;
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
}

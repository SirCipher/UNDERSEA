package com.type2labs.undersea.monitor;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.LogMessage;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.visualiser.VisualiserClient;
import com.type2labs.undersea.common.visualiser.VisualiserData;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class VisualiserClientImpl implements VisualiserClient {

    private static final Logger logger = LogManager.getLogger(VisualiserClientImpl.class);

    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private Agent parent;
    private SocketChannel channel;
    private boolean initialised = false;
    private boolean enabled = false;

    public VisualiserClientImpl(Agent parent) {
        this.parent = parent;
        enabled = parent.config().isVisualiserEnabled();

        if (!enabled) {
            return;
        }

        try {
            openConnection();
            sendVisualiserData();
        } catch (ConnectException e) {
            logger.warn("Couldn't connect to monitor, trying again");
            parent.schedule(new VisualiserConnectionTask(parent));
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to monitor. Is it running?", e);
        }
    }

    private void openConnection() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            channel.socket().setKeepAlive(true);
            channel.connect(visualiserAddress);

            while (!channel.finishConnect()) {
                // TODO: Set timeout
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO
        }

    }

    @Override
    public void write(String data) throws IOException {
        LogMessage logMessage = new LogMessage(parent.name(), data + "\n");
        write(logMessage);
    }

    @Override
    public void write(LogMessage data) throws IOException {
        if (!enabled) {
            return;
        }

        _write(data);
    }

    private void _write(Object data) throws IOException {
        openConnection();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());

            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VisualiserData agentState() {
        RaftNode raftNode = (RaftNode) parent.services().getService(ConsensusAlgorithm.class);
        String raftRole = String.valueOf(raftNode.getRaftRole());

        MissionPlanner missionPlanner = (MissionPlanner) parent.services().getService(MissionPlanner.class);
        int noTasks = missionPlanner.getTasks().size();

        return new VisualiserData(parent.name(), raftRole, noTasks, new double[]{0, 0});
    }

    private void sendVisualiserData() throws IOException {
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
        try {
            sendVisualiserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

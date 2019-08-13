package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
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
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            channel.connect(visualiserAddress);

            while (!channel.finishConnect()) {
                // TODO: Set timeout
            }

            initialised = true;
            openConnection();
        } catch (ConnectException e) {
            logger.warn("Couldn't connect to visualiser, trying again");
            parent.schedule(new VisualiserConnectionTask(parent));
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to visualiser. Is it running?", e);
        }
    }

    public boolean isInitialised() {
        return initialised;
    }

    @Override
    public void write(VisualiserData data) throws IOException {
        if (!enabled) {
            return;
        }
    }

    private VisualiserData agentState() {
        RaftNode raftNode = (RaftNode) parent.services().getService(ConsensusAlgorithm.class);
        String raftRole = String.valueOf(raftNode.getRaftRole());

        MissionPlanner missionPlanner = (MissionPlanner) parent.services().getService(MissionPlanner.class);
        int noTasks = missionPlanner.getTasks().size();

        return new VisualiserData(parent.name(), raftRole, noTasks, new double[]{0, 0});
    }

    private void openConnection() throws IOException {
        if (parent == null) {
            throw new IllegalStateException("Cannot start connection to visualiser without a parent");
        }

        if (!enabled) {
            return;
        }

        if (!isInitialised()) {
            logger.warn("Not initialised yet");
            return;
        }

        ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());

        oos.flush();
        oos.writeObject(agentState());
        oos.close();
    }

    @Override
    public void closeConnection() throws IOException {
        if (isInitialised()) {
            channel.close();
        }
    }


}

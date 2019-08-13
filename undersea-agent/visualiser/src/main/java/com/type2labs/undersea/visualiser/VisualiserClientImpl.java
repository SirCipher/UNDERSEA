package com.type2labs.undersea.visualiser;

import com.type2labs.undersea.common.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.missionplanner.MissionPlanner;
import com.type2labs.undersea.common.visualiser.VisualiserClient;
import com.type2labs.undersea.common.visualiser.VisualiserData;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

// TODO: Add read/write replies
public class VisualiserClientImpl implements VisualiserClient {

    private Agent parent;
    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private final SocketChannel channel;

    public VisualiserClientImpl() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(true);
            channel.connect(visualiserAddress);

            while (!channel.finishConnect()) {
                // TODO: Set timeout
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to visualiser. Is it running?");
        }
    }

    public void setParent(Agent parent) {
        this.parent = parent;
    }

    @Override
    public void write(VisualiserData data) throws IOException {

    }

    private VisualiserData agentState(){
        RaftNode raftNode = (RaftNode) parent.services().getService(ConsensusAlgorithm.class);
        String raftRole = String.valueOf(raftNode.getRaftRole());

        MissionPlanner missionPlanner = (MissionPlanner) parent.services().getService(MissionPlanner.class);
        int noTasks = missionPlanner.getTasks().size();

        return new VisualiserData(parent.name(), raftRole, noTasks, new double[]{0, 0});
    }

    @Override
    public void openConnection() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());

        oos.flush();
        oos.writeObject(agentState());
        oos.close();
    }

    @Override
    public void closeConnection() throws IOException {
        channel.close();
    }


}

package com.type2labs.undersea.common.monitor.impl;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.VisualiserMessage;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.monitor.VisualiserData;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.networking.SimpleServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class VisualiserClientImpl implements VisualiserClient {

    private static final Logger logger = LogManager.getLogger(VisualiserClientImpl.class);
    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private boolean enabled = false;
    private Agent parent;
    private int serverPort;
    private boolean running = false;

    private void runServer() {
        ServerSocket s;
        try {
            s = new ServerSocket(0);
            s.close();
        } catch (IOException e) {
            throw new UnderseaException(parent.name() + ": failed to start visualiser server", e);
        }

        serverPort = s.getLocalPort();

        SimpleServer server = new SimpleServer(serverPort, this::processRequest, logger);
        server.runServer();
    }

    private void processRequest(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String request = br.readLine();

            logger.info(parent.name() + ": received message: " + request, this::parent);

            LifecycleEvent lifecycleEvent = LifecycleEvent.valueOf(request);
            if (lifecycleEvent == LifecycleEvent.SHUTDOWN) {
                parent.services().shutdownServices();
            }

            inputStream.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            logger.error(parent.name() + ": failed to read message from: " + socket.getLocalPort(), e);
        }
    }

    private synchronized void _write(Object data) {
        if (!enabled) {
            return;
        }

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

    private synchronized VisualiserData agentState() {
        ServiceManager serviceManager = parent.services();

        ConsensusAlgorithm consensusAlgorithm = serviceManager.getService(ConsensusAlgorithm.class);

        String raftRole = "";
        String multiRoleStatus = "";
        String leaderPeerId = "N/A";
        int noPeers = parent.clusterClients().size();

        if (consensusAlgorithm != null) {
            raftRole = String.valueOf(consensusAlgorithm.getRaftRole());
            multiRoleStatus = String.valueOf(consensusAlgorithm.multiRole().getStatus());

            PeerId peerId = consensusAlgorithm.leaderPeerId();

            if (peerId == null) {
                leaderPeerId = "N/A";
            } else {
                leaderPeerId = String.valueOf(peerId);
            }
        }

        MissionManager missionPlanner = serviceManager.getService(MissionManager.class);
        List<Task> assignedTasks = new ArrayList<>();

        if (missionPlanner != null) {
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
                leaderPeerId,
                assignedTasks.size(),
                completedTasks,
                serverPort,
                noPeers);
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
    public synchronized void run() {
        if (!enabled || running) {
            return;
        }

        running = true;

        runServer();
        parent.services().startRepeatingTask(this::sendVisualiserData, 500);
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
    public void write(String data) {
        VisualiserMessage visualiserMessage = new VisualiserMessage(parent.peerId(), data + "\n");
        write(visualiserMessage);
    }

    @Override
    public void shutdown() {
        VisualiserData visualiserData = new VisualiserData(parent.peerId().toString(), parent.name());
        visualiserData.setServiceManagerStatus("SHUTDOWN");

        _write(visualiserData);

        enabled = false;
    }

    @Override
    public void write(VisualiserMessage data) {
        if (!enabled) {
            return;
        }

        _write(data);
    }

    @Override
    public void update() {
        sendVisualiserData();
    }

}

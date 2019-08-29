package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class AcquireStatusTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(AcquireStatusTask.class);
    private final RaftNode raftNode;

    public AcquireStatusTask(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void run() {
        Collection<Client> localNodes = raftNode.state().localNodes().values();

        if (localNodes.size() == 0) {
            logger.info(raftNode.name() + " has no local nodes", raftNode.agent());
            raftNode.schedule(new AcquireStatusTask(raftNode), 500);
            return;
        }

        for (Client localNode : localNodes) {
            RaftClient raftClient = (RaftClient) localNode;

            RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                    .newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .build();

            RaftProtos.AcquireStatusResponse response;
            ClusterState clusterState = raftNode.state().clusterState();

            try {
                RaftClusterConfig config = (RaftClusterConfig) raftNode.config();

                response = raftClient.getStatus(request, config.getStatusDeadline());

                ClusterState.ClientState agentInfo = new ClusterState.ClientState(localNode,
                        GrpcUtil.protoTupleToPair(response.getStatusList()));
                clusterState.setAgentInformation(localNode, agentInfo);
            } catch (StatusRuntimeException e) {
                Status.Code code = e.getStatus().getCode();

                if (code.equals(Status.Code.DEADLINE_EXCEEDED)) {
                    ClusterState.ClientState agentInfo = new ClusterState.ClientState(localNode, false);
                    clusterState.setAgentInformation(localNode, agentInfo);
                }
            }

        }
    }
}

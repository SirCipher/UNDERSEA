package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.ClusterState;
import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.RaftClient;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import com.type2labs.undersea.utilities.lang.ReschedulableTask;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class AcquireStatusTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(AcquireStatusTask.class);
    private final RaftNode raftNode;
    private int noResponses = 0;
    private int clusterSize;

    public AcquireStatusTask(RaftNode raftNode) {
        this.raftNode = raftNode;
        this.clusterSize = raftNode.parent().clusterClients().size();
    }

    @Override
    public void run() {
        Collection<Client> localNodes = raftNode.state().localNodes().values();

        raftNode.state().initPreVoteClusterState();

        if (localNodes.size() == 0) {
            raftNode.schedule(ReschedulableTask.wrap(new AcquireStatusTask(raftNode)), 100);
            return;
        }

        for (Client localNode : localNodes) {
            RaftClient raftClient = (RaftClient) localNode;

            RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                    .newBuilder()
                    .setClient(GrpcUtil.toProtoClient(raftNode))
                    .build();

            RaftProtos.AcquireStatusResponse response;
            ClusterState preVoteClusterState = raftNode.state().getPreVoteClusterState();

            try {
                RaftClusterConfig config = (RaftClusterConfig) raftNode.config();

                response = raftClient.getStatus(request, config.getStatusDeadline());

                ClusterState.ClientState agentInfo = new ClusterState.ClientState(localNode, response.getCost());
                preVoteClusterState.setAgentInformation(localNode, agentInfo);

                incrementAndVote();
            } catch (StatusRuntimeException e) {
                Status.Code code = e.getStatus().getCode();

                if (code.equals(Status.Code.DEADLINE_EXCEEDED)) {
                    incrementAndVote();
                }
            }
        }
    }

    private void incrementAndVote() {
        noResponses++;

        if (noResponses == clusterSize) {
            raftNode.execute(new VoteTask(raftNode));
        }
    }
}

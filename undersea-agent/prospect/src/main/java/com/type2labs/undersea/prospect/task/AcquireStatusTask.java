package com.type2labs.undersea.prospect.task;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
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

        for (Client localNode : localNodes) {
            RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                    .newBuilder()
                    .setClient(GrpcUtil.toRaftPeer(raftNode))
                    .build();

            localNode.getStatus(request, new FutureCallback<RaftProtos.AcquireStatusResponse>() {
                @Override
                public void onSuccess(RaftProtos.AcquireStatusResponse result) {
                    PoolInfo poolInfo = raftNode.poolInfo();
                    PoolInfo.AgentInfo agentInfo = new PoolInfo.AgentInfo(localNode, result.getStatusList());
                    poolInfo.setAgentInformation(localNode, agentInfo);
                }

                @Override
                public void onFailure(Throwable t) {
                    if (t instanceof StatusRuntimeException) {
                        StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                        Status.Code code = statusRuntimeException.getStatus().getCode();

                        if (code.equals(Status.Code.DEADLINE_EXCEEDED)) {
                            PoolInfo poolInfo = raftNode.poolInfo();
                            PoolInfo.AgentInfo agentInfo = new PoolInfo.AgentInfo(localNode, false);
                            poolInfo.setAgentInformation(localNode, agentInfo);
                        }
                    } else {
                        throw new RuntimeException(t);
                    }
                }
            }, raftNode.config().getGetStatusDeadline());

        }

    }
}

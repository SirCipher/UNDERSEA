package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.prospect.AcquireStatusServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;

public class AcquireStatusTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(AcquireStatusTask.class);
    private final RaftNode raftNode;

    public AcquireStatusTask(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    @Override
    public void run() {
        Collection<RaftNodeImpl> localNodes = raftNode.integration().localNodes().values();

        try {
            for (RaftNode localNode : localNodes) {
                RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest.newBuilder().build();
                AcquireStatusServiceGrpc.AcquireStatusServiceBlockingStub blockingStub =
                        AcquireStatusServiceGrpc.newBlockingStub(localNode.getLocalEndpoint().channel());

                // TODO
                RaftProtos.AcquireStatusResponse status = blockingStub.getStatus(request);
                List<RaftProtos.Tuple> statusList = status.getStatusList();

                PoolInfo.AgentInfo agentInfo = new PoolInfo.AgentInfo(statusList);

                raftNode.poolInfo().setAgentInformation(localNode.getLocalEndpoint(), agentInfo);
            }
        } catch (StatusRuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

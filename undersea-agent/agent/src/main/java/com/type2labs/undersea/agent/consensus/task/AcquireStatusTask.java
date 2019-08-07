package com.type2labs.undersea.agent.consensus.task;

import com.type2labs.undersea.agent.consensus.AcquireStatusServiceGrpc;
import com.type2labs.undersea.agent.consensus.RaftProtos;
import com.type2labs.undersea.agent.consensus.impl.PoolInfo;
import com.type2labs.undersea.agent.consensus.impl.RaftNodeImpl;
import com.type2labs.undersea.agent.consensus.model.RaftNode;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

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
                ThreadLocalRandom random = ThreadLocalRandom.current();
                PoolInfo.AgentInfo agentInfo = new PoolInfo.AgentInfo(random.nextInt(100), random.nextDouble(100),
                        random.nextDouble(100));

                raftNode.poolInfo().setAgentInformation(localNode.getLocalEndpoint(), agentInfo);
            }
        } catch (StatusRuntimeException e) {
            logger.warn("RPC failed: " + e.getStatus());
        }
    }
}
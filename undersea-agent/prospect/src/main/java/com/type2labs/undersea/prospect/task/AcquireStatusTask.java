package com.type2labs.undersea.prospect.task;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.prospect.AcquireStatusServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.PoolInfo;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.util.GrpcUtil;
import io.grpc.StatusRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

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
        Collection<RaftNode> localNodes = raftNode.state().localNodes().values();

        try {
            for (RaftNode localNode : localNodes) {
                RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                        .newBuilder()
                        .setClient(GrpcUtil.toRaftPeer(raftNode))
                        .build();

                AcquireStatusServiceGrpc.AcquireStatusServiceFutureStub
                        futureStub =
                        AcquireStatusServiceGrpc.newFutureStub(localNode.getLocalEndpoint().channel());

                ListenableFuture<RaftProtos.AcquireStatusResponse> status = futureStub.getStatus(request);

                Futures.addCallback(status, new FutureCallback<RaftProtos.AcquireStatusResponse>() {
                    @Override
                    public void onSuccess(RaftProtos.@Nullable AcquireStatusResponse result) {
                        System.out.println("Acquire status task response");
                        if (result == null) {
                            return;
                        }

                        List<RaftProtos.Tuple> statusList = result.getStatusList();

                        PoolInfo.AgentInfo agentInfo = new PoolInfo.AgentInfo(localNode.getLocalEndpoint(), statusList);

                        raftNode.poolInfo().setAgentInformation(localNode.getLocalEndpoint(), agentInfo);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                }, MoreExecutors.directExecutor());


            }
        } catch (StatusRuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.type2labs.undersea.prospect.task;

import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;
import com.type2labs.undersea.prospect.util.GrpcUtil;
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

        try {
            for (Client localNode : localNodes) {
                RaftProtos.AcquireStatusRequest request = RaftProtos.AcquireStatusRequest
                        .newBuilder()
                        .setClient(GrpcUtil.toRaftPeer(raftNode))
                        .build();

//                AcquireStatusServiceGrpc.AcquireStatusServiceBlockingStub stub =
//                        AcquireStatusServiceGrpc.newBlockingStub(null);
//                Iterator<RaftProtos.AcquireStatusResponse> status = stub.getStatus(request);

//                while (status.hasNext()) {
//                    System.out.println(status.next());
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

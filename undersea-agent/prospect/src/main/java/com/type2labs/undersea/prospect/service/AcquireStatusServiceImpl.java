package com.type2labs.undersea.prospect.service;


import com.type2labs.undersea.prospect.AcquireStatusServiceGrpc;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AcquireStatusServiceImpl extends AcquireStatusServiceGrpc.AcquireStatusServiceImplBase {

    private final RaftNode raftNode;

    public AcquireStatusServiceImpl(RaftNode raftNode) {
        super();
        this.raftNode = raftNode;
    }

    @Override
    public void getStatus(RaftProtos.AcquireStatusRequest request,
                          StreamObserver<RaftProtos.AcquireStatusResponse> responseObserver) {
        RaftProtos.AcquireStatusResponse.Builder builder = RaftProtos.AcquireStatusResponse.newBuilder();
        List<Pair<String, String>> status = raftNode.agent().status();

        for (Pair<String, String> p : status) {
            RaftProtos.Tuple.Builder tupleBuilder = RaftProtos.Tuple.newBuilder();
            tupleBuilder.setFieldType(p.getKey());
            tupleBuilder.setValue(p.getValue());

            builder.addStatus(tupleBuilder.build());
        }

        RaftProtos.AcquireStatusResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
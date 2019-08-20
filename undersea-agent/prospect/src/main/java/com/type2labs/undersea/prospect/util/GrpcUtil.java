package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.common.cluster.Client;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toProtoClient(Client client) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(client.peerId().toString());

        return builder.build();
    }

    public static RaftProtos.RaftPeerProto toProtoClient(RaftNode raftNode) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(raftNode.peerId().toString());

        return builder.build();
    }


}

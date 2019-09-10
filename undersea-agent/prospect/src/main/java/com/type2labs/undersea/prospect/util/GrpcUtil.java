package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toProtoClient(Client client) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(client.peerId().toString());

        return builder.build();
    }

    public static RaftProtos.RaftPeerProto toProtoClient(RaftNode raftNode) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        builder.setRaftPeerId(raftNode.parent().peerId().toString());

        return builder.build();
    }


}

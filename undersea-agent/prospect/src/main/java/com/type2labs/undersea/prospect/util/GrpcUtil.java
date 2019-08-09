package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.net.InetSocketAddress;

public class GrpcUtil {

    public static RaftProtos.RaftPeer toRaftPeer(RaftNode raftNode) {
        RaftProtos.RaftPeer.Builder builder = RaftProtos.RaftPeer.newBuilder();
        InetSocketAddress address = raftNode.getLocalEndpoint().socketAddress();

        builder.setHost(address.getHostString());
        builder.setPort(address.getPort());

        return builder.build();
    }

}

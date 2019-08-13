package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.common.Endpoint;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.EndpointImpl;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.net.InetSocketAddress;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toRaftPeer(Endpoint endpoint) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        InetSocketAddress address = endpoint.socketAddress();

        builder.setHost(address.getHostString());
        builder.setPort(address.getPort());
        builder.setName(endpoint.name());

        return builder.build();
    }

    public static RaftProtos.RaftPeerProto toRaftPeer(RaftNode raftNode) {
        return toRaftPeer(raftNode.getLocalEndpoint());
    }

    public static Endpoint raftPeerProtoToEndpoint(RaftProtos.RaftPeerProto raftPeerProto) {
        return new EndpointImpl(raftPeerProto.getName(), new InetSocketAddress(raftPeerProto.getHost(),
                raftPeerProto.getPort()));
    }


}

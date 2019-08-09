package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.EndpointImpl;
import com.type2labs.undersea.prospect.model.Endpoint;
import com.type2labs.undersea.prospect.model.RaftNode;

import java.net.InetSocketAddress;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toRaftPeer(RaftNode raftNode) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        InetSocketAddress address = raftNode.getLocalEndpoint().socketAddress();

        builder.setHost(address.getHostString());
        builder.setPort(address.getPort());
        builder.setName(raftNode.getLocalEndpoint().name());

        return builder.build();
    }

    public static Endpoint raftPeerProtoToEndpoint(RaftProtos.RaftPeerProto raftPeerProto) {
        return new EndpointImpl(raftPeerProto.getName(), new InetSocketAddress(raftPeerProto.getHost(),
                raftPeerProto.getPort()));
    }


}

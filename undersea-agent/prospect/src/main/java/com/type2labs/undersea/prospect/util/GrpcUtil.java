package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.networking.Client;

import java.net.InetSocketAddress;

public class GrpcUtil {

    public static RaftProtos.RaftPeerProto toRaftPeer(Client client) {
        RaftProtos.RaftPeerProto.Builder builder = RaftProtos.RaftPeerProto.newBuilder();
        InetSocketAddress address = client.socketAddress();

        builder.setHost(address.getHostString());
        builder.setPort(address.getPort());
//        builder.setName(client.name());

        return builder.build();
    }

    public static RaftProtos.RaftPeerProto toRaftPeer(RaftNode raftNode) {
//        return toRaftPeer(raftNode.endpoint());
        return null;
    }

    public static Client raftPeerProtoToEndpoint(RaftProtos.RaftPeerProto raftPeerProto) {
//        return new EndpointImpl(raftPeerProto.getName(), new InetSocketAddress(raftPeerProto.getHost(),
//                raftPeerProto.getPort()));
        return null;
    }


}

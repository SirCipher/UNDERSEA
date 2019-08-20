package com.type2labs.undersea.prospect.util;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Pair<String, String>> protoTupleToPair(List<RaftProtos.Tuple> input) {
        List<Pair<String, String>> pairList = new ArrayList<>(input.size());

        for (RaftProtos.Tuple t : input) {
            pairList.add(Pair.of(t.getFieldType(), t.getValue()));
        }

        return pairList;
    }


}

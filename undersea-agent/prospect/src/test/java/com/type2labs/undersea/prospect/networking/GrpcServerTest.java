package com.type2labs.undersea.prospect.networking;

import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.impl.RaftPeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GrpcServerTest {

    private List<RaftNode> getNagents(int n) {
        List<RaftNode> nodes = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            RaftNode node = new RaftNodeImpl(
                    null,
                    "test:" + i,
                    null,
                    new InetSocketAddress(0),
                    RaftPeerId.newId()
            );
            nodes.add(node);
        }

        return nodes;
    }

    @Test
    public void initN() {
        List<RaftNode> nodes = getNagents(10);
        RaftNode master = nodes.get(0);

        for (int i = 1; i < nodes.size(); i++) {
            master.state().discoverNode(nodes.get(i));
        }

    }

}
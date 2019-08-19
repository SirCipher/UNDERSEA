package com.type2labs.undersea.prospect.networking;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.impl.RaftPeerId;
import com.type2labs.undersea.prospect.model.RaftNode;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GrpcServerTest {


    private final Executor executor = Executors.newSingleThreadScheduledExecutor();

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
            executor.execute(node);
        }

        return nodes;
    }

    @Test
    public void initN() throws InterruptedException {
        List<RaftNode> nodes = getNagents(2);
        RaftNode master = nodes.get(0);

        for (int i = 1; i < nodes.size(); i++) {
            master.state().discoverNode(nodes.get(i));
        }

        for (Client client : master.state().localNodes().values()) {
            RaftProtos.HelloRequest request = RaftProtos.HelloRequest.newBuilder().setName("idk").build();

            client.sayHello(request, new FutureCallback<RaftProtos.HelloResponse>() {
                @Override
                public void onSuccess(RaftProtos.@Nullable HelloResponse result) {
                    System.out.println(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });

//            System.out.println("Sleeping");

        }

    }

}
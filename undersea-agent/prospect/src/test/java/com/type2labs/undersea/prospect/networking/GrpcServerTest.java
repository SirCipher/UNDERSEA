package com.type2labs.undersea.prospect.networking;

import com.type2labs.undersea.prospect.RaftClusterConfig;
import com.type2labs.undersea.prospect.RaftProtos;
import com.type2labs.undersea.prospect.impl.RaftNodeImpl;
import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.utilities.ThrowableExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class GrpcServerTest {

    private final Executor executor = ThrowableExecutor.newSingleThreadExecutor();

    private List<RaftNode> getNagents(int n) {
        List<RaftNode> nodes = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            RaftNode node = new RaftNodeImpl(
                    new RaftClusterConfig(),
                    "test:" + i,
                    null
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

            client.sayHello(request, new ClientImpl.Callback<RaftProtos.HelloResponse>() {
                @Override
                public void onSuccess(RaftProtos.HelloResponse response) {
                    System.out.println(response);
                }
            });

            Thread.sleep(5000);
        }

    }

}
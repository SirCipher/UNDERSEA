package com.type2labs.undersea.agent.consensus;

import com.type2labs.undersea.agent.consensus.model.RaftNode;
import com.type2labs.undersea.agent.consensus.service.AcquireStatusImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsensusUtil {

    private static Set<? extends Class<?>> services;

    static {
        ScanResult scanResult =
                new ClassGraph().verbose().enableAllInfo().whitelistPackages(AcquireStatusImpl.class.getPackage().getName()).scan();
        services =
                scanResult
                        .getClassesImplementing(BindableService.class.getName())
                        .stream()
                        // Ignore generated services
                        .filter(c -> !c.getName().contains("$"))
                        .map(ClassInfo::loadClass)
                        .collect(Collectors.toSet());
    }

    public static Server buildServer(InetSocketAddress address, RaftNode raftNode) {
        ServerBuilder<?> builder = ServerBuilder.forPort(address.getPort());

        for (Class<?> service : services) {
            BindableService instance = null;
            try {
                instance = (BindableService) service.getDeclaredConstructor(RaftNode.class).newInstance(raftNode);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException e) {
                throw new RuntimeException("Failed to build server");
            }

            builder.addService(instance);
        }

        return builder.build();
    }

}

package com.type2labs.undersea.agent.consensus;

import com.type2labs.undersea.agent.consensus.model.RaftNode;
import com.type2labs.undersea.agent.consensus.service.AcquireStatusImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsensusUtil {

    private static final Logger logger = LogManager.getLogger(ConsensusUtil.class);
    private static final Set<Class<?>> services;

    static {
        ScanResult scanResult = new ClassGraph()
                .verbose()
                .enableAllInfo()
                .disableNestedJarScanning()
                .disableModuleScanning()
                .disableJarScanning()
                .whitelistPackages(AcquireStatusImpl.class.getPackage().getName())
                .scan();

        services = scanResult
                // Superclass of GRPC services
                .getClassesImplementing(BindableService.class.getName())
                .stream()
                // Ignore generated services
                .filter(c -> !c.getName().contains("$"))
                .map(ClassInfo::loadClass)
                .collect(Collectors.toSet());

        if (services.size() == 0) {
            logger.warn("No services found, this doesn't seem right...");
        }

        for (Class<?> clazz : services) {
            logger.info("Registered class: " + clazz.getName());
        }
    }

    public static Server buildServer(InetSocketAddress address, RaftNode raftNode) {
        ServerBuilder<?> builder = ServerBuilder.forPort(address.getPort());

        for (Class<?> service : services) {
            BindableService instance;
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

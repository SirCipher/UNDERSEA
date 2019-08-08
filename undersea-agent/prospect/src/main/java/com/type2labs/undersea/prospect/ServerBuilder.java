package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.model.RaftNode;
import com.type2labs.undersea.prospect.service.AcquireStatusImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.grpc.BindableService;
import io.grpc.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerBuilder {

    private static final Logger logger = LogManager.getLogger(ServerBuilder.class);
    private static final Set<Class<?>> registeredServices;

    private ServerBuilder() {

    }

    static {
        ScanResult scanResult = new ClassGraph()
//                .verbose()
//                .enableAllInfo()
                .disableModuleScanning()
                .whitelistPackages(AcquireStatusImpl.class.getPackage().getName())
                .scan();

        registeredServices = scanResult
                // Superclass of GRPC services
                .getClassesImplementing(BindableService.class.getName())
                .stream()
                // Ignore generated services
                .filter(c -> !c.getName().contains("$"))
                .map(ClassInfo::loadClass)
                .collect(Collectors.toSet());

        if (registeredServices.size() == 0) {
            logger.warn("No services found, this doesn't seem right...");
        }

        for (Class<?> clazz : registeredServices) {
            logger.info("Registered class: " + clazz.getName());
        }
    }

    public static Server build(InetSocketAddress address, RaftNode raftNode) {
        io.grpc.ServerBuilder builder = io.grpc.ServerBuilder.forPort(address.getPort());

        for (Class<?> service : registeredServices) {
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

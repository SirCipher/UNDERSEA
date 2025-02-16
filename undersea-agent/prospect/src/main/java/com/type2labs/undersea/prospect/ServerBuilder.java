/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.prospect;

import com.type2labs.undersea.prospect.model.ConsensusNode;
import com.type2labs.undersea.prospect.service.ConsensusProtocolService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class ServerBuilder {

    private static final Logger logger = LogManager.getLogger(ServerBuilder.class);
    private static final Set<Class<?>> registeredServices;

    static {
        ScanResult scanResult = new ClassGraph()
//                .verbose()
//                .enableAllInfo()
                .disableModuleScanning()
                .whitelistPackages(ConsensusProtocolService.class.getPackage().getName())
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

    private ServerBuilder() {

    }

    public static Server build(InetSocketAddress address, ConsensusNode consensusNode, ExecutorService executorService) {
        NettyServerBuilder builder = NettyServerBuilder.forPort(address.getPort());

        for (Class<?> service : registeredServices) {
            BindableService instance;
            try {
                instance = (BindableService) service.getDeclaredConstructor(ConsensusNode.class).newInstance(consensusNode);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException e) {
                throw new RuntimeException("Failed to build server", e);
            }

            builder.addService(instance);
        }

        return builder.executor(executorService).build();
    }

}

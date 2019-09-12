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

package com.type2labs.undersea.common.logger;

import com.type2labs.undersea.common.agent.AgentFactory;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;
import com.type2labs.undersea.common.logger.model.LogEntry;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class LogServiceImplTest {

    @Test
    public void readNextForClient() {
        LogServiceImpl logService = new LogServiceImpl();
        logService.initialise(new AgentFactory().create());
        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PeerId peerId = PeerId.newId();
            clients.add(new Client() {
                @Override
                public String name() {
                    return peerId.toString();
                }

                @Override
                public PeerId peerId() {
                    return peerId;
                }

                @Override
                public InetSocketAddress socketAddress() {
                    return null;
                }

                @Override
                public void shutdown() {

                }

                @Override
                public boolean isSelf() {
                    return false;
                }
            });
        }

        for (int i = 0; i < 100; i++) {
            logService.add(new LogEntry(PeerId.newId(), i, i, i, logService,true));
        }

        List<LogEntry> logEntries;

        while ((logEntries = logService.readNextForClient(clients.get(0))) != null) {
            for (LogEntry logEntry : logEntries) {
                System.out.println(logEntry);
            }
        }
    }

}
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
            clients.add(new Client() {
                @Override
                public PeerId peerId() {
                    return PeerId.newId();
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
            logService.add(new LogEntry(PeerId.newId(), i, i, i, logService));
        }

        List<LogEntry> logEntries;

        while ((logEntries = logService.readNextForClient(clients.get(0))) != null) {
            for (LogEntry logEntry : logEntries) {
                System.out.println(logEntry);
            }
        }
    }

}
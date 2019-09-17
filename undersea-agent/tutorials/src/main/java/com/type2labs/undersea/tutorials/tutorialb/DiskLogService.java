package com.type2labs.undersea.tutorials.tutorialb;

import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.logger.model.LogService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiskLogService implements LogService {

    private Agent agent;

    @Override
    public void add(LogEntry logEntry) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("log-" + agent.name() + ".txt", true));

            writer.newLine();
            writer.write(logEntry.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LogEntry> read(int startIndex) {
        return new ArrayList<>();
    }

    @Override
    public List<LogEntry> readNextForClient(Client client) {
        return new ArrayList<>();
    }

    @Override
    public void appendEntries(List<LogEntry> logEntries) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

}

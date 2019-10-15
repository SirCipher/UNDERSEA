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
    private BufferedWriter writer;

    @Override
    public void add(LogEntry logEntry) {
        try {
            writer.write(logEntry.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try {
            this.writer = new BufferedWriter(new FileWriter("log-" + agent.name() + ".txt", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

}

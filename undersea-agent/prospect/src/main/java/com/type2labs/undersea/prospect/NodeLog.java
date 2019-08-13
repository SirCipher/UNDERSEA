package com.type2labs.undersea.prospect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class NodeLog {

    private List<LogEntry> logEntries = new ArrayList<>();

    public void appendLog(LogEntry logEntry) {
        logEntries.add(logEntry);
    }

    /**
     * Returns a sublist of log entries between the range
     *
     * @param lower endpoint (include)
     * @param upper endpoint (exclusive)
     * @return the sublist of entries
     */
    public List<LogEntry> getBetween(int lower, int upper) {
        return logEntries.subList(lower, upper);
    }

    public static class LogEntry {

        private int term;
        private int index;
        private Object operation;

        public LogEntry() {

        }

        public LogEntry(int term, int index, Object operation) {
            this.term = term;
            this.index = index;
            this.operation = operation;
        }

        public static LogEntry valueOf(RaftProtos.LogEntryProto proto) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(proto.getData(), LogEntry.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to process log entry: " + proto.toString(), e);
            }
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Object getOperation() {
            return operation;
        }

        public void setOperation(Object operation) {
            this.operation = operation;
        }

        public RaftProtos.LogEntryProto toLogEntryProto() {
            ObjectMapper mapper = new ObjectMapper();
            String result = "";

            try {
                result = mapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to process log entry: " + this.toString(), e);
            }

            RaftProtos.LogEntryProto.Builder builder = RaftProtos.LogEntryProto.newBuilder();
            builder.setData(result);

            return builder.build();
        }

        @Override
        public String toString() {
            return "LogEntry{" +
                    "term=" + term +
                    ", index=" + index +
                    ", operation=" + operation +
                    '}';
        }
    }

}

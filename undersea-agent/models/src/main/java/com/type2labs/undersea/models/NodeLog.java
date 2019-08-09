package com.type2labs.undersea.models;

import java.util.ArrayList;
import java.util.List;

public class NodeLog {

    private List<LogEntry> logEntries = new ArrayList<>();

    public static class LogEntry {

        private int term;
        private int index;
        private Object operation;

        public LogEntry(int term, int index, Object operation) {
            this.term = term;
            this.index = index;
            this.operation = operation;
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
    }

}

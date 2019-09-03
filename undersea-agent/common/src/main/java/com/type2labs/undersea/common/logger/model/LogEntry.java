package com.type2labs.undersea.common.logger.model;

import java.time.ZonedDateTime;

public class LogEntry {

    private int term;
    private Object message;

    public LogEntry(Object message, int term) {
        this.message = message;
        this.term = term;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "term=" + term +
                ", message=" + message +
                '}';
    }

    public int getTerm() {
        return term;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}

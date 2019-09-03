package com.type2labs.undersea.common.logger.model;

import java.time.ZonedDateTime;

public class LogEntry {

    private ZonedDateTime dateTime;
    private int term;
    private Object message;

    public LogEntry(Object message, int term) {
        dateTime = ZonedDateTime.now();

        this.message = message;
        this.term = term;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "dateTime=" + dateTime +
                ", term=" + term +
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

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

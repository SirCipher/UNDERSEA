package com.type2labs.undersea.common.logger;

import java.time.ZonedDateTime;

public class LogEntry {

    private ZonedDateTime dateTime;
    private Object message;

    public LogEntry(Object message) {
        dateTime = ZonedDateTime.now();
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "dateTime=" + dateTime +
                ", message=" + message +
                '}';
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

package ru.parma.mirs.loader.model;

import java.util.Date;

public class LogRecord {
    public Date dateTime;
    public String module;
    public String user;
    public String msg;
    public int msgType;

    public Date getDateTime() {
        return dateTime;
    }

    public String getModule() {
        return module;
    }

    public String getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public int getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return String.format("%1$td-%1$tm-%1$tY %1$tH:%1$tM:%1$tS: %2$s---%3$s[%4$s]\n", dateTime, module, msgType, msg);
    }
}

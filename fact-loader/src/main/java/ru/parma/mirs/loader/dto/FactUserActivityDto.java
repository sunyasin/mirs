package ru.parma.mirs.loader.dto;

public class FactUserActivityDto {
    public Integer id;
    public Integer orgId;
    public Integer calendarId;
    public Integer objectId;
    public Integer subsystemId;
    public Integer loginCount;
    public Float timeOnline;

    public FactUserActivityDto( Integer id, Integer orgId, Integer calendarId, int subsystemId, Float timeOnline, Integer loginCount ) {

        this.id = id;
        this.orgId = orgId;
        this.calendarId = calendarId;
        this.subsystemId = subsystemId;
        this.loginCount = loginCount;
        this.timeOnline = timeOnline;
    }
}

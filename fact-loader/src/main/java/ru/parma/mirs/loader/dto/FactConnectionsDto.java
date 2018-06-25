package ru.parma.mirs.loader.dto;

public class FactConnectionsDto {
    public Integer id;
    public Integer countNewUsers;
    public Integer countUsers;
    public Integer orgId;
    public Integer calendarId;

    public FactConnectionsDto( Integer countNewUsers, Integer countUsers, Integer orgId, Integer calendarId, Integer id ) {

        this.calendarId = calendarId;
        this.countNewUsers = countNewUsers;
        this.countUsers = countUsers;
        this.orgId = orgId;
        this.id = id;
    }
}

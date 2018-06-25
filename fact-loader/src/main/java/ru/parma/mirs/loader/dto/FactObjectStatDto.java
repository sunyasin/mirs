package ru.parma.mirs.loader.dto;

public class FactObjectStatDto {
    public Integer id;
    public Integer orgId;
    public Integer calendarId;
    public Integer objectId;
    public Integer subsystemId;
    public Integer countNew;
    public Integer countChanges;
    public Integer countReports;

    public FactObjectStatDto( Integer countReports, Integer countChanges, Integer countNew,
                              Integer orgId, Integer calendarId, Integer objectId, int mirsCode, Integer id ) {
        this.calendarId = calendarId;
        this.countChanges = countChanges;
        this.countNew = countNew;
        this.countReports = countReports;
        this.objectId = objectId;
        this.subsystemId = mirsCode;
        this.orgId = orgId;
        this.id = id;
    }
}

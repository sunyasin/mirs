package ru.parma.mirs.loader.dto;

public class FactWorkTimeDto {
    public Integer id;
    public Integer calendarId;
    public Integer orgId;
    public Integer departmentId;
    public Integer positionId;
    public String snils;
    public Float workHours;
    public Integer listingCount;
    public Integer listingCountAvg;
    public String employeeGuid;

    public FactWorkTimeDto( Integer factId, Integer calendarId, Integer orgId, Integer departmentId, Integer positionId,
                            String snils, Float workHours, Integer listingCount, String employeeGuid ) {

        id = factId;
        this.calendarId = calendarId;
        this.orgId = orgId;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.snils = snils;
        this.workHours = workHours;
        this.listingCount = listingCount;
        this.listingCountAvg = listingCount;
        this.employeeGuid = employeeGuid;
    }
}

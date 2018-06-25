package ru.parma.mirs.loader.dto;

public class FactSalaryDto {
    public Integer id;
    public final Integer calendarId;
    public final Integer orgId;
    public final Integer departmentId;
    public final Integer positionId;
    public String snils;
    public final float salaryAmount;
    public final Integer accrualId;
    public String employeeGUID;

    public FactSalaryDto( Integer factSalaryId, Integer calendarId, Integer orgId, Integer departmentId, Integer positionId,
                          String snils, float salaryAmount, Integer accrualId, String employeeGUID ) {

        id = factSalaryId;
        this.calendarId = calendarId;
        this.orgId = orgId;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.snils = snils;
        this.salaryAmount = salaryAmount;
        this.accrualId = accrualId;
        this.employeeGUID = employeeGUID;
    }
}

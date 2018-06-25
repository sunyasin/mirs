package ru.parma.mirs.loader.dto;

public class FactPositionSalaryDto {
    public Integer id;
    public final Integer calendarId;
    public final Integer positionId;
    public final float salaryAmount;
    public final Integer accrualId;

    public FactPositionSalaryDto( Integer factSalaryId, Integer calendarId, Integer positionId, float salaryAmount, Integer accrualId ) {

        id = factSalaryId;
        this.calendarId = calendarId;
        this.positionId = positionId;
        this.salaryAmount = salaryAmount;
        this.accrualId = accrualId;
    }
}

package ru.parma.mirs.loader.dto;

import java.util.Date;

public class GenericIndicatorDto {
    public Integer id;
    public String inn;
    public int totalStaffPositions;
    public float totalFullTimePositions;
    public Date hrmClosingDate;
    public Date payrollClosingDate;
    public Date salaryAccountingClosingDate;
    public Date lastHRMOperationDate;
    public Date lastPayrollOperationDate;
    public int dataAreaId;
    public Date dataAreaDateEnd;

    public GenericIndicatorDto( Integer id, String inn, int totalStaffPositions, float totalFullTimePositions,
                                Date hrmClosingDate, Date payrollClosingDate, Date salaryAccountingClosingDate,
                                Date lastHRMOperationDate, Date lastPayrollOperationDate,
                                int dataAreaId, Date dataAreaDateEnd ) {
        this.id = id;
        this.inn = inn;
        this.totalStaffPositions = totalStaffPositions;
        this.totalFullTimePositions = totalFullTimePositions;
        this.hrmClosingDate = hrmClosingDate;
        this.payrollClosingDate = payrollClosingDate;
        this.salaryAccountingClosingDate = salaryAccountingClosingDate;
        this.lastHRMOperationDate = lastHRMOperationDate;
        this.lastPayrollOperationDate = lastPayrollOperationDate;
        this.dataAreaId = dataAreaId;
        this.dataAreaDateEnd = dataAreaDateEnd;
    }
}

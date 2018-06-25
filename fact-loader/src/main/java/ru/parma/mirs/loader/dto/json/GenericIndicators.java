package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericIndicators {
    public String inn;
    public int totalStaffPositions;
    public float totalFullTimePositions;
    public Date hrmClosingDate;
    public Date payrollClosingDate;
    public Date salaryAccountingClosingDate;
    public Date lastHRMOperationDate;
    public Date lastPayrollOperationDate;

    @Override
    public String toString() {
        return "GenericIndicators{" +
                "inn='" + inn + '\'' +
                ", totalStaffPositions=" + totalStaffPositions +
                ", totalFullTimePositions=" + totalFullTimePositions +
                ", hrmClosingDate=" + hrmClosingDate +
                ", payrollClosingDate=" + payrollClosingDate +
                ", salaryAccountingClosingDate=" + salaryAccountingClosingDate +
                ", lastHRMOperationDate=" + lastHRMOperationDate +
                ", lastPayrollOperationDate=" + lastPayrollOperationDate +
                '}';
    }
}

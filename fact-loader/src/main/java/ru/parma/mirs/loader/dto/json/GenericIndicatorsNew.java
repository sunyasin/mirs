package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

public class GenericIndicatorsNew {
    public String inn;
    public int totalStaffPositions;
    public float totalFullTimePositions;
    public Date hrmClosingDate;
    public Date payrollClosingDate;
    public Date salaryAccountingClosingDate;
    public Date lastHRMOperationDate;
    public Date lastPayrollOperationDate;
    public List<Position> positions;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Position {
        public String position;
        public String positionGUID;
        public String department;
        public String departmentGUID;
        public String positionCategory;
        public Integer fullTimePositions;
    }

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

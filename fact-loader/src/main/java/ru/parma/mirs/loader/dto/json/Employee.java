package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    @JsonIgnore
    public DataArea dataArea;

    public String organizationINN;
    public String employeeGUID;
    public String position;
    public int rate;
    public String snils;
    public int salaryAccrualCount;
    public int salaryPaymentCount;
    public float workHours;
    public String department;
    public String departmentGUID;
    public String positionCategory;
    public List<EmployeeSalary> salary;

    public String positionGUID;
    public boolean hasPersonnelNumber;
    public boolean hasBirthDate;
    public boolean hasBirthPlace;
    public boolean hasGender;
    public boolean hasINN;
    public boolean hasSpecialty;
    public boolean hasCareer;
    public boolean hasExperience;
    public boolean hasEducation;
    public boolean hasFamily;
    public boolean hasAddressInfo;
    public boolean hasIdentificationDoc;
    public boolean hasStaffDoc;

    public static class EmployeeSalary {
        public Float amount;
        public String category;
    }
   /*
    @Override
    public String toString() {

        return "Employee{" +
                "dataArea=" + dataArea +
                ", organizationINN='" + organizationINN + '\'' +
                ", employeeGUID='" + employeeGUID + '\'' +
                ", position='" + position + '\'' +
                ", rate=" + rate +
                ", snils='" + snils + '\'' +
                ", salaryAccrualCount=" + salaryAccrualCount +
                ", salaryPaymentCount=" + salaryPaymentCount +
                ", positionGUID='" + positionGUID + '\'' +
                ", hasPersonnelNumber=" + hasPersonnelNumber +
                ", hasBirthDate=" + hasBirthDate +
                ", hasBirthPlace=" + hasBirthPlace +
                ", hasGender=" + hasGender +
                ", hasINN=" + hasINN +
                ", hasSpecialty=" + hasSpecialty +
                ", hasCareer=" + hasCareer +
                ", hasExperience=" + hasExperience +
                ", hasEducation=" + hasEducation +
                ", hasFamily=" + hasFamily +
                ", hasAddressInfo=" + hasAddressInfo +
                ", hasIdentificationDoc=" + hasIdentificationDoc +
                ", hasStaffDoc=" + hasStaffDoc +
                '}';
    }*/
}

package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDates {
    public String organizationINN;
    public String employeeGUID;
    public String snils;
    public String position;
    public String positionGUID;
    public String positionCategory;
    public String department;
    public String departmentGUID;
    public List<Dates> dates;

    public static class Dates {
        public Date date;
        public Float workHours;
        public Integer staffCount;
    }
}

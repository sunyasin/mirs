package ru.parma.mirs.loader.dto.json;

import java.util.List;

public class DataAreaEmp extends DataArea {

    public List<Employee> employees;

    @Override
    public String toString() {

        return "DataAreaEmp{" +
                super.toString() +
                "employees=" + employees +
                '}';
    }
}

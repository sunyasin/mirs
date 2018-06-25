package ru.parma.mirs.loader.dto.json;

import java.util.List;

public class ListOfEmployeeDates {
    public List<DataAreaOrgList> dataAreas;

    public static class DataAreaOrgList extends DataArea {
        public List<EmployeeDates> employees;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        if (dataAreas != null) {
            for (DataAreaOrgList dataArea : dataAreas) {
                sb.append(dataArea.employees == null ? "0" : dataArea.employees.size())
                        .append(" employees in dataArea(")
                        .append(dataArea.id)
                        .append(")")
                        .append(",\n");
            }
        }
        return sb.toString();
    }
}

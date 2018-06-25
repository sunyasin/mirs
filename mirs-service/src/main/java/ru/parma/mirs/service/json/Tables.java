package ru.parma.mirs.service.json;

import java.time.LocalDate;
import java.util.List;

public class Tables {
    private LocalDate beginDate;
    private LocalDate endDate;

    private List<Table> tables;

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}

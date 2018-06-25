package ru.parma.mirs.service.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Panel {

    private String name;
    private Integer idMeasure;
    private LocalDate beginDate;
    private LocalDate endDate;

    private Integer type;//1:проценты; 2:рубли; 3:дни; 4:штуки
    private String value = "";
    private String subValue = "";
    private String changePercentValue = "";
    private String lastPeriodValue = "";

    private List<PairData> tableDataGrbs = new ArrayList<>();
    private List<PairData> tableDataOrganization = new ArrayList<>();

    public static class PairData implements Comparator<PairData> {

        public PairData() {
        }

        public PairData(String name, Float value) {
            this.name = name;
            this.value = value;
        }

        private String name;
        private Float value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getValue() {
            return value;
        }

        public void setValue(Float value) {
            this.value = value;
        }

        @Override
        public int compare(PairData o1, PairData o2) {
            if (o1.getValue() == null && o2.getValue() == null) return 0;
            if (o1.getValue() == null) return 1;
            if (o2.getValue() == null) return -1;
            Float different = o1.getValue() - o2.getValue();
            if (different == 0) return 0;
            else if (different > 0) return -1;
            else return 1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(Integer idMeasure) {
        this.idMeasure = idMeasure;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSubValue() {
        return subValue;
    }

    public void setSubValue(String subValue) {
        this.subValue = subValue;
    }

    public String getChangePercentValue() {
        return changePercentValue;
    }

    public void setChangePercentValue(String changePercentValue) {
        this.changePercentValue = changePercentValue;
    }

    public String getLastPeriodValue() {
        return lastPeriodValue;
    }

    public void setLastPeriodValue(String lastPeriodValue) {
        this.lastPeriodValue = lastPeriodValue;
    }

    public List<PairData> getTableDataGrbs() {
        return tableDataGrbs;
    }

    public void setTableDataGrbs(List<PairData> tableDataGrbs) {
        this.tableDataGrbs = tableDataGrbs;
    }

    public List<PairData> getTableDataOrganization() {
        return tableDataOrganization;
    }

    public void setTableDataOrganization(List<PairData> tableDataOrganization) {
        this.tableDataOrganization = tableDataOrganization;
    }
}

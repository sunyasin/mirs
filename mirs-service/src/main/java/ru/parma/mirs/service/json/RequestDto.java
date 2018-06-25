package ru.parma.mirs.service.json;

import java.time.LocalDate;
import java.util.List;

public class RequestDto {

    private List<Integer> filterGrbs;
    private List<String> filterOrganisation;

    private List<RequestDto.Measure> measures;

    public static class Measure {

        private Integer idMeasure;
        private LocalDate beginDate;
        private LocalDate endDate;

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
    }

    public List<Integer> getFilterGrbs() {
        return filterGrbs;
    }

    public void setFilterGrbs(List<Integer> filterGrbs) {
        this.filterGrbs = filterGrbs;
    }

    public List<String> getFilterOrganisation() {
        return filterOrganisation;
    }

    public void setFilterOrganisation(List<String> filterOrganisation) {
        this.filterOrganisation = filterOrganisation;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}

package ru.parma.mirs.service.json;

import java.util.List;
import java.util.Objects;

public class CubeMeasures {

    private Integer idCube;
    private String nameCube;
    private List<PairData> measures;

    public Integer getIdCube() {
        return idCube;
    }

    public void setIdCube(Integer idCube) {
        this.idCube = idCube;
    }

    public String getNameCube() {
        return nameCube;
    }

    public void setNameCube(String nameCube) {
        this.nameCube = nameCube;
    }

    public List<PairData> getMeasures() {
        return measures;
    }

    public void setMeasures(List<PairData> measures) {
        this.measures = measures;
    }

    public static class PairData {

        private Integer idMeasure;
        private String nameMeasure;

        public Integer getIdMeasure() {
            return idMeasure;
        }

        public void setIdMeasure(Integer idMeasure) {
            this.idMeasure = idMeasure;
        }

        public String getNameMeasure() {
            return nameMeasure;
        }

        public void setNameMeasure(String nameMeasure) {
            this.nameMeasure = nameMeasure;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubeMeasures that = (CubeMeasures) o;
        return Objects.equals(idCube, that.idCube);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idCube);
    }
}

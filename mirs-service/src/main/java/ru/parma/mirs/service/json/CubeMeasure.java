package ru.parma.mirs.service.json;

import java.util.List;

public class CubeMeasure {
    private String name;
    private Integer idTypeMeasure;
    private String formulaText;
    private String shortName;
    private List<String> declines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdTypeMeasure() {
        return idTypeMeasure;
    }

    public void setIdTypeMeasure(Integer idTypeMeasure) {
        this.idTypeMeasure = idTypeMeasure;
    }

    public String getFormulaText() {
        return formulaText;
    }

    public void setFormulaText(String formulaText) {
        this.formulaText = formulaText;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getDeclines() {
        return declines;
    }

    public void setDeclines(List<String> declines) {
        this.declines = declines;
    }
}

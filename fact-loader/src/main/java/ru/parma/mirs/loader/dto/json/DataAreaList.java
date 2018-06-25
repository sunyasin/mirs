package ru.parma.mirs.loader.dto.json;

import java.util.List;

public class DataAreaList {
    public List<DataAreaEmp> dataAreas;

    @Override
    public String toString() {

        return "DataAreaList{" +
                "dataAreas=" + dataAreas +
                '}';
    }
}

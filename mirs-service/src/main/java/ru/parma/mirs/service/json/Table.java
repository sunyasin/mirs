package ru.parma.mirs.service.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {

    private String name;
    private Integer idMeasure;

    private Integer type;//1:проценты; 2:рубли; 3:дни; 4:штуки

    private String nameValue;
    private String nameSubValue1;
    private String nameSubValue2;
    private String nameSubValue3;

    private List<Data> parents;

    public static class Data implements Comparator<Data> {
        private String name;
        private Float value;
        private Float subValue1;
        private Float subValue2;
        private LocalDate subValue3;

        private List<Data> children = new ArrayList<>();

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

        public Float getSubValue1() {
            return subValue1;
        }

        public void setSubValue1(Float subValue1) {
            this.subValue1 = subValue1;
        }

        public Float getSubValue2() {
            return subValue2;
        }

        public void setSubValue2(Float subValue2) {
            this.subValue2 = subValue2;
        }

        public LocalDate getSubValue3() {
            return subValue3;
        }

        public void setSubValue3(LocalDate subValue3) {
            this.subValue3 = subValue3;
        }

        public List<Data> getChildren() {
            return children;
        }

        public void setChildren(List<Data> children) {
            this.children = children;
        }

        @Override
        public int compare(Data o1, Data o2) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getNameSubValue1() {
        return nameSubValue1;
    }

    public void setNameSubValue1(String nameSubValue1) {
        this.nameSubValue1 = nameSubValue1;
    }

    public String getNameSubValue2() {
        return nameSubValue2;
    }

    public void setNameSubValue2(String nameSubValue2) {
        this.nameSubValue2 = nameSubValue2;
    }

    public String getNameSubValue3() {
        return nameSubValue3;
    }

    public void setNameSubValue3(String nameSubValue3) {
        this.nameSubValue3 = nameSubValue3;
    }

    public List<Data> getParents() {
        return parents;
    }

    public void setParents(List<Data> parents) {
        this.parents = parents;
    }
}

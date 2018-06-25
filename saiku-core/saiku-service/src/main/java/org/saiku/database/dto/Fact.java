package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fact {
    private Integer id;
    private String name;
    private String updateDate;
    private String value;
    private Integer unitId;
    private String unitName;
    private String unitNameShort;

    public Fact(String name) {
        this.name = name;
    }
}

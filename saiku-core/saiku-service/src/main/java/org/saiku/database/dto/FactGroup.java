package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FactGroup {
    private String name;
    private List<Fact> facts;
    private Integer minYear;
    private List<FactGroup> factGroups;

    public FactGroup(String name) {
        this.name = name;
    }
}

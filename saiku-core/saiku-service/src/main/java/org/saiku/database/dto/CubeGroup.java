package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CubeGroup {
    private String name;
    private List<FactGroup> factGroups;
}

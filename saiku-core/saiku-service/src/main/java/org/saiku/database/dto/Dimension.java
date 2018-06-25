package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimension {
    private Integer dimId;
    private String name;
    private Boolean isColumn;
}

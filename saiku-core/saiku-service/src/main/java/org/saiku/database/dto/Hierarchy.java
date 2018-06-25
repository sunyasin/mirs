package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hierarchy {
    private Integer id;
    private String name;
    private String keyColumn;
    private String nameColumn;
}

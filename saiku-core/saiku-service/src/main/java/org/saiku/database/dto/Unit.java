package org.saiku.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    private Integer unitId;
    private Integer unitGroupId;
    private String unitName;
    private String unitNameShort;
    private String unitKey;
}

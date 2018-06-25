package org.saiku.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CubesDimensions {
    List<Dimension> dimensionList;
    List<DateLevel> dateLevelList;
    List<Measure> measures;
}

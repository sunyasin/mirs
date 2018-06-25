package org.saiku.database.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDimensionValue {
    private Integer reportDimId;
    private String valueText;
    private Integer isInclude;
}

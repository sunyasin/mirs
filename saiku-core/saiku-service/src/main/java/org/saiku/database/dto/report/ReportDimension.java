package org.saiku.database.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportDimension {
    private Integer reportId;
    private Integer reportDimensionId;
    private Integer dimensionId;
    private Integer placeId;
    private Integer placeOrder;
    private Integer levelId;
    private Integer dtLevelId;
    private List<ReportDimensionValue> dimensionValues;
}

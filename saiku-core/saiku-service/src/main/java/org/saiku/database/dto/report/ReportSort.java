package org.saiku.database.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportSort {
    private String sortType;
    private String sortOrder;
    private String sortMeasure;
    private String totalKey;
    private String intervalTypeId;
    private Integer intervalCount;
    private String intervalParam;
}

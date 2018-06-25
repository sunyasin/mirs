package org.saiku.database.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMeasure {
    /**
     * идентификатор факта куба в отчете
     */
    private Integer reportMeasureId;
    /**
     * идентификатор факта куба
     */
    private Integer measureId;
    /**
     * наименование факта куба
     */
    private String measureName;
    /**
     * единицы измерения
     */
    private String unitName;
    /**
     * единицы измерения короткое название
     */
    private String unitNameShort;
    /**последняя дата, на которую есть данные*/
    private String updateDate;
    /**максимальное значение на последнюю дату*/
    private String value;
    /**
     * Итоги по строкам
     */
    private String totalRow;
    /**
     * Итоги по столбцам
     */
    private String totalColumn;

    private Integer placeOrder;
}


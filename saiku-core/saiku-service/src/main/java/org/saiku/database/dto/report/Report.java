package org.saiku.database.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Report {
    /** идентификатор отчета */
    private Integer reportId;
    /** наименование отчета */
    private String rName;
    /** дата-время создания отчета */
    private String dtCreate;
    /** дата-время последнего обновления отчета */
    private String dtChange;
    /** идентификатор пользователя, кот.создал отчет */
    private Integer userId;
    /** Фамилия И.О. пользователя, кот.создал отчет */
    private String userName;
    /** флаг является ли пользователь создателем отчёта */
    private Boolean isOwner = false;
    /** идентификатор место расположения показателей в отчете */
    private Integer placeMeasureId;
    /** дата-время создания отчета для обновлённого отчёта */
    private Timestamp dtCreateTimeStamp;
    /** идентификатор вида отображения */
    private Integer viewId;
    /** ключ вида отображения */
    private String viewSelector;

    /** список показателей отчёта */
    private List<ReportMeasure> reportMeasures;
    /** набор измерений в отчёте */
    private List<ReportDimension> reportDimensions;
    /** сортировка и итоги по строкам и колонкам */
    private List<ReportSort> reportSorts;
}

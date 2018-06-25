package org.saiku.database.dao;

import mondrian.util.Format;
import org.saiku.database.dto.report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportDAO extends JdbcDaoSupport {

    private static HashMap<Integer, String> unitMap = new HashMap<>();

    @Autowired
    private OlapUserDAO olapUserDAO;

    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public void init() {
        parameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
        unitMap = getJdbcTemplate().query("SELECT unit_id,  concat(unit_name,'|' , unit_name_short) AS name FROM cube_measure_unit", new ResultSetExtractor<HashMap<Integer, String>>() {
            @Override
            public HashMap<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<Integer, String> res = new HashMap<>();
                while (rs.next()) {
                    res.put(rs.getInt("unit_id"), rs.getString("name"));
                }
                return res;
            }
        });
    }

    public List<Report> getAllReports(String userName) {
        return getReportList(userName, null);
    }

    public Report getReportById(String userName, Integer reportId) {
        Report report = getReportList(userName, reportId).get(0);
        report.setReportDimensions(getReportDimensions(reportId));
        report.setReportSorts(getReportSorts(reportId));
        return report;
    }

    private List<Report> getReportList(String userName, Integer reportId) {
        final Integer userId = olapUserDAO.getUserIdByUsername(userName);
        assert userId != null;
        String sql = "SELECT * FROM reports";
        MapSqlParameterSource source = new MapSqlParameterSource();
        if (reportId != null) {
            source.addValue("report_id", reportId);
            sql += " WHERE report_id = :report_id";
        } else if (olapUserDAO.checkFoms(userId)) {
            source.addValue("list_id", new HashSet<>(olapUserDAO.getUserIdsByRoleId(6)));
            sql += " WHERE user_id IN (:list_id)";
        }

        return parameterJdbcTemplate.query(sql, source, new RowMapper<Report>() {
            @Override
            public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
                Report report = new Report();
                report.setReportId(rs.getInt("report_id"));
                report.setRName(rs.getString("r_name"));
                report.setDtChange(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(rs.getTimestamp("dt_change")));
                report.setDtCreate(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(rs.getTimestamp("dt_create")));
                Integer reportUserId = rs.getInt("user_id");
                report.setUserId(reportUserId);
                if (userId.equals(reportUserId)) {
                    report.setIsOwner(true);
                }
                report.setUserName(olapUserDAO.getFioByUserId(reportUserId));
                report.setReportMeasures(getReportMeasures(report.getReportId()));
                return report;
            }
        });
    }

    private List<ReportMeasure> getReportMeasures(Integer reportId) {
        return getJdbcTemplate().query(
                "SELECT * FROM report_measure rm \n" +
                        "JOIN cube_measure cm ON cm.measure_id = rm.measure_id \n" +
                        "WHERE rm.report_id = ?", new RowMapper<ReportMeasure>() {
                    @Override
                    public ReportMeasure mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final ReportMeasure reportMeasure = new ReportMeasure();
                        reportMeasure.setMeasureId(rs.getInt("measure_id"));
                        reportMeasure.setMeasureName(rs.getString("m_name"));
                        reportMeasure.setReportMeasureId(rs.getInt("report_measure_id"));
                        Date updateDate = rs.getDate("last_date");
                        String strDate = (updateDate != null ? new SimpleDateFormat("dd.MM.yyyy").format(updateDate) : "");
                        reportMeasure.setUpdateDate(strDate);
                        Integer depth = rs.getInt("bit_depth");
                        Double value = rs.getDouble("last_value");
                        String formatExp = rs.getString("format_value");
                        String strValue = String.format(Locale.forLanguageTag("ru-RU"), "%."+depth+"f", value);;
                        if (formatExp!=null) {
                            strValue = new Format(formatExp,Locale.forLanguageTag("ru-RU")).format(value);
                        }
                        reportMeasure.setValue(strValue);

                        if (rs.getInt("unit_id")!=0) {
                            String name = unitMap.get(rs.getInt("unit_id"));
                            reportMeasure.setUnitName(name.split("\\|")[0]);
                            reportMeasure.setUnitNameShort(name.split("\\|")[1]);
                        }

                        return reportMeasure;
                    }
                }, reportId);
    }

    private List<ReportDimension> getReportDimensions(Integer reportId) {
        return getJdbcTemplate().query("SELECT * FROM report_dimension WHERE report_id = ?", new RowMapper<ReportDimension>() {
            @Override
            public ReportDimension mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReportDimension reportDimension = new ReportDimension();
                reportDimension.setDimensionId(rs.getInt("dimension_id"));
                reportDimension.setDtLevelId(rs.getInt("dt_level_id"));
                reportDimension.setLevelId(rs.getInt("level_id"));
                reportDimension.setReportDimensionId(rs.getInt("report_dim_id"));
                reportDimension.setPlaceId(rs.getInt("place_id"));
                reportDimension.setPlaceOrder(rs.getInt("place_order"));
                reportDimension.setDimensionValues(getReportDimensionValues(reportDimension.getReportDimensionId()));
                return reportDimension;
            }
        }, reportId);
    }

    private List<ReportDimensionValue> getReportDimensionValues(Integer reportDimId) {
        return getJdbcTemplate().query("SELECT * FROM report_dimension_value WHERE report_dim_id = ?", new RowMapper<ReportDimensionValue>() {
            @Override
            public ReportDimensionValue mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReportDimensionValue reportDimensionValue = new ReportDimensionValue();
                reportDimensionValue.setValueText(rs.getString("value_text"));
                reportDimensionValue.setIsInclude(rs.getBoolean("is_include") ? 1 : 0);
                return reportDimensionValue;
            }
        }, reportDimId);
    }

    private List<ReportSort> getReportSorts(Integer reportId) {
        return getJdbcTemplate().query("SELECT * FROM report_sort WHERE report_id = ?", new RowMapper<ReportSort>() {
            @Override
            public ReportSort mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReportSort reportSort = new ReportSort();
                reportSort.setIntervalCount(rs.getInt("interval_count"));
                reportSort.setIntervalParam(rs.getString("interval_param"));
                reportSort.setIntervalTypeId(rs.getString("interval_type_id"));
                reportSort.setSortMeasure(rs.getString("sort_measure"));
                reportSort.setSortOrder(rs.getString("sort_order"));
                reportSort.setSortType(rs.getString("sort_type"));
                reportSort.setTotalKey(rs.getString("total_key"));
                return reportSort;
            }
        }, reportId);
    }

    @Transactional
    public Report saveReport(Report report) {
        Integer reportId = report.getReportId();
        if (reportId!=null) {
            report.setDtCreateTimeStamp(getJdbcTemplate().queryForObject("SELECT dt_create FROM reports WHERE report_id = ?",
                    Timestamp.class, reportId));
            deleteReport(reportId);
        }
        Integer viewId = 1;
        if (report.getViewSelector()!=null) {
            viewId = getJdbcTemplate().queryForObject("SELECT view_id FROM report_view WHERE view_key = ?", Integer.class, report.getViewSelector());
        }
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("view_id", viewId)
                .addValue("r_name", report.getRName())
                .addValue("dt_create", report.getDtCreateTimeStamp())
                .addValue("user_id", report.getUserId())
                .addValue("user_name", olapUserDAO.getFioByUserId(report.getUserId()))
                .addValue("place_measure_id", report.getPlaceMeasureId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update("INSERT INTO \n" +
                "  REPORTS\n" +
                "(\n" +
                "  view_id,\n" +
                "  r_name,\n" +
                (report.getDtCreateTimeStamp()!=null?"  dt_create,\n" : "") +
                "  user_id,\n" +
                "  place_measure_id,\n" +
                "  user_name\n" +
                ") \n" +
                "VALUES (\n" +
                "  :view_id,\n" +
                "  :r_name,\n" +
                (report.getDtCreateTimeStamp()!=null?"  :dt_create,\n" : "") +
                "  :user_id,\n" +
                "  :place_measure_id,\n" +
                "  :user_name\n" +
                ")", source, keyHolder);
        report.setReportId((Integer) keyHolder.getKeys().get("report_id"));
        Timestamp dtChange = getJdbcTemplate().queryForObject("SELECT dt_change FROM reports WHERE report_id = ?",
                Timestamp.class, report.getReportId());
        report.setDtChange(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(dtChange));
        for (ReportMeasure reportMeasure : report.getReportMeasures()) {
            saveReportMeasure(reportMeasure, report.getReportId());
        }
        for (ReportDimension reportDimension : report.getReportDimensions()) {
            saveReportDimension(reportDimension, report.getReportId());
        }
        for (ReportSort reportSort : report.getReportSorts()) {
            saveReportSort(reportSort, report.getReportId());
        }
        return report;
    }

    private void saveReportMeasure(ReportMeasure reportMeasure, Integer reportId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("report_id", reportId)
                .addValue("measure_id", reportMeasure.getMeasureId())
                .addValue("place_order", reportMeasure.getPlaceOrder())
                .addValue("total_row", reportMeasure.getTotalRow())
                .addValue("total_column", reportMeasure.getTotalColumn());
        parameterJdbcTemplate.update("INSERT INTO \n" +
                "  REPORT_MEASURE\n" +
                "(\n" +
                "  report_id,\n" +
                "  measure_id,\n" +
                "  place_order,\n" +
                "  total_row,\n" +
                "  total_column\n" +
                ") \n" +
                "VALUES (\n" +
                "  :report_id,\n" +
                "  :measure_id,\n" +
                "  :place_order,\n" +
                "  :total_row,\n" +
                "  :total_column\n" +
                ");", source);
    }

    private void saveReportDimension(ReportDimension reportDimension, Integer reportId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("report_id", reportId)
                .addValue("dimension_id", reportDimension.getDimensionId())
                .addValue("place_id", reportDimension.getPlaceId())
                .addValue("place_order", reportDimension.getPlaceOrder())
                .addValue("level_id", reportDimension.getLevelId())
                .addValue("dt_level_id", reportDimension.getDtLevelId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update("INSERT INTO \n" +
                "  REPORT_DIMENSION\n" +
                "(\n" +
                "  report_id,\n" +
                "  dimension_id,\n" +
                "  place_id,\n" +
                "  place_order,\n" +
                "  level_id,\n" +
                "  dt_level_id\n" +
                ") \n" +
                "VALUES (\n" +
                "  :report_id,\n" +
                "  :dimension_id,\n" +
                "  :place_id,\n" +
                "  :place_order,\n" +
                "  :level_id,\n" +
                "  :dt_level_id\n" +
                ");", source, keyHolder);
        reportDimension.setReportDimensionId((Integer)keyHolder.getKeys().get("report_dim_id"));
        for (ReportDimensionValue reportDimensionValue : reportDimension.getDimensionValues()) {
            saveReportDimensionValue(reportDimensionValue, reportDimension.getReportDimensionId());
        }
    }

    private void saveReportSort(ReportSort reportSort, Integer reportId) {
        String totalKey = "nil";
        if (reportSort.getTotalKey() != null && reportSort.getTotalKey().length()>0 && !reportSort.getTotalKey().equals("not")) {
            totalKey = reportSort.getTotalKey();
        }
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("report_id", reportId)
                .addValue("sort_type", reportSort.getSortType())
                .addValue("sort_order", reportSort.getSortOrder())
                .addValue("sort_measure", reportSort.getSortMeasure())
                .addValue("total_key", totalKey)
                .addValue("interval_type_id", reportSort.getIntervalTypeId())
                .addValue("interval_count", reportSort.getIntervalCount())
                .addValue("interval_param", reportSort.getIntervalParam());
        parameterJdbcTemplate.update("INSERT INTO \n" +
                "  REPORT_SORT\n" +
                "(\n" +
                "  report_id,\n" +
                "  sort_type,\n" +
                "  sort_order,\n" +
                "  sort_measure,\n" +
                "  total_key," +
                "  interval_type_id,\n" +
                "  interval_count,\n" +
                "  interval_param\n" +
                ") \n" +
                "VALUES (\n" +
                "  :report_id,\n" +
                "  :sort_type,\n" +
                "  :sort_order,\n" +
                "  :sort_measure,\n" +
                "  :total_key,\n" +
                "  :interval_type_id,\n" +
                "  :interval_count,\n" +
                "  :interval_param\n" +
                ");", source);
    }

    private void saveReportDimensionValue(ReportDimensionValue reportDimensionValue, Integer reportDimensionId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("report_dim_id", reportDimensionId)
                .addValue("value_text", reportDimensionValue.getValueText())
                .addValue("is_include", reportDimensionValue.getIsInclude() == null ? false : reportDimensionValue.getIsInclude() == 1);
        parameterJdbcTemplate.update("INSERT INTO \n" +
                "  REPORT_DIMENSION_VALUE\n" +
                "(\n" +
                "  report_dim_id,\n" +
                "  value_text,\n" +
                "  is_include" +
                ") \n" +
                "VALUES (\n" +
                "  :report_dim_id,\n" +
                "  :value_text,\n" +
                "  :is_include" +
                ");", source);
    }

    public void deleteReport(Integer reportId) {
        getJdbcTemplate().update("DELETE FROM report_sort WHERE report_id = ?", reportId);
        getJdbcTemplate().update("DELETE FROM report_dimension WHERE report_id = ?", reportId);
        getJdbcTemplate().update("DELETE FROM report_measure WHERE report_id = ?", reportId);
        getJdbcTemplate().update("DELETE FROM reports WHERE report_id = ?", reportId);
    }
}

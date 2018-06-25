package ru.parma.mirs.loader.service;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import ru.parma.mirs.loader.dto.*;

import javax.sql.DataSource;
import java.util.*;

/**
 * в процессе рефакторинга сюда будет перенесена вся работа с БД из слоя бизнес логики
 */
@Repository
public class FactDaoDB extends JdbcDaoSupport {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FactDaoDB.class);

    private LoaderLogger logger;


    @Autowired
    public FactDaoDB( DataSource dataSource, LoaderLogger logger ) {
        super();
        this.logger = logger;
        setDataSource(dataSource);
    }


    public Integer getFactConnectionId( Integer orgId, Integer calendarId ) {
        List<Integer> ids = getJdbcTemplate().queryForList("SELECT id FROM fact_connections WHERE org_id=? AND calendar_id=?", Integer.class, orgId, calendarId);
        if (ids == null || ids.isEmpty())
            return null;
        return ids.get(0);
    }

    public void updateFactConnections( List<FactConnectionsDto> list ) {

        String factConnectionUpdateSql = "UPDATE fact_connections SET new_users_count=?, users_count_total=?, last_updated=now() WHERE id=?";

        String factConnectionInsertSql = "INSERT INTO fact_connections " +
                "(new_users_count, users_count_total, org_id, calendar_id) VALUES " +
                "(?,                 ?,               ?,         ?) ";

        List<Object[]> updateBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactConnectionsDto i : list) {
            if (i.id == null) {
                insertBatch.add(new Object[]{i.countNewUsers, i.countUsers, i.orgId, i.calendarId});
            } else {
                updateBatch.add(new Object[]{i.countNewUsers, i.countUsers, i.id});
            }
        }
        if (insertBatch.size() > 0)
            getJdbcTemplate().batchUpdate(factConnectionInsertSql, insertBatch);
        if (updateBatch.size() > 0)
            getJdbcTemplate().batchUpdate(factConnectionUpdateSql, updateBatch);
    }

    public Integer findFactObjectStat( Integer orgId, Integer calendarId, Integer objectId, int subsytemId ) {
        List<Integer> ids = getJdbcTemplate().queryForList(
                "SELECT id FROM fact_object_stat WHERE org_id=? AND calendar_id=? AND object_id=? AND subsystem_id=?", Integer.class,
                orgId, calendarId, objectId, subsytemId);
        if (ids == null || ids.isEmpty())
            return null;
        return ids.get(0);
    }

    public Integer getOrNewObjectType( String objectTypeLabel, HashMap<String, Integer> objectTypes ) throws RuntimeException {

        Integer id = getOrNewCommon("spr_object_type", objectTypeLabel, objectTypes);
        return id;
    }

    public Integer getOrNewObjectId( String value, HashMap<String, Integer> cache, int typeId ) throws RuntimeException {

        String table = "spr_object";
        Integer id = cache.get(value);
        if (id == null) {
            String sql = "select id from " + table + " where name='" + value + "' AND type_id=" + typeId;
            List<Integer> ids = getJdbcTemplate().queryForList(sql, Integer.class);
            if (ids == null || ids.isEmpty()) {
                getJdbcTemplate().update("INSERT INTO " + table + " (name, type_id) VALUES (?, ?)", value, typeId);
                ids = getJdbcTemplate().queryForList(sql, Integer.class);
                if (ids == null || ids.isEmpty()) {
                    throw new RuntimeException("failed to get id for new object with type=" + typeId + ", name=" + value);
                }
            }
            id = ids.get(0);
            if (id != null)
                cache.put(value, id);
        }
        return id;
    }

    private Integer getOrNewCommon( String table, String value, HashMap<String, Integer> cache ) throws RuntimeException {

        Integer id = cache.get(value);
        if (id == null) {
            id = selectIdByNameColumn(table, value, false);
            if (id == null) {
                getJdbcTemplate().update("INSERT INTO " + table + " (name) VALUES (?)", value);
                id = selectIdByNameColumn(table, value, true);
            }
            if (id != null)
                cache.put(value, id);
        }
        return id;
    }

    public Integer selectIdByNameColumn( String table, String name, boolean throwIfAbsent ) {

        String sql = "select id from " + table + " where name='" + name + "'";
        List<Integer> ids = getJdbcTemplate().queryForList(sql, Integer.class);
        if (ids == null || ids.isEmpty()) {
            if (throwIfAbsent)
                throw new RuntimeException("failed to resolve id by name " + name + ", SQL:" + sql);
            return null;
        }
        return ids.get(0);
    }

    public HashMap<String, Integer> loadDictionary( String table ) {

        HashMap<String, Integer> map = new HashMap<>();
        List<Map<String, Object>> list = getJdbcTemplate().queryForList("select name, id from " + table);
        list.forEach(m -> map.put((String) m.get("name"), (Integer) m.get("id")));
        return map;
    }

    public void updateFactObjectStat( List<FactObjectStatDto> list ) {

        String objectStatInsertSql = "INSERT INTO fact_object_stat " +
                "(reports_generated, objects_updated, objects_created, subsystem_id, object_id, org_id, calendar_id) VALUES" +
                "(?,                 ?,               ?,               ?,            ?,          ?,       ? )";
        String objectStatUpdateSql = "UPDATE fact_object_stat SET " +
                " reports_generated=?, objects_updated=?, objects_created=?, last_updated=now() " +
                " WHERE id=?";

        List<Object[]> updateBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactObjectStatDto i : list) {
            if (i.id == null) {
                insertBatch.add(new Object[]{i.countReports, i.countChanges, i.countNew, i.subsystemId, i.objectId, i.orgId, i.calendarId});
            } else {
                updateBatch.add(new Object[]{i.countReports, i.countChanges, i.countNew, i.id});
            }
        }
        if (insertBatch.size() > 0)
            getJdbcTemplate().batchUpdate(objectStatInsertSql, insertBatch);
        if (updateBatch.size() > 0)
            getJdbcTemplate().batchUpdate(objectStatUpdateSql, updateBatch);
    }


    public Integer findUserActivityFact( Integer orgId, Integer calendarId, int subsytemId ) {

        List<Integer> ids = getJdbcTemplate().queryForList(
                "SELECT id FROM fact_user_activity WHERE org_id=? AND calendar_id=? AND subsystem_id=?", Integer.class,
                orgId, calendarId, subsytemId);
        if (ids == null || ids.isEmpty())
            return null;
        return ids.get(0);
    }

    public void updateUserActivity( List<FactUserActivityDto> list ) {

        String insertSql = "INSERT INTO fact_user_activity " +
                "(online_time, login_count, subsystem_id, org_id, calendar_id) VALUES " +
                "(?,           ?,           ?,            ?,       ? )";
        String updateSql = "UPDATE fact_user_activity SET " +
                " online_time=?, login_count=?, subsystem_id=?, org_id=?, calendar_id=?, last_updated=now() " +
                " WHERE id=?";
        List<Object[]> updateBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactUserActivityDto i : list) {
            if (i.id == null) {
                insertBatch.add(new Object[]{i.timeOnline, i.loginCount, i.subsystemId, i.orgId, i.calendarId});
            } else {
                updateBatch.add(new Object[]{i.timeOnline, i.loginCount, i.subsystemId, i.orgId, i.calendarId, i.id});
            }
        }
        if (insertBatch.size() > 0)
            getJdbcTemplate().batchUpdate(insertSql, insertBatch);
        if (updateBatch.size() > 0)
            getJdbcTemplate().batchUpdate(updateSql, updateBatch);
    }

    public void updateFactConnectionsTotals() {
        log.debug("updateFactConnectionsTotals...");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(new_users_count) FROM fact_connections) WHERE m_key='new_users_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(users_count_total) FROM fact_connections) WHERE m_key='users_count_total'");
        log.debug("updateFactConnectionsTotals finished...");
    }

    public void updateFactObjectStatTotals() {
        log.debug("updateFactObjectStatTotals...");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(reports_generated) FROM fact_object_stat) WHERE m_key='reports_generated'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(objects_updated) FROM fact_object_stat) WHERE m_key='objects_updated'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(objects_created) FROM fact_object_stat) WHERE m_key='objects_created'");
        log.debug("updateFactObjectStatTotals finished...");
    }

    public void updateUserActivityTotals() {
        log.debug("updateUserActivityTotals...");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT max(days_from_last_activity) FROM fact_user_activity) WHERE m_key='days_from_last_activity'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(online_time) FROM fact_user_activity) WHERE m_key='online_time'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(login_count) FROM fact_user_activity) WHERE m_key='login_count'");
        log.debug("updateUserActivityTotals finished...");
    }

    public Integer selectPreviousFact( String whereText, String table ) {

        List<Integer> ids = getJdbcTemplate().queryForList(
                "select id from " + table + " where " + whereText,
                Integer.class);
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return ids.get(0);
    }

    public Integer findGenericFact( String inn, Date end ) {

        String whereString = String.format("inn='%1$s' AND period_end=to_date('%2$tY%2$tm%2$td', 'YYYYMMDD')", inn, end);
        Integer id = selectPreviousFact(whereString, "facts_generic_indicators");
        return id;
    }

    public void updateGenericIndicators( List<GenericIndicatorDto> list ) {

        String insertSql = "INSERT INTO facts_generic_indicators (" +
                "inn," +
                "total_staff_positions, " +
                "total_full_time_positions, " +
                "hrm_closing_date," +
                "payroll_closing_date," +
                "salary_accounting_closing_date," +
                "last_hrm_operation_date," +
                "last_payroll_operation_date," +
                "data_area," +
                "period_end" +
                ") VALUES (?,?,?,?,?, ?,?,?,?,?)";
        String updateSql = "UPDATE facts_generic_indicators SET " +
                "inn=?," +
                "total_staff_positions=?, " +
                "total_full_time_positions=?, " +
                "hrm_closing_date=?," +
                "payroll_closing_date=?," +
                "salary_accounting_closing_date=?," +
                "last_hrm_operation_date=?," +
                "last_payroll_operation_date=?," +
                "data_area=?," +
                "period_end=?" +
                " WHERE id=?";
        List<Object[]> updateBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (GenericIndicatorDto i : list) {
            if (i.id == null) {
                insertBatch.add(new Object[]{i.inn, i.totalStaffPositions, i.totalFullTimePositions, i.hrmClosingDate, i.payrollClosingDate, i.salaryAccountingClosingDate,
                        i.lastHRMOperationDate, i.lastPayrollOperationDate, i.dataAreaId, i.dataAreaDateEnd});
            } else {
                updateBatch.add(new Object[]{i.inn, i.totalStaffPositions, i.totalFullTimePositions, i.hrmClosingDate, i.payrollClosingDate, i.salaryAccountingClosingDate,
                        i.lastHRMOperationDate, i.lastPayrollOperationDate, i.dataAreaId, i.dataAreaDateEnd, i.id});
            }
        }
        if (insertBatch.size() > 0)
            getJdbcTemplate().batchUpdate(insertSql, insertBatch);
        if (updateBatch.size() > 0)
            getJdbcTemplate().batchUpdate(updateSql, updateBatch);
    }

    public Integer findFactSalary( Integer calendarId, String employeeGuid ) {

        String whereString = String.format("calendar_id=%s AND employee_guid='%s'", calendarId, employeeGuid);
        Integer id = selectPreviousFact(whereString, "fact_salary");
        return id;
    }

    public void batchProcessAndClear( List<FactSalaryDto> list ) {

        String insertSql = "INSERT INTO fact_salary " +
                "(calendar_id, org_id, depart_id, position_id, snils, payed_salary, accrual_id, employee_guid) VALUES " +
                "(?,           ?,      ? ,        ?,           ?,     ?,            ?          ,?)";
        String deleteSql = "DELETE FROM fact_salary WHERE calendar_id=? AND employee_guid=?";
//        String updateSql = "UPDATE fact_salary SET " +
//                "calendar_id=?, org_id=?, depart_id=?, position_id=?, snils=?, payed_salary=?, accrual_id=?, employee_guid=? WHERE id=?";
        List<Object[]> deleteBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactSalaryDto i : list) {
            deleteBatch.add(new Object[]{i.calendarId, i.employeeGUID});
            insertBatch.add(new Object[]{i.calendarId, i.orgId, i.departmentId, i.positionId, i.snils, i.salaryAmount, i.accrualId, i.employeeGUID});
        }
        try {
            if (deleteBatch.size() > 0)
                getJdbcTemplate().batchUpdate(deleteSql, deleteBatch);
            if (insertBatch.size() > 0)
                getJdbcTemplate().batchUpdate(insertSql, insertBatch);
        }
        finally {
            list.clear();
        }

    }

    public void batchFactPositionSalaryAndClear( List<FactPositionSalaryDto> list ) {

        String insertSql = "INSERT INTO fact_position_salary " +
                "(calendar_id, position_id, salary, accrual_id) VALUES " +
                "(?,           ?,           ?,      ? )";
        String updateSql = "UPDATE fact_position_salary SET " +
                "calendar_id=?, position_id=?, salary=?, accrual_id=? WHERE id=?";
        List<Object[]> updateBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactPositionSalaryDto i : list) {
            if (i.id == null) {
                insertBatch.add(new Object[]{i.calendarId, i.positionId, i.salaryAmount, i.accrualId});
            } else {
                updateBatch.add(new Object[]{i.calendarId, i.positionId, i.salaryAmount, i.accrualId, i.id});
            }
        }
        try {
            if (insertBatch.size() > 0)
                getJdbcTemplate().batchUpdate(insertSql, insertBatch);
            if (updateBatch.size() > 0)
                getJdbcTemplate().batchUpdate(updateSql, updateBatch);
        }
        finally {
            list.clear();
        }
    }

    //todo cachable
    public Integer findDeparment( String departmentGUID, String department ) {

        String whereString = String.format("guid='%s'", departmentGUID);
        Integer id = selectPreviousFact(whereString, "spr_department");
        if (id == null) {
            getJdbcTemplate().update("INSERT INTO spr_department (name, guid) VALUES (?,?)", department, departmentGUID);
            id = selectPreviousFact(whereString, "spr_department");
            if (id == null)
                throw new RuntimeException("failed to insert new department");
        }
        return id;
    }

    //todo cachable
    public Integer findPosition( String positionGUID, String position, String positionCategory ) {

        String whereString = String.format("guid='%s'", positionGUID);
        Integer id = selectPreviousFact(whereString, "spr_position");
        if (id == null) {
            //Integer positionCatId = findPositionCategory (positionCategory);
            getJdbcTemplate().update("INSERT INTO spr_position (name, guid, position_group_id, position_group) VALUES (?,?, ?,?)", position, positionGUID, positionCategory, positionCategory);
            id = selectPreviousFact(whereString, "spr_position");
            if (id == null)
                throw new RuntimeException("failed to insert new position");
        }
        return id;
    }

    public void batchProcessFactWorktime( List<Object[]> deleteBatch, List<FactWorkTimeDto> list ) {

        String insertSql = "INSERT INTO fact_worktime " +
                "(calendar_id, org_id, depart_id, position_id, snils, listing_count, listing_count_avg, work_time, employee_guid) VALUES " +
                "(?,           ?,      ? ,        ?,           ?,     ?,             ?,                 ?,          ?)";
        String deleteSql = "DELETE FROM fact_worktime WHERE calendar_id=? AND employee_guid=?";
//        String updateSql = "UPDATE fact_worktime SET " +
//                "calendar_id=?, org_id=?, depart_id=?, position_id=?, snils=?, listing_count=?, listing_count_avg=?, work_time=?  WHERE id=?";
        List<Object[]> insertBatch = new ArrayList<>();
        for (FactWorkTimeDto i : list) {
            insertBatch.add(new Object[]{i.calendarId, i.orgId, i.departmentId, i.positionId, i.snils, i.listingCount, i.listingCountAvg, i.workHours, i.employeeGuid});
        }
        try {
            if (deleteBatch.size() > 0)
                getJdbcTemplate().batchUpdate(deleteSql, deleteBatch);
            if (insertBatch.size() > 0)
                getJdbcTemplate().batchUpdate(insertSql, insertBatch);
        }
        finally {
            deleteBatch.clear();
            list.clear();
        }
    }

    public void updateFactStaffStatTotals() {
        log.debug("__updateStaffStat__: insert batch finished. Updating Totals...");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT count(DISTINCT(employee_guid)) FROM fact_staff_stat) WHERE cube_id=15 AND m_key='employee_guid'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT count(DISTINCT(has_staff_doc)) FROM fact_staff_stat) WHERE m_key='has_staff_doc';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT count(DISTINCT(two_pays_count)) FROM fact_staff_stat) WHERE m_key='two_pays_count';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT count(DISTINCT(two_accrues_count)) FROM fact_staff_stat) WHERE m_key='two_accrues_count';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT percent(last_value, (SELECT count(DISTINCT(guid_with_position)) FROM fact_staff_stat)) FROM cube_measure WHERE m_key='has_staff_doc') WHERE m_key='staff_doc_percent';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT percent((SELECT count(DISTINCT(two_accrues_count)) FROM fact_staff_stat), (SELECT count(DISTINCT(employee_guid)) FROM fact_staff_stat))) WHERE m_key='two_accrues_percent';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT percent((SELECT count(DISTINCT(two_pays_percent)) FROM fact_staff_stat), (SELECT count(DISTINCT(employee_guid)) FROM fact_staff_stat)))  WHERE m_key='two_pays_percent';");
        log.debug("__updateStaffStat__: Totals updated");
    }

    public void updateEmployeeStatTotals() {
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT count(DISTINCT(cart_value_filled)) FROM fact_employee_cart) WHERE cube_id=17 AND m_key = 'cart_value_filled'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT count(DISTINCT(has_mandatory_8)) FROM fact_employee_cart) WHERE cube_id=17 AND m_key = 'has_mandatory_8';");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT count(DISTINCT(has_mandatory_5)) FROM fact_employee_cart) WHERE cube_id=17 AND m_key = 'has_mandatory_5'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT count(DISTINCT(staff_guid)) FROM fact_employee_cart) WHERE cube_id=17 AND m_key = 'staff_guid'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT count(DISTINCT(employee_guid)) FROM fact_employee_cart) WHERE cube_id=17 AND m_key = 'employee_guid'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT percent((SELECT count(DISTINCT(has_mandatory_8)) FROM fact_employee_cart), (SELECT count(DISTINCT(staff_guid)) FROM fact_employee_cart))) WHERE cube_id=17 AND m_key = 'optional_percent'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT percent((SELECT count(DISTINCT(has_mandatory_5)) FROM fact_employee_cart), (SELECT count(DISTINCT(employee_guid)) FROM fact_employee_cart))) WHERE cube_id=17 AND m_key = 'mandatory_percent'");
    }

    public void updateFactSalaryTotals() {
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT sum(payed_salary) FROM fact_salary) WHERE cube_id=18 AND m_key = 'payed_salary'");
    }

    public void updateFactWorktimeTotals() {
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT sum(work_time) FROM fact_worktime) WHERE cube_id=19 AND m_key = 'work_time'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT sum(listing_count) FROM fact_worktime) WHERE cube_id=19 AND m_key = 'listing_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value = (SELECT avg(listing_count_avg) FROM fact_worktime) WHERE cube_id=19 AND m_key = 'listing_count_avg'");
    }

    public Integer findFactPositionSalary( Integer calendarId, Integer positionId ) {

        String whereString = String.format("calendar_id=%s AND position_id=%s", calendarId, positionId);
        Integer id = selectPreviousFact(whereString, "fact_position_salary");
        return id;
    }

    public Integer getOrNewAccural( String category ) throws Exception {
        String whereString = String.format("name='%s'", category);
        Integer id = selectPreviousFact(whereString, "spr_accrual");
        if (id == null) {
            getJdbcTemplate().update("INSERT INTO spr_accrual (name) VALUES (?)", category);
            id = selectPreviousFact(whereString, "spr_accrual");
            if (id == null)
                throw new Exception("failed to add new accrual");
        }
        return id;
    }

    public void updateGenericPositions( List<GenericPositionDto> positionsDeleteBatch, List<GenericPositionDto> list ) {

        String insertSql = "INSERT INTO facts_generic_positions" +
                "(inn, position_id, department_id, full_time_positions, data_area, period_end) VALUES " +
                "(?,   ?,           ?,             ?,                   ?,         ?)";
        String deleteSql = "DELETE FROM facts_generic_positions " +
                " WHERE inn=? AND period_end=?";
        List<Object[]> deleteBatch = new ArrayList<>();
        List<Object[]> insertBatch = new ArrayList<>();
        for (GenericPositionDto i : positionsDeleteBatch) {
            deleteBatch.add(new Object[]{i.inn, i.periodEnd});
        }
        for (GenericPositionDto i : list) {
            insertBatch.add(new Object[]{i.inn, i.positionId, i.departmentId, i.fullTimePositions, i.dataAreaId, i.periodEnd});
        }
        if (deleteBatch.size() > 0)
            getJdbcTemplate().batchUpdate(deleteSql, deleteBatch);
        if (insertBatch.size() > 0)
            getJdbcTemplate().batchUpdate(insertSql, insertBatch);
    }
}
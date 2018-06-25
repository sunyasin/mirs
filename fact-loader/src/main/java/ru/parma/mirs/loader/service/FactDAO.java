package ru.parma.mirs.loader.service;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.parma.mirs.loader.dto.*;
import ru.parma.mirs.loader.dto.json.*;
import ru.parma.mirs.loader.dto.json.ListOfDataAreaOrgList.DataAreaOrgList;

import javax.sql.DataSource;
import java.util.*;

import static ru.parma.mirs.loader.dto.json.ApplicationInfo.AppSubsystem.HRM;
import static ru.parma.mirs.loader.dto.json.ApplicationInfo.AppSubsystem.HRMCORP;
import static ru.parma.mirs.loader.service.LoaderLogger.LogModule.*;

/**
 * Основная логика сохранения моделей фактов в БД Postgres
 */
@Repository
public class FactDAO extends JdbcDaoSupport {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FactDAO.class);
    private static final int MAX_FACT_CHUNK = 10000;
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    FactDaoDB storage;

    @Autowired
    FactDAO self;

    @Autowired
    LoaderLogger logger;

    @Autowired
    public FactDAO( DataSource dataSource ) {
        super();
        setDataSource(dataSource);
    }

    private final String buhSqlInsert = "INSERT INTO fact_cube_buh(" +
            "data_area, " +
            "operation_count, " +
            "hand_operation_count, " +
            "correction_count, " +
            "last_operation_date, " +
            "account_balance, " +
            "closed_date, " +
            "closed_periods_count, " +
            "subsystem_id, " +
            "org_id, " +
            "calendar_id) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void storeFactBuh( FactBuhList data, SubsystemEnum subsystem ) throws IllegalAccessException {

        if (data == null || data.dataAreas == null) {
            throw new IllegalArgumentException("data == null || data.dataAreas == null");
        }
        if (subsystem != SubsystemEnum.BGU) {
            throw new IllegalAccessException("Unsupported Subsystem value. Call me for BGU subsystem only. For other values please call storeBuhFactsForZikGu()");
        }
        log.debug(logger.logStart(ACCOUNTING.name(), "-> start storeFactBuh for subsystem =" + subsystem + ". fact count=" + data.dataAreas.size()));
        HashMap<String, Integer> orgIdByInn = new HashMap<>(); // кэш для id по инн
        HashMap<Date, Integer> calendarMap = new HashMap<>(); // date ->  calendarId
        List<Object[]> batchDelete = new ArrayList<>();
        List<Object[]> batchInsert = new ArrayList<>();

        for (FactBuh dataArea : data.dataAreas) {
            int calendarId = self.resolveCalendarId(dataArea.end);
            for (FactBuh.Organization org : dataArea.orgList) {
                Integer orgId = self.resolveOrgIdByInn(org.inn);
                if (orgId == null) {
                    log.warn("ORG not found by INN: " + org.inn);
                    continue;
                }
                batchDelete.add(new Object[]{orgId, calendarId});
                int closedPeriodsCount = resolveClosedPeriodsCountUsingDate(org.closingDate);
                batchInsert.add(new Object[]{
                        dataArea.id,
                        org.totalOperations,
                        org.manualOperations,
                        org.correctiveOperations,
                        org.lastOperationDate,
                        org.accountBalance == null ? 0.0 : org.accountBalance,
                        org.closingDate,
                        closedPeriodsCount,
                        subsystem.getId(),
                        orgId,
                        calendarId
                });
                log.debug("storeFactBuh -- added " + org + ", closedPeriodsCount=" + closedPeriodsCount);
            }
        }
        log.debug(logger.log(ACCOUNTING.name(), "storeFactBuh -- start delete batch size=" + batchDelete.size()));
        getJdbcTemplate().batchUpdate("DELETE FROM fact_cube_buh WHERE org_id=? AND calendar_id=?", batchDelete);
        log.debug(logger.log(ACCOUNTING.name(), "storeFactBuh -- delete batch finished"));
        log.debug(logger.log(ACCOUNTING.name(), "storeFactBuh -- start insert batch size=" + batchInsert.size()));
        getJdbcTemplate().batchUpdate(buhSqlInsert, batchInsert);
        updateCubeBuhTotals();
        log.debug(logger.log(ACCOUNTING.name(), "<- storeFactBuh finished for subsystem =" + subsystem));
    }

    // для куба бухучета Подсистемы ЗикГу использовать данные GetGenericIndicators
    public void storeBuhFactsForZikGu() {

        log.debug(logger.log(ACCOUNTING.name(), "--> storeBuhFactsForZikGu"));
        List<Object[]> batchInsert = new ArrayList<>();

        Date maxDate = getJdbcTemplate().queryForObject("SELECT max(period_end) FROM facts_generic_indicators", Date.class);
        int calendarId = self.resolveCalendarId(maxDate);
        log.debug(logger.log(ACCOUNTING.name(), "MAX date=" + maxDate + ", calendarId=" + calendarId));

        SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(
                "SELECT spr_org.id, i.data_area, i.salary_accounting_closing_date, i.hrm_closing_date, i.payroll_closing_date " +
                        " FROM facts_generic_indicators i " +
                        " LEFT JOIN spr_org ON i.inn = spr_org.inn" +
                        " WHERE i.period_end IN (SELECT max(period_end) FROM facts_generic_indicators)");
        if (rowSet.first()) {
            for (; !rowSet.isAfterLast(); rowSet.next()) {
                Date hrmDate = rowSet.getDate("hrm_closing_date");
                Date salaryDate = rowSet.getDate("salary_accounting_closing_date");
                Date payrollDate = rowSet.getDate("payroll_closing_date");
                int orgId = rowSet.getInt("id");
                int dataArea = rowSet.getInt("data_area");
                batchInsert.add(new Object[]{
                        dataArea, null, null, null, null, null, null,
                        resolveClosedPeriodsCountUsingDate(salaryDate),
                        SubsystemEnum.BUH_ACC_SALARY.getId(), orgId, calendarId});
                batchInsert.add(new Object[]{
                        dataArea, null, null, null, null, null, null,
                        resolveClosedPeriodsCountUsingDate(payrollDate),
                        SubsystemEnum.SALARY.getId(), orgId, calendarId});
                batchInsert.add(new Object[]{
                        dataArea, null, null, null, null, null, null,
                        resolveClosedPeriodsCountUsingDate(hrmDate),
                        SubsystemEnum.STAFF_ACC.getId(), orgId, calendarId});
            }
            log.debug(logger.log(ACCOUNTING.name(), "storeBuhFactsForZikGu --start insert batch size=" + batchInsert.size()));
            getJdbcTemplate().batchUpdate(buhSqlInsert, batchInsert);
            log.debug(logger.log(ACCOUNTING.name(), "storeBuhFactsForZikGu --insert batch finished"));
        } else {
            log.warn(logger.logError(ACCOUNTING.name(), "storeBuhFactsForZikGu exit: [facts_generic_indicators] is empty ?"));
        }
        updateCubeBuhTotals();
        log.debug(logger.log(ACCOUNTING.name(), "<--storeBuhFactsForZikGu finished"));
    }

    private void updateCubeBuhTotals() {
        log.debug("updateCubeBuhTotals -- Updating Totals...");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(correction_count) FROM fact_cube_buh) WHERE cube_id=14 AND m_key='correction_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(operation_count) FROM fact_cube_buh) WHERE cube_id=14 AND m_key='operation_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(hand_operation_count) FROM fact_cube_buh) WHERE cube_id=14 AND m_key='hand_operation_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(closed_periods_count) FROM fact_cube_buh) WHERE cube_id=14 AND m_key='closed_periods_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT sum(account_balance) FROM fact_cube_buh) WHERE cube_id=14 AND m_key='account_balance'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value=(SELECT percent((SELECT sum(hand_operation_count) FROM fact_cube_buh), (SELECT sum(operation_count) FROM fact_cube_buh))) WHERE cube_id=14 AND m_key = 'hand_operation_percent'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT percent((SELECT sum(correction_count) FROM fact_cube_buh), (SELECT sum(operation_count) FROM fact_cube_buh))) WHERE cube_id=14 AND m_key = 'correction_percent'");
        log.debug("updateCubeBuhTotals -- Totals updated");
    }

    //    смотрю дату блокировки - если нет её или прошлый год -0
    //    если есть и она в январе - тоже 0, в феврале - 1, в марте -2 , в апреле - 3 и так далее (номер месяца минус 1)
    private int resolveClosedPeriodsCountUsingDate( Date date ) {

        Calendar cal = Calendar.getInstance();
        if (date == null)
            return 0;
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR))
            return 0;
        return cal.get(Calendar.MONTH);
    }

    @Cacheable("loader-cache")
    public Integer resolveOrgIdByInn( String inn ) {

        List<Integer> ids = getJdbcTemplate().queryForList(
                "select id from spr_org where inn = '" + inn + "'",
                Integer.class);
        if (ids == null || ids.isEmpty()) {
            log.warn("organization not found in [spr_org] by inn " + inn);
            return null;
//            throw new RuntimeException("organization not found by inn " + inn);
        }
        return ids.get(0);
    }

    @Cacheable(value = "loader-cache", key = "#date.toString()")
    public Integer resolveCalendarId( Date date ) throws RuntimeException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer id = getJdbcTemplate().queryForObject(
                "select date_id " +
                        "from spr_date " +
                        "where spr_date.dt_month = " + (cal.get(Calendar.MONTH) + 1) +
                        " AND dt_day = " + cal.get(Calendar.DAY_OF_MONTH) +
                        " AND dt_year = " + cal.get(Calendar.YEAR),
                Integer.class);
        if (id == null) {
            throw new RuntimeException("date_id not found for date " + date);
        }
        return id;
    }

    public HashMap<String, Employee> getEmployeeMap( ListOfDataAreaEmpList facts ) {

        HashMap<String, Employee> guids = new HashMap<>();
        facts.dataAreas.forEach(dataArea -> {
            dataArea.employees.forEach(employee -> {
                guids.putIfAbsent(employee.employeeGUID, employee);
                employee.dataArea = dataArea;
            });
        });
        return guids;
    }


    /**
     * данные для куба Статистика Карточки сотрудника [fact_employee_cart]
     */
    public void storeEmployeesStat( HashMap<String, Employee> guids, String moduleName ) {

        log.debug("--> storeEmployeesStat for " + moduleName);
        logger.log(moduleName, "storeEmployeesStat total employees count=" + guids.size());

        int empCount = 0;
        List<Object[]> batchParams = new ArrayList<Object[]>();
        List<Object[]> deleteBatch = new ArrayList<Object[]>();
        List<String> unknownInn = new ArrayList<>();
        for (Employee emp : guids.values()) {
            Integer orgId = self.resolveOrgIdByInn(emp.organizationINN);
            if (orgId == null) {
                unknownInn.add(emp.organizationINN);
                continue;
            }
            Integer calendarId = self.resolveCalendarId(emp.dataArea.end);
            ++empCount;
            log.debug("process employee #" + (empCount) + " from " + guids.size() + ", orgId=" + orgId + ", date_id=" + calendarId + ", guid=" + emp.employeeGUID);

            deleteBatch.add(new Object[]{orgId, calendarId, emp.dataArea.id, emp.employeeGUID});

            // добавление 13 записей - всех атрибутов карточки - для каждого employee
            boolean isStaff = !StringUtils.isEmpty(emp.position);
            for (CartValue attribute : CartValue.values()) {
                if (attribute.ordinal() == 0)
                    continue;
                boolean hasCartValue = getCartValue(emp, attribute);
                // атрибут заполнен?
                String filledValue = hasCartValue ? emp.employeeGUID : null;
                // один из 5 обязательных для сотрудника?
                String hasMandatoryCommon = attribute.isMandatoryForAll && hasCartValue ? emp.employeeGUID : null;
                // один из 8ми обязательный для штатного сотрудника?
                String hasMandatory4staff = attribute.isMandatoryForStaff && hasCartValue && isStaff ? emp.employeeGUID : null;
                batchParams.add(new Object[]{
                        emp.dataArea.id, orgId, calendarId, attribute.ordinal(), hasMandatoryCommon, hasMandatory4staff, filledValue, emp.employeeGUID,
                        isStaff ? emp.employeeGUID : null // кол-во штатных = Количество уникальных employeeGUID  для которых position не пусто
                });
            }

            if (batchParams.size() >= MAX_FACT_CHUNK) {
                log.debug(logger.log(moduleName, "Chunk limit reached - flushing batch buffers --> 2del=" + deleteBatch.size() + ", insert=" + batchParams.size()));
                batchProcessAndClear(batchParams, deleteBatch, "CHUNK");
                log.debug(logger.log(moduleName, "Chunk butch finished. Employees processed=" + empCount));
            }
        }
        log.debug(logger.log(moduleName, "updating results: added:" + empCount + ", unknown Inns: " + unknownInn.toString() +
                "\nFlushing batch buffers 2del=" + deleteBatch.size() + ", insert=" + batchParams.size()));
        if (batchParams.size() > 0 || deleteBatch.size() > 0) {
            batchProcessAndClear(batchParams, deleteBatch, "last CHUNK");
        }
    }

    public void updateEmployeeStatTotals() {
        storage.updateEmployeeStatTotals();
    }

    private void batchProcessAndClear( List<Object[]> batchParams, List<Object[]> deleteBatch, String prefix ) {
        final String deleteSql = "DELETE FROM fact_employee_cart " +
                "WHERE org_id = ? AND " +
                "   calendar_id=? AND " +
                "   data_area=? AND " +
                "   employee_guid=?";

        final String sqlInsert = "INSERT INTO fact_employee_cart(" +
                "data_area, org_id, calendar_id, cart_value_id, has_mandatory_5, has_mandatory_8, cart_value_filled, employee_guid, staff_guid )" +
                "VALUES (?,   ?,         ?,           ?,              ?,              ?,                  ?,                  ?,          ?)";


        log.debug(prefix + " delete batch started, size=" + deleteBatch.size());
        getJdbcTemplate().batchUpdate(deleteSql, deleteBatch);
        log.debug(prefix + " delete batch finished");
        log.debug(prefix + " insert batch started, Size=" + batchParams.size());
        getJdbcTemplate().batchUpdate(sqlInsert, batchParams);
        log.debug(prefix + " insert batch finished");
        batchParams.clear();
        deleteBatch.clear();
    }

    /*
        обновление куба "дисциплина кадрового учета" [fact_staff_stat]
     */
    public void updateStaffStat( HashMap<String, Employee> guids,
                                 Set<Integer> onlyDataAreas,
                                 String moduleName ) {

        logger.log(moduleName, "updateStaffStat started");
        String sqlInsert = "INSERT INTO fact_staff_stat " +
                "( data_area, org_id, calendar_id, has_staff_doc, two_pays_count, two_accrues_count, employee_guid, guid_with_position ) VALUES " +
                "(   ?,          ?,      ?,          ?,              ?,              ?,                  ?,              ?)";
        List<Object[]> batchParams = new ArrayList<Object[]>();
        List<Object[]> deleteBatch = new ArrayList<Object[]>();
        int empCounter = 1;
        Set<String> skippedByArea = new HashSet<>();
        Set<String> unknownInn = new HashSet<>();
        for (Employee emp : guids.values()) {
            if (!onlyDataAreas.contains(emp.dataArea.id)) {
                log.debug(String.format("updateStaffStat---skip dataArea=%s for employee guid=%s", emp.dataArea.id, emp.employeeGUID));
                skippedByArea.add(emp.employeeGUID);
                continue;
            }
            Integer orgId = self.resolveOrgIdByInn(emp.organizationINN);
            if (orgId == null) {
                log.warn("!!! updateStaffStat: skip unknown inn=" + emp.organizationINN + " for emp guid=" + emp.employeeGUID);
                unknownInn.add(emp.organizationINN);
                continue;
            }
            int calendarId = self.resolveCalendarId(emp.dataArea.end);
            deleteBatch.add(new Object[]{orgId, calendarId});
            String hasStaffDoc = !StringUtils.isEmpty(emp.position) && emp.hasStaffDoc ? emp.employeeGUID : null;
            String hasPosition = StringUtils.isEmpty(emp.position) ? null : emp.employeeGUID;
            String twoPaysCount = emp.salaryPaymentCount >= 2 ? emp.employeeGUID : null;
            String twoAccuresCount = emp.salaryAccrualCount >= 2 ? emp.employeeGUID : null;
            batchParams.add(new Object[]{
                    emp.dataArea.id, orgId, calendarId, hasStaffDoc, twoPaysCount, twoAccuresCount, emp.employeeGUID, hasPosition
            });
            ++empCounter;
            log.debug("updateStaffStat: processed employee #" + (empCounter) + " from " + guids.size());
            if (batchParams.size() >= MAX_FACT_CHUNK) {
                logger.log(moduleName, "updateStaffStat: CHUNK limit reached - flushing...processed employees " + empCounter + " of " + guids.size());
                log.debug("__updateStaffStat__: CHUNK  delete batch started, size=" + deleteBatch.size());
                getJdbcTemplate().batchUpdate("DELETE FROM fact_staff_stat WHERE org_id = ? AND calendar_id=?", deleteBatch);
                log.debug("__updateStaffStat__: CHUNK  delete batch finished. \n Start insert batch size=" + batchParams.size());
                getJdbcTemplate().batchUpdate(sqlInsert, batchParams);
                deleteBatch.clear();
                batchParams.clear();
                logger.log(moduleName, "updateStaffStat: CHUNK limit flushing finished");
            }
        }

        log.debug(logger.log(moduleName, "updateStaffStat: batch update started. size=" + deleteBatch.size()));
        getJdbcTemplate().batchUpdate("DELETE FROM fact_staff_stat WHERE org_id = ? AND calendar_id=?", deleteBatch);
        log.debug("__updateStaffStat__:  delete batch finished. \n Start insert batch size=" + batchParams.size());
        getJdbcTemplate().batchUpdate(sqlInsert, batchParams);
        log.debug(logger.log(moduleName, "updateStaffStat: batch update finished. Processed employees=" + empCounter + "\nUnknown INNs: " + unknownInn + ",\n skipped via dataArea=" + skippedByArea.size()));
    }

    public void updateFactStaffStatTotals() {

        storage.updateFactStaffStatTotals();
    }


    public void updateEmployeeTable( HashMap<String, Employee> guidMap, String moduleName ) {
        log.debug("--> updateEmployeeTable -- employees count=" + guidMap.size());

        if (guidMap.isEmpty())
            return;
        //вычислить какие guids уже есть в БД
        String allGuids = joinToString(guidMap.keySet(), ",", "'");
        List<String> idList = getJdbcTemplate().queryForList(
                "select distinct guid from employee where guid in (" + allGuids + ")",
                String.class);

        // вставить новых или обновить employees
        List<Object[]> updateBatch = new ArrayList<Object[]>();
        List<Object[]> insertBatch = new ArrayList<Object[]>();
        String insertSql = "INSERT INTO employee " +
                "(data_area, org_inn, guid, position, position_guid, rate, snils, salary_accrual_count, salary_payment_count) VALUES     " +
                "(?,         ?,       ?,    ?,        ?,             ?,    ?,     ?,                    ?)";
        String updateSql = "UPDATE employee SET " +
                " data_area=?,org_inn=?,position=?, position_guid=?, rate=?, snils=?, salary_accrual_count=?, salary_payment_count=?" +
                " WHERE guid=?";
        for (Employee emp : guidMap.values()) {
            String position = emp.position == null ? null : emp.position.trim();
            if (!idList.contains(emp.employeeGUID)) {
                log.debug("insert employee " + emp.employeeGUID);
                insertBatch.add(new Object[]{emp.dataArea.id, emp.organizationINN, emp.employeeGUID, position, emp.positionGUID, emp.rate, emp.snils, emp.salaryAccrualCount, emp.salaryPaymentCount});
            } else {
                log.debug("updating employee " + emp.employeeGUID);
                updateBatch.add(new Object[]{emp.dataArea.id, emp.organizationINN, position, emp.positionGUID, emp.rate, emp.snils, emp.salaryAccrualCount, emp.salaryPaymentCount,
                        emp.employeeGUID});
            }
        }
        if (!insertBatch.isEmpty())
            getJdbcTemplate().batchUpdate(insertSql, insertBatch);
        if (!updateBatch.isEmpty())
            getJdbcTemplate().batchUpdate(updateSql, updateBatch);
        log.debug(logger.log(moduleName, "updating employees table finished"));
    }

    private boolean getCartValue( Employee emp, CartValue attribute ) {

        switch (attribute) {
            case NUMBER:
                return emp.hasPersonnelNumber;
            case BIRTHDAY:
                return emp.hasBirthDate;
            case GENDER:
                return emp.hasGender;
            case INN:
                return emp.hasINN;
            case SNILS:
                return StringUtils.isEmpty(emp.snils);
            case BERTHPLACE:
                return emp.hasBirthPlace;
            case ID_PASSPORT:
                return emp.hasIdentificationDoc;
            case ADDRESS:
                return emp.hasAddressInfo;
            case SPECIALITY:
                return emp.hasSpecialty;
            case CAREER:
                return emp.hasCareer;
            case EXPERIENCE:
                return emp.hasExperience;
            case EDUCATION:
                return emp.hasEducation;
            case FAMILY:
                return emp.hasFamily;
        }
        throw new RuntimeException("unknown attribute " + attribute);
    }

    private String joinToString( Collection list, String delim, String quote ) {

        StringBuffer buf = new StringBuffer();
        list.forEach(item -> {
            if (buf.length() > 0)
                buf.append(delim);
            buf.append(quote).append(item).append(quote);
        });
        return buf.toString();
    }

    public void init() {

        parameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
    }


    public void storeOrganizations( OrganizationList facts ) {

        String sql = "INSERT INTO spr_org(org_name, inn) VALUES (?, ?)";
        int i = 1;
        List<Object[]> parameters = new ArrayList<Object[]>();
        for (OrganizationList.Organization org : facts.orgList) {
            System.out.println("storeOrganizations " + i + ", INN=" + org.info.inn);
            parameters.add(new Object[]{org.info.shortName, org.info.inn});
            ++i;
        }
        getJdbcTemplate().batchUpdate(sql, parameters);

    }

    public void updateOrganizations( OrganizationList facts ) {

        String sql = "UPDATE spr_org SET org_name=?, kpp=?, kbk_code=?, kbk_name=?, full_name=?, " +
                "status           =?," +
                "recordnum        =?," +
                "inclusiondate    =?," +
                "exclusiondate    =?," +
                "updatenum        =?," +
                "code             =?," +
                "ogrn             =?," +
                "isuch            =?," +
                "isogv            =?," +
                "isobosob         =?," +
                "ougvcode         =?," +
                "ougvname         =?," +
                "regioncode       =?," +
                "regionname       =?," +
                "oktmocode        =?," +
                "oktmoname        =?," +
                "okatocode        =?," +
                "okatoname        =?," +
                "budgetlvlcode    =?," +
                "budgetlvlname    =?," +
                "budgetcode       =?," +
                "budgetname       =?," +
                "creatorkindcode  =?," +
                "founderkindcode  =?," +
                "creatorkindname  =?," +
                "founderkindname  =?," +
                "сreatorplacecode  =?," +
                "founderplacecode =?," +
                "creatorplacename =?," +
                "founderplacename =?," +
                "parentcode       =?," +
                "parentname       =?" +
                " WHERE inn=?";
        int i = 1;
        List<Object[]> parameters = new ArrayList<Object[]>();
        String name;
        for (OrganizationList.Organization org : facts.orgList) {
            System.out.println("updateOrganizations " + i + ", INN=" + org.info.inn);
            name = StringUtils.isEmpty(org.info.shortName) ? org.info.fullName : org.info.shortName;
            //parameters.add(new Object[]{name, org.info.kpp, org.info.kbkCode, org.info.kbkName, org.info.fullName, org.info.inn});
            getJdbcTemplate().update(sql,
                    name, org.info.kpp, org.info.kbkCode, org.info.kbkName, org.info.fullName,
                    org.info.status,
                    org.info.recordNum,
                    org.info.inclusionDate,
                    org.info.exclusionDate,
                    org.info.updateNum,
                    org.info.code,
                    org.info.ogrn,
                    org.info.isUch,
                    org.info.isOGV,
                    org.info.ougvCode,
                    org.info.ougvName,
                    org.info.isObosob,
                    org.info.regionCode,
                    org.info.regionName,
                    org.info.oktmoCode,
                    org.info.oktmoName,
                    org.info.okatoCode,
                    org.info.okatoName,
                    org.info.budgetLvlCode,
                    org.info.budgetLvlName,
                    org.info.budgetCode,
                    org.info.budgetName,
                    org.info.creatorKindCode,
                    org.info.founderKindCode,

                    org.info.creatorKindName,
                    org.info.founderKindName,

                    org.info.creatorPlaceCode,
                    org.info.founderPlaceCode,

                    org.info.creatorPlaceName,
                    org.info.founderPlaceName,
                    org.info.parentCode,
                    org.info.parentName,

                    org.info.inn
            );
            ++i;
        }
    }

    /*
        заполнение куба "сведения по кадрам" [fact_staff_details]
       0) провалидировать параметры
       1) найти и провалидировать calendar_id для параметра endPeriod
       2) заполняем fact_staff_details
            для каждой организации (после фильтра по dataArea):
            - взять из facts_generic_indicators (найти по ИНН )
                - total_staff_postions и
                - total_full_time_postions
            - для каждого employee этой огранизации:
                - посчитать кол-во has_position = employee.user_guid если position заполнена
                - посчитать кол-во employee_guid = employee.user_guid
                - посчитать сумму position_guid = employee.positionGUID, если не пустая
                - посчитать сумму rate = employee.rate
            - записать в БД
        3) обновить totals
        //todo сделать это в хранимой процедуре
     */
    public void updateCubeStaffDetails( ApplicationInfo apps, String endPeriod ) {

        log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails started"));
        if (endPeriod == null || !endPeriod.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("endPeriod required in YYYYMMDD format!");
        }
        Integer calendarId = resolveCalendarIdByDateFormat(endPeriod);
        if (calendarId == null) {
            throw new IllegalArgumentException("no such dt_value in spr_date! -->" + endPeriod);
        }
        final String sqlInsert = "INSERT INTO fact_staff_details " +
                "(calendar_id, org_id, has_position_count, employee_count, position_guid_count, rate_sum, total_staff_positions, total_full_time_positions) VALUES " +
                "(  ?,            ?,        ?,              ?,          ?,          ?,              ?,                      ?)";
        List<Object[]> batchDelete = new ArrayList<>();
        List<Object[]> batchInsert = new ArrayList<>();
        HashSet<String> unknownInns = new HashSet<>();
        Set<Integer> dataAreas = apps.getDataAreasForSubsystem(HRM);
        Set<Integer> dataAreasCorp = apps.getDataAreasForSubsystem(HRMCORP);
        dataAreas.addAll(dataAreasCorp);
        String dataAreasSql = joinToString(dataAreas, ", ", "");
        log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails: END date=" + endPeriod + ", calendarId=" + calendarId + ", dataAreas=" + dataAreasSql));

        SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(
                "SELECT gi.inn, gi.total_staff_positions, gi.total_full_time_positions, \n" +
                        "      count(DISTINCT(position_guid)) unique_pos_guid,\n" +
                        "      count(DISTINCT(guid)) u_guid_count,\n" +
                        "      sum(rate) rate_sum\n" +
                        "     FROM employee\n" +
                        "       join facts_generic_indicators gi on org_inn = gi.inn\n" +
                        "     WHERE employee.data_area IN  (" + dataAreasSql + ")" +
                        "     AND period_end = to_date('" + endPeriod + "', 'YYYYMMDD')\n" +
                        "     GROUP BY inn, total_staff_positions, total_full_time_positions");

        if (rowSet.first()) {
            for (; !rowSet.isAfterLast(); rowSet.next()) {
                String inn = rowSet.getString("inn");
                Integer orgId = self.resolveOrgIdByInn(inn);
                if (orgId == null) {
                    log.warn("updateCubeStaffDetails: skip unknown inn=" + inn);
                    unknownInns.add(inn);
                    continue;
                }
                batchDelete.add(new Object[]{calendarId, orgId});
                int rateSum = rowSet.getInt("rate_sum");
                int guidsCount = rowSet.getInt("u_guid_count");//Количество уникальных employeeGUID
                int uniquePositionGuidCount = rowSet.getInt("unique_pos_guid"); //Количество уникальных не пустых positionGUID.
                int totalStaffPositions = rowSet.getInt("total_staff_positions");
                float totalFullTimePositions = rowSet.getFloat("total_full_time_positions");
                log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails: got for inn=" + inn + ", orgID=" + orgId + ", totalStaffPos=" + totalStaffPositions + ", totalFullPos=" + totalFullTimePositions));

                SqlRowSet empRowSet = getJdbcTemplate().queryForRowSet(
                        "SELECT count(DISTINCT(guid)) has_position FROM employee " +
                                " WHERE employee.data_area IN  (" + dataAreasSql + ")" +
                                " AND org_inn =? AND length(position)>0", inn);
                if (empRowSet.first()) {
                    int hasPosition = empRowSet.getInt("has_position"); //Количество уникальных employeeGUID  для которых position не пусто
                    log.debug(String.format("updateCubeStaffDetails: insert to [fact_staff_details] inn=%s, orgId=%s, dateId=%s, hasPosition=%s, guidsCount=%s, uniquePosGuidCount=%s, rateSum=%s, totalStaffPositions=%s, totalFullTimePositions=%s",
                            inn, orgId, calendarId, hasPosition, guidsCount, uniquePositionGuidCount, rateSum, totalStaffPositions, totalFullTimePositions));
                    batchInsert.add(new Object[]{calendarId, orgId, hasPosition, guidsCount, uniquePositionGuidCount, rateSum, totalStaffPositions, totalFullTimePositions});
                } else {
                    log.debug(logger.log(STAFF_DETAILS.name(), String.format("updateCubeStaffDetails: no employee found for inn %s, with filled position and available dataAreas", inn)));
                }
            }

            log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails --- deleting batch started size=" + batchDelete.size()));
            getJdbcTemplate().batchUpdate("DELETE FROM fact_staff_details WHERE calendar_id=? AND org_id=?", batchDelete);
            log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails --- inserting batch started size=" + batchInsert.size()));
            getJdbcTemplate().batchUpdate(sqlInsert, batchInsert);
            log.debug(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails --- inserting batch finished"));

            updateStaffDetailsTotals();
        } else {
            log.warn(logger.log(STAFF_DETAILS.name(), "updateCubeStaffDetails failed: maybe got empty [select from facts_generic_indicators  " +
                    "WHERE period_end=to_date('" + endPeriod + "', 'YYYYMMDD') AND dataAreas in " + dataAreasSql));
        }
    }

    private Integer resolveCalendarIdByDateFormat( String endPeriod ) {
        return getJdbcTemplate().queryForObject("SELECT date_id FROM spr_date WHERE dt_value = to_date('" + endPeriod + "', 'YYYYMMDD')", Integer.class);
    }

    private void updateStaffDetailsTotals() {
        String where = " WHERE calendar_id in (select max(calendar_id) from fact_staff_details) ";
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(rate_sum) FROM fact_staff_details " + where + ") WHERE m_key = 'rate_sum'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(employee_count) FROM fact_staff_details " + where + ") WHERE m_key = 'employee_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(has_position_count) FROM fact_staff_details " + where + ") WHERE m_key = 'has_position_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(position_guid_count) FROM fact_staff_details " + where + ") WHERE m_key = 'position_guid_count'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(total_staff_positions) FROM fact_staff_details " + where + ") WHERE m_key = 'total_staff_positions'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT sum(total_full_time_positions) FROM fact_staff_details " + where + ") WHERE m_key = 'total_full_time_positions'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT percent((SELECT sum(rate_sum) FROM fact_staff_details " + where + "), (SELECT sum(total_full_time_positions) FROM fact_staff_details " + where + "))) WHERE m_key = 'staff_fill_percent'");
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT percent((SELECT sum(employee_count) FROM fact_staff_details " + where + "), (SELECT sum(total_staff_positions) FROM fact_staff_details " + where + "))) WHERE m_key = 'occupied_percent'");
        log.debug("updateCubeStaffDetails --- totals updated for [fact_staff_details]");
    }


    /**
     * сохранение ежедневных показателей
     * lastOperationDate - из FactBuhList
     * lastHRMOperationDate, lastPayrollOperationDate - из Generic_indicators берутся
     *  @param factBuhList - BGU факты
     * @param generic     - GenericIndicators факты
     * @param dateEnd     - дата в формате
     */
    public void storeDaily( FactBuhList factBuhList,
                            ListOfDataAreaOrgList generic,
                            String dateEnd ) {

        List<Object[]> batchDelete = new ArrayList<>();
        List<Object[]> batchInsert = new ArrayList<>();
        HashMap<String, Date> inn2DateMap = new HashMap<>();
        HashMap<String, GenericIndicatorsNew> inn2GenericMap = new HashMap<>();
        HashSet<String> doubleInn = new HashSet<>();
        HashSet<String> doubleInnGen = new HashSet<>();
        for (DataAreaOrgList<GenericIndicatorsNew> genDataArea : generic.dataAreas) {
            genDataArea.organizations.forEach(item -> {
                if (inn2GenericMap.containsKey(item.inn)) {
                    doubleInnGen.add(item.inn);
                }
                inn2GenericMap.put(item.inn, item);
            });
        }
        log.debug(logger.log(DAILY.name(), "storeDaily: BGU facts=" + factBuhList.getSummary()));
        for (FactBuh dataArea : factBuhList.dataAreas) {
            log.debug(String.format("storeDaily: dataArea=%s, end=%s, orgs count=%s", dataArea.id, dataArea.end, dataArea.orgList.size()));
            for (FactBuh.Organization org : dataArea.orgList) {
                log.debug(String.format("storeDaily: store org.inn=%s, dateEnd=%s, lastOpDate=%s", org.inn, dateEnd, org.lastOperationDate));
                batchDelete.add(new Object[]{dateEnd, org.inn});
                if (!inn2DateMap.containsKey(org.inn)) {
                    inn2DateMap.put(org.inn, org.lastOperationDate);
                } else {
                    doubleInn.add(org.inn);
                    inn2DateMap.merge(org.inn, org.lastOperationDate, ( oldVal, newVal ) ->
                            newVal != null && newVal.compareTo(oldVal) > 0 ? newVal : oldVal);
                }
            }
        }
        inn2DateMap.forEach(( orgInn, date ) -> {
            GenericIndicatorsNew genItem = inn2GenericMap.get(orgInn);
            if (genItem == null) { // уникальные Инн в FactBuh списке
                batchInsert.add(new Object[]{date, orgInn, dateEnd, null, null});
            } else { // совпавшие по инн
                batchInsert.add(new Object[]{date, orgInn, dateEnd, genItem.lastHRMOperationDate, genItem.lastPayrollOperationDate});
            }
        });
        // добавить уникальные ИНН из inn2GenericMap
        inn2GenericMap.forEach(( orgInn, genItem ) -> {
            if (!inn2DateMap.containsKey(orgInn)) {
                batchDelete.add(new Object[]{dateEnd, orgInn});
                batchInsert.add(new Object[]{null, orgInn, dateEnd, genItem.lastHRMOperationDate, genItem.lastPayrollOperationDate});
            }
        });
        log.debug(logger.log(DAILY.name(), "loadDaily: starting delete Batch size=" + batchDelete.size()));
        getJdbcTemplate().batchUpdate("DELETE FROM load_daily WHERE date_end=to_date(?, 'YYYYMMDD') AND org_inn=?", batchDelete);
        log.debug(logger.log(DAILY.name(), "loadDaily: starting insert batch size=" + batchInsert.size()));

        getJdbcTemplate().batchUpdate("INSERT INTO load_daily " +
                        "(last_operation_date, org_inn, date_end, last_hrm_operation_date, last_payroll_operation_date) VALUES " +
                        "(?,                   ?,       to_date(?, 'YYYYMMDD'),  ?,        ?                         )",
                batchInsert);

        log.debug(logger.log(DAILY.name(), "loadDaily: batch finished. Duplicated BGU INNs: " + doubleInn + ", duplicates in Generics: " + doubleInnGen));
    }

    /*
     обновление куба [fact_user_activity]
Количество календарных дней с даты последней операции добавления или изменения объекта в системе до текущей, исключая первую
    Для подсистемы «ЗиК» в детализации разделов учета:
        lastHRMOperationDate  – для раздела «Кадровый учет»
        lastPayrollOperationDate - для раздела «Зарплата»
    Для подсистемы «БГУ»
        lastOperationDate

      */
    public void updateCubeUserActivity( String dateEndStr ) {

        List<Object[]> batchDelete = new ArrayList<>();
        List<Object[]> batchInsert = new ArrayList<>();
        HashSet<String> unknownInns = new HashSet<>();
        HashSet<String> badDates = new HashSet<>();
        final String sql = "SELECT org_inn, date_end, last_hrm_operation_date, last_payroll_operation_date, last_operation_date " +
                " FROM load_daily" +
                " WHERE date_end=to_date('" + dateEndStr + "', 'YYYYMMDD')";
        final String insertSql = "INSERT INTO fact_user_activity " +
                "( subsystem_id, org_id, calendar_id, days_from_last_activity) VALUES" +
                "(   ?,              ?,       ?,                 ?        )";
        SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
        if (rowSet.first()) {
            for (; !rowSet.isAfterLast(); rowSet.next()) {
                String inn = rowSet.getString("org_inn");
                Integer orgId = self.resolveOrgIdByInn(inn);
                if (orgId == null) {
                    unknownInns.add(inn);
                    continue;
                }
                java.sql.Date dateEnd = rowSet.getDate("date_end");
                int calendarId = self.resolveCalendarId(dateEnd);
                batchDelete.add(new Object[]{orgId, calendarId});

                Date lastHRMOperationDate = rowSet.getDate("last_hrm_operation_date");
                Date lastPayrollOperationDate = rowSet.getDate("last_payroll_operation_date");
                Date lastOperationDate = rowSet.getDate("last_operation_date");
                long staffAccDays = calculateNumberOfDays(lastHRMOperationDate);
                long payrollDays = calculateNumberOfDays(lastPayrollOperationDate);
                long bguDays = calculateNumberOfDays(lastOperationDate);
                if (staffAccDays < 0 || payrollDays < 0 || bguDays < 0) {
                    badDates.add(inn);
                }
                if (staffAccDays >= 0) // игнорируем даты из будущего
                    batchInsert.add(new Object[]{SubsystemEnum.STAFF_ACC.getId(), orgId, calendarId, staffAccDays});//lastHRMOperationDate
                if (payrollDays >= 0)
                    batchInsert.add(new Object[]{SubsystemEnum.SALARY.getId(), orgId, calendarId, payrollDays});//lastPayrollOperationDate
                if (bguDays >= 0)
                    batchInsert.add(new Object[]{SubsystemEnum.BGU.getId(), orgId, calendarId, bguDays});//lastOperationDate
                log.debug("updateCubeUserActivity: processed orgs so far=" + batchDelete.size());
            }
            log.debug(logger.log(DAILY.name(), "loadDaily: starting delete Batch size=" + batchDelete.size()));
            getJdbcTemplate().batchUpdate("DELETE FROM fact_user_activity WHERE org_id=? AND calendar_id=?", batchDelete);
            log.debug(logger.log(DAILY.name(), "loadDaily: starting insert Batch size=" + batchInsert.size()));
            getJdbcTemplate().batchUpdate(insertSql, batchInsert);
            log.debug(logger.log(DAILY.name(), "loadDaily: insert Batch finished. Unknown INNs:" + unknownInns));
        }
    }

    private long calculateNumberOfDays( Date fromDate ) {

        final Date zeroDate = new Date(1);
        if (fromDate == null || fromDate.before(zeroDate))
            return -1;
        return (new Date().getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    public void updateCubeUserActivityTotals() {
        getJdbcTemplate().update("UPDATE cube_measure SET last_date=now(), last_value= (SELECT max(days_from_last_activity) FROM fact_user_activity) WHERE m_key = 'days_from_last_activity'");

    }

    /**
     * загрузка в кубы
     * "Статистика объектов",
     * "Сведения о подключении",
     * "Сведения об активности пользователей"
     *
     * @param statResult
     * @param loadDate   - данные на эту дату
     */
    public void updateStatistics( OrgStat statResult, String loadDate ) {

        if (statResult == null || statResult.abonents == null) {
            log.warn(logger.log(DAILY.name(), "got empty abonent list from service"));
            return;
        }
        Integer calendarId = resolveCalendarIdByDateFormat(loadDate);
        if (calendarId == null) {
            log.error(logger.logError(DAILY.name(), "bad or invalid date format " + loadDate));
            return;
        }
        log.debug(logger.logStart(DAILY.name(), "updateStatistics: loadDate=" + loadDate + " (" + calendarId + ")"));
        List<FactObjectStatDto> objStatUpdate = new ArrayList<>();
        List<FactConnectionsDto> connectionsUpdateList = new ArrayList<>();
        List<FactUserActivityDto> activityBatch = new ArrayList<>();
        List<String> noInn = new ArrayList<>();
        Set<String> unknownInn = new HashSet<>();
        Set<String> badCounters = new HashSet<>();
        HashMap<String, Integer> objectTypes = storage.loadDictionary("spr_object_type");
        HashMap<String, Integer> objectIds = storage.loadDictionary("spr_object");

        for (OrgStat.OrgStatDetails org : statResult.abonents) {

            if (StringUtils.isEmpty(org.inn)) {
                log.warn(logger.log(DAILY.name(), "no inn for name " + org.name));
                noInn.add(org.name);
                continue;
            }
            Integer orgId = self.resolveOrgIdByInn(org.inn);
            if (orgId == null) {
                unknownInn.add(org.inn);
                continue;
            }
            // for updating [fact_connections]
            if (org.countNewUsers == null || org.countUsers == null) {
                log.warn(logger.log(DAILY.name(), "invalid user (countNewUsers & countUsers) found for org " + org));
                badCounters.add(org.inn);
            } else {
                Integer id = storage.getFactConnectionId(orgId, calendarId);
                connectionsUpdateList.add(new FactConnectionsDto(org.countNewUsers, org.countUsers, orgId, calendarId, id));
            }
            // for updating [fact_object_stat]
            updateObjectStatInternal(org, orgId, calendarId, objectTypes, objectIds, objStatUpdate, activityBatch);
        }
        storage.updateFactConnections(connectionsUpdateList);
        storage.updateFactObjectStat(objStatUpdate);
        storage.updateUserActivity(activityBatch);

        storage.updateFactConnectionsTotals();
        storage.updateFactObjectStatTotals();
        storage.updateUserActivityTotals();

        log.info(logger.logEnd(DAILY.name(), "updateStatistics results: connections updated (" + connectionsUpdateList.size() +
                "),\n ObjectStat updated count=" + objStatUpdate.size() +
                ",\n noInns:" + noInn + ",\n badCounters=" + badCounters + ",\n unknownInns=" + unknownInn));
    }


    private void updateObjectStatInternal( OrgStat.OrgStatDetails org,
                                           Integer orgId,
                                           Integer calendarId,
                                           HashMap<String, Integer> objectTypes,
                                           HashMap<String, Integer> objectIds,
                                           List<FactObjectStatDto> batchUpdate,
                                           List<FactUserActivityDto> activityBatch ) {

        if (org.subsystems != null) {
            org.subsystems.forEach(subsystem -> {
                OrgStatSubsystemEnum subsytemId = OrgStatSubsystemEnum.getById(subsystem.name);
                if (subsytemId == null) {
                    log.warn(logger.log(DAILY.name(), "unknown subsystem  " + subsystem.name));
                } else if (subsystem.objects == null) {
                    log.warn(logger.log(DAILY.name(), "null objects array for " + org));
                } else {
                    int batchSize = batchUpdate.size();
                    subsystem.objects.forEach(object -> {
                        if (StringUtils.isNotEmpty(object.type) && StringUtils.isNotEmpty(object.name)) {
                            Integer objectTypeId = storage.getOrNewObjectType(object.type, objectTypes);
                            Integer objectId = storage.getOrNewObjectId(object.name, objectIds, objectTypeId);
                            Integer id = storage.findFactObjectStat(orgId, calendarId, objectId, subsytemId.getMirsCode());
                            batchUpdate.add(new FactObjectStatDto(object.countReports, object.countChanges, object.countNew,
                                    orgId, calendarId, objectId, subsytemId.getMirsCode(), id));
                        } else {
                            log.warn(logger.log(DAILY.name(), "skip empty object type or name for " + org + ",\nOBJECT=" + object));
                        }
                        // [fact_user_activity]
                        Integer id = storage.findUserActivityFact(orgId, calendarId, subsytemId.getMirsCode());
                        activityBatch.add(new FactUserActivityDto(id, orgId, calendarId, subsytemId.getMirsCode(), object.timeWork, object.maxLoginIB));
                    });
                    log.info(logger.log(DAILY.name(), "processed objects for " + org.inn + ", =" + (batchUpdate.size() - batchSize)));
                }
            });
        }
    }


    public void storeGenericIndicatorsNew( ListOfDataAreaOrgList facts ) {

        log.debug(logger.logStart(GENERIC.name(), "-> storeGenericIndicators " + facts.getSummary()));
        int processed = 0, orgCount = 0;
        HashMap<String, Integer> inn2AreaMap = new HashMap<>();
        HashSet<String> innsInTwoAreas = new HashSet<>();
        List<GenericIndicatorDto> batchUpdate = new ArrayList<>();
        List<GenericPositionDto> positionsBatch = new ArrayList<>();
        List<GenericPositionDto> positionsDeleteBatch = new ArrayList<>();
        for (DataAreaOrgList<GenericIndicatorsNew> dataArea : facts.dataAreas) {
            orgCount += dataArea.organizations.size();
            for (GenericIndicatorsNew org : dataArea.organizations) {
//                Integer factId = selectPreviousFact(String.format("inn='%s' AND data_area=%s", org.inn, dataArea.id), "facts_generic_indicators");
                // !!! второй инн с другой dataArea затрет старое значение! но это неверная ситуация в поставщике данных
                if (inn2AreaMap.containsKey(org.inn)) {
                    log.warn(logger.log(GENERIC.name(), "Inn " + org.inn + " appears in several dataAreas! " + inn2AreaMap.get(org.inn) + " and " + dataArea.id));
                    innsInTwoAreas.add(org.inn);
                    continue;
                }
                inn2AreaMap.put(org.inn, dataArea.id);

                Integer factId = storage.findGenericFact(org.inn, dataArea.end);

                batchUpdate.add(new GenericIndicatorDto(factId, org.inn,
                        org.totalStaffPositions, org.totalFullTimePositions, org.hrmClosingDate, org.payrollClosingDate, org.salaryAccountingClosingDate,
                        org.lastHRMOperationDate, org.lastPayrollOperationDate, dataArea.id, dataArea.end));

                if (org.positions != null) {
                    Integer positionId, departmentId;
                    positionsDeleteBatch.add(new GenericPositionDto(org.inn, dataArea.end));
                    for (GenericIndicatorsNew.Position position : org.positions) {
                        departmentId = storage.findDeparment(position.departmentGUID, position.department);//todo cachable
                        positionId = storage.findPosition(position.positionGUID, position.position, position.positionCategory);
                        positionsBatch.add(new GenericPositionDto(org.inn, positionId, departmentId, position.fullTimePositions, dataArea.id, dataArea.end));
                    }
                }
                ++processed;
            }
            log.debug(logger.log(GENERIC.name(), "processed dataArea " + dataArea.id + ", total " + processed + " of " + orgCount));
        }
        log.debug(logger.log(GENERIC.name(), "batch size=" + batchUpdate.size()));
        storage.updateGenericIndicators(batchUpdate);
        log.debug(logger.log(GENERIC.name(), "positionsBatch size=" + positionsBatch.size()));
        storage.updateGenericPositions(positionsDeleteBatch, positionsBatch);
        log.debug(logger.logEnd(GENERIC.name(), "<-- storeGenericIndicators finished. Processed " + processed + " of " + orgCount + ", INNs appear in several dataAreas:" + innsInTwoAreas));
    }

    /**
     * данные для куба Структура заработной платы [fact_salary]
     */
    public void updateFactSalary( HashMap<String, Employee> guids, String moduleName ) throws Exception {

        log.debug("--> updateFactSalary for " + moduleName);
        logger.log(moduleName, "updateFactSalary total employees count=" + guids.size());

        Integer calendarId = null;
        int empCount = 0;
        List<FactSalaryDto> salaryBatch = new ArrayList<>();
        List<FactPositionSalaryDto> positionSalaryBatch = new ArrayList<>();
        Float sum = 0f;
        for (Employee emp : guids.values()) {
            Integer orgId = self.resolveOrgIdByInn(emp.organizationINN);
            if (orgId == null) {
                continue;
            }
            if (calendarId == null) // нет смысла вычислять для каждого: у всех будет одно и то же значение равное дате в параметре end
                calendarId = self.resolveCalendarId(emp.dataArea.end);
            ++empCount;
            log.debug("process employee #" + (empCount) + " from " + guids.size() + ", orgId=" + orgId + ", date_id=" + calendarId + ", guid=" + emp.employeeGUID);

            //Integer factSalaryId = storage.findFactSalary(calendarId, emp.employeeGUID);
            Integer departmentId = storage.findDeparment(emp.departmentGUID, emp.department);

            //для куба position_salary
            Integer positionId = storage.findPosition(emp.positionGUID, emp.position, emp.positionCategory);
            Integer factPositionSalaryId = storage.findFactPositionSalary(calendarId, positionId);

            if (emp.salary != null) {
                for (Employee.EmployeeSalary salaryItem : emp.salary) {
                    Integer accuralId = storage.getOrNewAccural(salaryItem.category);
                    positionSalaryBatch.add(new FactPositionSalaryDto(factPositionSalaryId, calendarId, positionId, salaryItem.amount, accuralId));
                    salaryBatch.add(new FactSalaryDto(null, calendarId, orgId, departmentId, positionId, emp.snils, salaryItem.amount, accuralId, emp.employeeGUID));
                }
            }

            if (salaryBatch.size() > MAX_FACT_CHUNK) {
                log.debug(logger.log(moduleName, "updating factSalary CHUNK LIMIT reached - flushing. Processed so far:" + empCount));
                storage.batchProcessAndClear(salaryBatch);
            }
            if (positionSalaryBatch.size() > MAX_FACT_CHUNK) {
                log.debug(logger.log(moduleName, "updating factPositionSalary CHUNK LIMIT reached - flushing. Processed so far:" + empCount));
                storage.batchFactPositionSalaryAndClear(positionSalaryBatch);
            }
        }
        if (salaryBatch.size() > 0) {
            storage.batchProcessAndClear(salaryBatch);
        }
        if (positionSalaryBatch.size() > 0) {
            storage.batchFactPositionSalaryAndClear(positionSalaryBatch);
        }
        log.debug(logger.log(moduleName, "updating results: added:"
                + empCount + "\nFlushing batch buffers done (last salary chunk size=" +
                salaryBatch.size() + ", positionSalaryBatch=" + positionSalaryBatch.size()));
    }


    /**
     * данные для куба Структура заработной платы [fact_salary]
     */
    public void updateFactWorkTime( HashMap<String, EmployeeDates> empGuids, String moduleName ) throws Exception {
        log.debug(logger.log(moduleName, "updateFactWorkTime for module " + moduleName + ", total employees count=" + empGuids.size()));
        int empCount = 0;

        List<Object[]> deleteBatch = new ArrayList<>();
        List<FactWorkTimeDto> updateBatch = new ArrayList<>();
        for (EmployeeDates emp : empGuids.values()) {
            Integer orgId = self.resolveOrgIdByInn(emp.organizationINN);
            if (orgId == null) {
                continue;
            }
            ++empCount;
            log.debug("process employee #" + (empCount) + " from " + empGuids.size() + ", orgId=" + orgId + ", guid=" + emp.employeeGUID);

            //Integer factId = storage.findFactWorkTime(calendarId, emp.employeeGUID);// может быть 1 человек на 2х и более позициях на одном предприятии - у него будет разный employee_guid
            Integer departmentId = storage.findDeparment(emp.departmentGUID, emp.department);
            Integer positionId = storage.findPosition(emp.positionGUID, emp.position, emp.positionCategory);
            for (EmployeeDates.Dates date : emp.dates) {
                Integer dateId = self.resolveCalendarId(date.date);
                deleteBatch.add(new Object[]{dateId, emp.employeeGUID});
                updateBatch.add(new FactWorkTimeDto(null, dateId, orgId, departmentId, positionId, emp.snils, date.workHours, date.staffCount, emp.employeeGUID));
            }
            if (updateBatch.size() > MAX_FACT_CHUNK) {
                log.debug(logger.log(moduleName, "updateFactWorkTime: CHUNK LIMIT. flushing batch buffers size=" + updateBatch.size() + ", processed=" + empCount + " of " + empGuids.size()));
                storage.batchProcessFactWorktime(deleteBatch, updateBatch);
                log.debug(logger.log(moduleName, "CHUNK update finished"));
            }
        }
        log.debug(logger.log(moduleName, "updateFactWorkTime: flushing last buffer size=" + updateBatch.size()));
        if (updateBatch.size() > 0) {
            storage.batchProcessFactWorktime(deleteBatch, updateBatch);
        }
        log.debug(logger.log(moduleName, "updateFactWorkTime results: records added=" + empCount));
    }

    public void updateFactSalaryTotals( String module ) {
        storage.updateFactSalaryTotals();
    }

    public void updateFactWorktimeTotals( String module ) {
        storage.updateFactWorktimeTotals();
    }

    public HashMap<String, EmployeeDates> getEmployeeMap( ListOfEmployeeDates facts ) {

        HashMap<String, EmployeeDates> guids = new HashMap<>();
        facts.dataAreas.forEach(dataArea -> {
            dataArea.employees.forEach(employee -> {
                guids.putIfAbsent(employee.employeeGUID, employee);
            });
        });
        return guids;
    }
}
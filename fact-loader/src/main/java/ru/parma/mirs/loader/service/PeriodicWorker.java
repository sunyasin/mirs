package ru.parma.mirs.loader.service;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.parma.mirs.loader.dto.json.*;
import ru.parma.mirs.loader.model.LoaderFormDto;

import java.io.IOException;
import java.util.*;

import static ru.parma.mirs.loader.dto.json.ApplicationInfo.AppSubsystem.*;
import static ru.parma.mirs.loader.dto.json.ApplicationInfo.AppSubsystem.HRM;
import static ru.parma.mirs.loader.dto.json.ApplicationInfo.AppSubsystem.HRMCORP;
import static ru.parma.mirs.loader.service.LoaderLogger.LogModule.*;

@Service
@PropertySource("classpath:fact-source.properties")
public class PeriodicWorker {

    private static final Logger LOG = LogManager.getLogger();
    public static final String END_PARAM = "?end=";

    @Value("${applications_list_url}")
    private String applicationsListUrl;

    @Value("${run_in_thread}")
    private boolean runInThread;

    @Value("${organizationRegistryUrl}")
    private String organizationRegistryUrl;

    @Value("${loadFactStaffDetailsUrl}")
    private String loadFactStaffDetailsUrl;

    @Value("${fact_buh_url_BGU}")
    private String factBuhUrlBGU;

    @Value("${HRM_indicators_url}")
    private String HRMIndicatorsUrl;

    @Value("${HRM_indicators_corp_url}")
    private String HRMIndicatorsCorpUrl;

    @Value("${HRM_indicators_dates_url}")
    private String HRMIndicatorsDatesUrl;// сведения по рабочим часам
    @Value("${HRM_indicators_dates_corp_url}")
    private String HRMIndicatorsDatesCorpUrl;// сведения по рабочим часам

    //сведения по датам операций, датам закрытия периодов и показатели штатного расписания.
    @Value("${genericIndicators_zikgu}")
    private String genericIndicatorsZikgu;
    @Value("${genericIndicators_zikgu_corp}")
    private String genericIndicatorsZikguCorp;

    @Value("${stat_url}")
    private String statServiceUrl;

    @Value("${genericIndicators_new}")
    private String genericIndicatorsNew;


    //@Autowired in constuctor
    private FactDAO factDao;

    //@Autowired in constuctor
    LoaderLogger logger;

    private boolean loadIndicatorsStarted = false;  // флаг загрузка основных индикаторов началась
    private boolean loadAccountingStarted = false;  //  флаг загрузка с сервиса Accounting началась
    private boolean loadDaily = false;              //  флаг загрузка ежедневных данных началась

    @Autowired
    private JsonLoaderTask jsonLoader;

    @Autowired
    public PeriodicWorker( LoaderLogger logger, FactDAO factDao ) {
        this.logger = logger;
        this.factDao = factDao;
        LOG.info(logger.logStart(DAILY.name(), "PeriodicWorker constructor!"));
    }

    //загрузка ежедневных пок-лей
    @Scheduled(cron = "0 0 11 */1 * ?") //в 11-00 каждый день sec-min-hour-day-month-dofweek[-year]
    public void loadDailyPeriodic() {
        LOG.info(logger.logStart(DAILY.name(), "loadIndicatorsPeriodic started as scheduled"));
        LoaderFormDto params = new LoaderFormDto();
        params.setDoLoadDaily(true);
        String today = String.format("%1$tY%1$tm%1$td", Calendar.getInstance());// YYYYMMDD
        params.setEndPeriod(today);
        loadDaily(params);
        LOG.info(logger.logStart(DAILY.name(), "loadIndicatorsPeriodic finished"));
    }

    // загрузка основных индикаторов
    @Scheduled(cron = "0 0 12 */2 * ?") //в 12-00 через день
    public void loadIndicatorsPeriodic() {

        LOG.info(logger.log(GENERIC.name(), "loadIndicatorsPeriodic started as scheduled"));
        LoaderFormDto params = new LoaderFormDto();
        params.setDoLoadGenericIndicators(true);
        params.setDoLoadHrmIndicators(true);
        params.setDoLoadHrmIndicatorsCorp(true);
        params.setDoLoadHrmIndicatorsAndWorkTime(true);
        loadIndicators(params);
        LOG.info(logger.log(GENERIC.name(), "loadIndicatorsPeriodic finished"));
    }

    //AccountingINfo загрузка
    @Scheduled(cron = "0 30 11 */1 * ?") //(в 11-30 через день)
    public void loadAccountingPeriodic() {

        LOG.info(logger.logStart(ACCOUNTING.name(), "loadAccountingPeriodic started as scheduled"));
        String today = String.format("%1$tY%1$tm%1$td", Calendar.getInstance());// YYYYMMDD
        loadFactBuh(today);
        LOG.info(logger.logEnd(ACCOUNTING.name(), "loadAccountingPeriodic finished"));
    }

    /**
     * загрузка кодов приложений (DataAreas) из сервиса
     *
     * @return стуктуру со списком приложений
     * @throws IOException
     */
    public ApplicationInfo loadDataAreas() throws IOException {
        try {
            ApplicationInfo dataAreaList = jsonLoader.loadDataAreas(applicationsListUrl);
            return dataAreaList;
        }
        catch (Exception e) {
            String stack = Arrays.asList(e.getStackTrace()).toString();
            LOG.error("loadDataAreas failed: " + stack);
            throw e;
        }
    }

    public void loadIndicators( LoaderFormDto params ) {

        if (loadIndicatorsStarted) {
            logStart("SKIP loadIndicators - already started");
            return;
        }
        if (runInThread) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    String logMessage = "loadIndicators IN THREAD " + Thread.currentThread().getName();
                    logStart(logMessage);
                    loadIndicatorsInternal(params);
                    logEnd(logMessage);
                }
            };
            new Thread(task).start();
        } else {
            loadIndicatorsInternal(params);
        }
    }

    public void loadIndicatorsInternal( LoaderFormDto params ) {

        loadIndicatorsStarted = true;
        try {
            logStart(logger.logStart(GENERIC.name(), "loadIndicatorsInternal params=" + params));
            ApplicationInfo apps = loadDataAreas();

            if (params.isDoLoadGenericIndicators()) { // загрузка generic_indicators по предприятиям в [facts_generic_indicators]
                updateGenericIndicators(apps, params.getEndPeriod());
            }

            // 2 куба: 1) Статистика Карточки сотрудника [fact_employee_cart]
            //         2) "кадровый учет" [fact_staff_stat]
            if (params.isDoLoadHrmIndicatorsCorp()) {
                loadHRMIndicatorsCorp(apps.getDataAreasForSubsystem(HRMCORP), params.getEndPeriod());
            }
            if (params.isDoLoadHrmIndicators()) {
                loadHRMIndicators(apps.getDataAreasForSubsystem(HRM), params.getEndPeriod());
            }
            //куб учет рабочего времени [fact_worktime]
            if (params.isDoLoadHrmIndicatorsAndWorkTime()) {
                loadHRMIndicatorsAndWorkTime(HRMIndicatorsDatesCorpUrl, apps.getDataAreasForSubsystem(HRMCORP), params.getEndPeriod());
                loadHRMIndicatorsAndWorkTime(HRMIndicatorsDatesUrl, apps.getDataAreasForSubsystem(HRM), params.getEndPeriod());
            }
            try {
                String endPeriod = StringUtils.isEmpty(params.getEndPeriod()) ? getLastMonthDayParam() : params.getEndPeriod();
                factDao.updateCubeStaffDetails(apps, endPeriod); //обновление куба "сведения по кадрам" [fact_staff_details]
            }
            catch (Exception e) {
                e.printStackTrace();
                String stack = e.toString() + Arrays.asList(e.getStackTrace()).toString();
                LOG.error(logger.logError(STAFF_DETAILS.name(), "updateCubeStaffDetails failed: " + stack));
            }
        }
        catch (Exception e) {
            //logged inside methods
        }
        loadIndicatorsStarted = false;
        logEnd("loadIndicators");
        logger.logEnd(GENERIC.name(), "loadIndicatorsInternal finished");
    }

    /*
        загрузка из 2х сервисов GetGenericIndicators в промежуточную [facts_generic_indicators]
     */
    protected void updateGenericIndicators( ApplicationInfo apps, String endPeriod ) throws IOException {

        logger.logStart(GENERIC.name(), "updateGenericIndicators started");

        String lastMonthDay = StringUtils.isEmpty(endPeriod) ? getLastMonthDayParam() : endPeriod;
        String url1 = genericIndicatorsZikgu + END_PARAM + lastMonthDay;
        String urlCorp = genericIndicatorsZikguCorp + END_PARAM + lastMonthDay;
        logger.log(GENERIC.name(), "zikGu url=" + url1 + ",\nCorpUrl=" + urlCorp);

        try {
            ListOfDataAreaOrgList facts = jsonLoader.loadGenericIndicatorsNew(url1);
            filterDataAreas(facts.dataAreas, apps.getDataAreasForSubsystem(HRM));
            factDao.storeGenericIndicatorsNew(facts);

            // для Zikgu Corp
            facts = jsonLoader.loadGenericIndicatorsNew(urlCorp);
            filterDataAreas(facts.dataAreas, apps.getDataAreasForSubsystem(HRMCORP));
            factDao.storeGenericIndicatorsNew(facts);
            logger.logEnd(GENERIC.name(), "updateGenericIndicators finished");
        }
        catch (Exception e) {
            e.printStackTrace();
            String stack = e.toString() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.logError(GENERIC.name(), "Error in updateGenericIndicators: " + e.getMessage() + stack), e);
            throw e;
        }
    }

    /*
        загрузка с 2х URL для куба Статистика Карточки сотрудника в [fact_employee_cart]
        + обновление куба "кадровый учет"
     */
    private void loadHRMIndicatorsCorp( Set<Integer> onlyAreas, String endPeriod ) throws Exception {

        logStart(logger.logStart(HRMCORP.name(), "loadHRMIndicatorsCorp endPeriod=" + endPeriod));
        String lastMonthDay = StringUtils.isEmpty(endPeriod) ? getLastMonthDayParam() : endPeriod;
        String urlCorp = HRMIndicatorsCorpUrl + END_PARAM + lastMonthDay;
        LOG.debug(logger.log(HRMCORP.name(), "load from CorpUrl=" + urlCorp));

        try {
            ListOfDataAreaEmpList facts = jsonLoader.loadHRMIndicators(urlCorp);
            filterDataAreas(facts.dataAreas, onlyAreas);
            HashMap<String, Employee> empGuids = factDao.getEmployeeMap(facts); // make map GUID -> employee
            if (!empGuids.isEmpty()) {
                factDao.updateEmployeeTable(empGuids, HRMCORP.name()); // сохранить данные сотрудников в таблицу
//                [fact_employee_cart]
                factDao.storeEmployeesStat(empGuids, HRMCORP.name());
                factDao.updateEmployeeStatTotals(); // totals для куба
                //обновление куба "дисциплина кадрового учета", его показатели зависят от storeEmployeesStat
                factDao.updateStaffStat(empGuids, onlyAreas, HRMCORP.name());
                // обновление куба "Структура заработной платы" [fact_salary]
                // и обновление куба [fact_position_salary]
                factDao.updateFactSalary(empGuids, HRMCORP.name());
                factDao.updateFactSalaryTotals(HRMCORP.name());
            } else {
                LOG.info(logger.log(HRMCORP.name(), "empty employee map from " + facts.getSummary() + ", dataAreas:" + onlyAreas));
            }
            logEnd(logger.logEnd(HRMCORP.name(), "loadHRMIndicatorsCorp finished"));
        }
        catch (Exception e) {
            e.printStackTrace();
            String stack = e.toString() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.log(HRMCORP.name(), "loadHRMIndicatorsCorp failed: " + stack));
            throw e;
        }
    }

    private void loadHRMIndicatorsAndWorkTime( String baseUrl, Set<Integer> onlyAreas, String endPeriod ) throws Exception {

        final String module = HRM_DATES.name();
        logStart(logger.log(module, "loadHRMIndicatorsAndWorkTime period End=" + endPeriod));
        String lastMonthDay = StringUtils.isEmpty(endPeriod) ? getLastMonthDayParam() : endPeriod;
        String urlMain = baseUrl.trim() + END_PARAM + lastMonthDay;
        logger.log(module, "url=" + urlMain);

        try {
            ListOfEmployeeDates facts = jsonLoader.loadHRMIndicatorsDates(urlMain);
            filterDataAreas(facts.dataAreas, onlyAreas);
            HashMap<String, EmployeeDates> empGuids = factDao.getEmployeeMap(facts); // make map GUID -> employee
            if (!empGuids.isEmpty()) {
                // обновление куба "учет рабочего времени" [fact_worktime]
                factDao.updateFactWorkTime(empGuids, module);
                factDao.updateFactWorktimeTotals(module);
            }

            logEnd(logger.logEnd(module, "loadHRMIndicatorsAndWorkTime finished"));
        }
        catch (Exception e) {
            e.printStackTrace();
            String stack = e.toString() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.log(module, "loadHRMIndicatorsAndWorkTime failed: " + stack));
            throw e;
        }

    }

    private void loadHRMIndicators( Set<Integer> onlyAreas, String endPeriod ) throws Exception {

        final String module = HRM.name();
        logStart(logger.logStart(module, "loadHRMIndicators endPeriod=" + endPeriod));
        String lastMonthDay = StringUtils.isEmpty(endPeriod) ? getLastMonthDayParam() : endPeriod;
        String urlMain = HRMIndicatorsUrl + END_PARAM + lastMonthDay;
        logger.log(module, "url=" + urlMain);

        try {
            ListOfDataAreaEmpList facts = jsonLoader.loadHRMIndicators(urlMain);
            filterDataAreas(facts.dataAreas, onlyAreas);
            HashMap<String, Employee> empGuids = factDao.getEmployeeMap(facts); // make map GUID -> employee
            if (!empGuids.isEmpty()) {
                factDao.updateEmployeeTable(empGuids, module); // сохранить данные сотрудников в таблицу
                //[fact_employee_cart]
                factDao.storeEmployeesStat(empGuids, module);
                factDao.updateEmployeeStatTotals(); // totals для куба
                //обновление куба "дисциплина кадрового учета", его показатели зависят от storeEmployeesStat
                factDao.updateStaffStat(empGuids, onlyAreas, module);
                factDao.updateFactStaffStatTotals();
                // обновление куба "Структура заработной платы" [fact_salary]
                factDao.updateFactSalary(empGuids, module);
                factDao.updateFactSalaryTotals(module);
            }

            logEnd(logger.logEnd(module, "loadHRMIndicators finished"));
        }
        catch (Exception e) {
            e.printStackTrace();
            String stack = e.toString() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.log(module, "loadHRMIndicatorsCorp failed: " + stack));
            throw e;
        }
    }

    public void loadDaily( LoaderFormDto params ) {

        if (loadDaily) {
            logStart(logger.log(DAILY.name(), "SKIP loadDaily - already started"));
            return;
        }
        if (runInThread) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    String text = "loadDaily IN THREAD " + Thread.currentThread().getName();
                    logStart(logger.logStart(DAILY.name(), text));
                    loadDailyInternal(params.getEndPeriod());
                    logEnd(logger.logEnd(DAILY.name(), text));
                }
            };
            new Thread(task).start();
        } else {
            loadDailyInternal(params.getEndPeriod());
        }
    }

    /**
     * загрузка ежедневных показаетлей
     *
     * @param endPeriod expected date in format YYYYMMDD, if empty, will be generated
     */
    private void loadDailyInternal( String endPeriod ) {
        loadDaily = true;
        updateCubeUserActivity(endPeriod);

        updateStatistics(endPeriod);

        // обновление куба fact_worktime                  по учету рабочего времени
        try {
            ApplicationInfo apps = loadDataAreas();
            loadHRMIndicatorsAndWorkTime(HRMIndicatorsDatesCorpUrl, apps.getDataAreasForSubsystem(HRMCORP), endPeriod);
            loadHRMIndicatorsAndWorkTime(HRMIndicatorsDatesUrl, apps.getDataAreasForSubsystem(HRM), endPeriod);
        }
        catch (Exception e) {
            e.printStackTrace();
            String stack = e.getMessage() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.logError(DAILY.name(), "loadHRMIndicatorsAndWorkTime ERROR: " + stack));
        }

        loadDaily = false;
    }

    public void updateStatistics( String endPeriod ) {

        try {
            logStart(logger.logStart(DAILY.name(), "updateStatistics started, endPeriod=" + endPeriod));
            String needDate = StringUtils.isEmpty(endPeriod) || !endPeriod.matches("^\\d{8}$") ? getTodayDateParam() : endPeriod;
            OrgStat statResult = jsonLoader.loadStatistics(statServiceUrl + "?begin=" + needDate + "&end=" + needDate);
            factDao.updateStatistics(statResult, needDate);
        }
        catch (Exception e) {
            String stack = e.getMessage() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.logError(DAILY.name(), "updateStatistics ERROR: " + stack));
        }

    }

    private void updateCubeUserActivity( String endPeriod ) {

        try {
            logStart(logger.logStart(DAILY.name(), "updateCubeUserActivity started, endPeriod=" + endPeriod));
            ApplicationInfo apps = loadDataAreas();//загрузка допустимых dataAreas
            if (StringUtils.isEmpty(endPeriod)) {
                endPeriod = getTodayDateParam();
            }
            String urlGen = genericIndicatorsZikgu + END_PARAM + endPeriod;
            String urlGenCorp = genericIndicatorsZikguCorp + END_PARAM + endPeriod;
            String urlBGU = factBuhUrlBGU + END_PARAM + endPeriod; // dataArea.end будет хранить дату конца периода)
            logger.log(DAILY.name(), "BGU url=" + urlBGU + ", zikGu url=" + urlGen + ",\nCorpUrl=" + urlGenCorp);

            //загрузка GenericIndicators из 2х источников
            ListOfDataAreaOrgList generic = jsonLoader.loadGenericIndicatorsNew(urlGen);
            ListOfDataAreaOrgList genericCorp = jsonLoader.loadGenericIndicatorsNew(urlGenCorp);
            filterDataAreas(generic.dataAreas, apps.getDataAreasForSubsystem(HRM)); //удалить лишние
            filterDataAreas(genericCorp.dataAreas, apps.getDataAreasForSubsystem(HRMCORP)); //удалить лишние
            generic.dataAreas.addAll(genericCorp.dataAreas); //объединить 2 списка

            //загрузка БГУ данных
            FactBuhList factBuhList = jsonLoader.parseFactBuhList(urlBGU);
            filterDataAreas(factBuhList.dataAreas, apps.getDataAreasForSubsystem(ACC));

            // сохранить во временной таблице
            factDao.storeDaily(factBuhList, generic, endPeriod);
            // из временной таблицы - в куб
            factDao.updateCubeUserActivity(endPeriod);
            //обновление Totals
            factDao.updateCubeUserActivityTotals();

            logEnd(logger.logEnd(DAILY.name(), "updateCubeUserActivity finished"));
        }
        catch (Exception e) {
            String stack = e.getMessage() + Arrays.asList(e.getStackTrace()).toString();
            LOG.error(logger.logError(DAILY.name(), "updateCubeUserActivity ERROR: " + stack));
        }
    }

    private void filterDataAreas( List dataAreas, Set<Integer> selectedIds ) {
        List toDel = new ArrayList();
        for (Object dataArea : dataAreas) {
            if (!selectedIds.contains(((DataArea) dataArea).id))
                toDel.add(dataArea);
        }
        dataAreas.removeAll(toDel);
    }

    private String getTodayDateParam() {

        return String.format("%1$tY%1$tm%1$td", Calendar.getInstance());// YYYYMMDD
    }

    public void loadFactBuh(String endPeriod) {
        if (loadAccountingStarted) {
            logStart(logger.logStart(ACCOUNTING.name(), "SKIP loadFactBuh - already started"));
            return;
        }

        if (runInThread) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    logStart(logger.logStart(ACCOUNTING.name(), "loadFactBuh IN THREAD " + Thread.currentThread().getName()));
                    loadFactBuhInternal(endPeriod);
                    logEnd(logger.logEnd(ACCOUNTING.name(), "loadFactBuh IN THREAD " + Thread.currentThread().getName()));
                }
            };
            new Thread(task).start();
        } else {
            loadFactBuhInternal(endPeriod);
        }
    }

    private void loadFactBuhInternal(String endPeriod) {

        loadAccountingStarted = true;
        try {
            String url = factBuhUrlBGU + END_PARAM + (StringUtils.isEmpty(endPeriod) ? getLastMonthDayParam() : endPeriod);
            logStart(logger.logStart(ACCOUNTING.name(), "loadFactBuhInternal BGU url=" + url));
            FactBuhList facts = jsonLoader.parseFactBuhList(url);
            factDao.storeFactBuh(facts, SubsystemEnum.BGU);

            factDao.storeBuhFactsForZikGu(); //для ЗИкГУ использовать данные GetGenericIndicators
            logEnd(logger.logStart(ACCOUNTING.name(),"loadFactBuhInternal"));
        } catch (Exception e) {
            LOG.error("loadFactBuhInternal ERROR" + e.getMessage());
        }
        loadAccountingStarted = false;
    }

    private String getLastMonthDayParam() {
//        if (true)
//            return "20180531";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDayOfMonthDate = String.format("%1$tY%1$tm%1$td", cal);// YYYYMMDD
        return lastDayOfMonthDate;
    }

    public void loadOrganizations() {

        logStart("loadOrganizations");
        try {
            for (int p = 5; p < 2458; ++p) {
                System.out.println("loadOrganizations p=" + p);
                OrganizationList facts = jsonLoader.loadOrganizations(organizationRegistryUrl + "?pageSize=100&pageNum=" + p);
                factDao.storeOrganizations(facts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo make runnable

        logEnd("loadOrganizations");
    }

    //todo переделать на загрузку инн из БД
    public void updateOrganizations() {

        long[] inns = new long[]{
                5902993324L,
                5902293851l,
                5902293467l,
                5902293308l,
                5902293298l,
                5902293202l,
                5902290931l,
                5903003918l,
                5903077035l,
                5902290917l,
                5902290723l,
                5902290586l,
                5902221423l,
                5902217177l,
                5902043202l,
                5904101918l,
                5902293192l,
                5906052769l,
                5905006199l,
                5908025545l,
                5947009089l,
                5902995233l,
                5904082630l,
                5903003097l,
                5904100431l,
                5916032419l,
                5904345865l,
                5902293185l,
                5902293107l,
                5908011750l,
                5902290258l,
                5902044157l,
                5902291290l,
                5903003643l,
                5902293812l,
                5902290434l,
                5907013191l,
                5902291090l,
                5905006520l,
                5902290882l,
                5948047591l,
                5908030009l,
                5918214069l,
                5904103489l,
                5906107961l,
                5905021310l,
                8107009755l,
                5902192934l,
                5902292738l,
                5902290709l,
                5904329415l,
                5906031511l,
                5917100510l,
                5902290441l,
                5907005112l
        };
        logStart("updateOrganizations");
        try {

            OrganizationList facts = new OrganizationList();
            facts.orgList = new ArrayList<>();
            for (long inn : inns) {
                System.out.println("updateOrganizations inn=" + inn);
                OrganizationList orgData = jsonLoader.loadOrganizations(organizationRegistryUrl + "?filterinn=" + inn);
                if (orgData == null || orgData.orgList.isEmpty()) {
                    System.out.println("updateOrganizations  skip empty org inn=" + inn);
                    continue;
                }
                facts.orgList.add(orgData.orgList.get(0));
            }
            factDao.updateOrganizations(facts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo make runnable

        logEnd("updateOrganizations");
    }


    private void logEnd(String name) {

        LOG.info("<-- FINISHED (" + name + ")");
    }

    private void logStart(String name) {

        LOG.info("--> STARTED (" + name + ")");
    }

    public void setHRMIndicatorsUrl(String HRMIndicatorsUrl) {

        this.HRMIndicatorsUrl = HRMIndicatorsUrl;
    }

    public void setRunInThread( boolean runInThread ) {
        this.runInThread = runInThread;
    }

    public boolean isRunInThread() {
        return runInThread;
    }
}


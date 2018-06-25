package ru.parma.mirs.service.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.parma.mirs.service.dao.CubeMeasureDAO;
import ru.parma.mirs.service.json.*;
import ru.parma.mirs.service.json.cube.Cell;
import ru.parma.mirs.service.json.cube.CubeResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class IndicatorService {

    @Autowired public CubeMeasureDAO cubeMeasureDAO;

    private static final Logger LOG = Logger.getLogger("IndicatorService.class");
    private static final String NAME_PROPERTY_VALUE = "raw";
    private static final Integer QUANTITY_RECORDS = 3;

    private final String QUERY_PANEL = "{\"queryModel\":{\"axes\":{\"FILTER\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"FILTER\",\"hierarchies\":[],\"nonEmpty\":false,\"aggregators\":[]},\"COLUMNS\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"COLUMNS\",\"hierarchies\":[{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"name\":\"[dimension_2].[dimension_2]\",\"caption\":\"Календарь\",\"dimension\":\"dimension_2\",\"levels\":{\"hierarchy_4\":{\"mdx\":null,\"filters\":[],\"name\":\"hierarchy_4\",\"caption\":\"Месяц\",\"selection\":{\"type\":\"INCLUSION\",\"members\":[listDate],\"parameterName\":null},\"aggregators\":[null],\"measureAggregators\":[]}},\"cmembers\":{}}],\"nonEmpty\":true,\"aggregators\":[]},\"ROWS\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"ROWS\",\"hierarchies\":[{\"mdx\":null,\"filters\":[],\"sortOrder\":\"BDESC\",\"sortEvaluationLiteral\":\"measure_sort\",\"hierarchizeMode\":null,\"name\":\"[dimension_20].[dimension_20]\",\"caption\":\"Организация\",\"dimension\":\"dimension_20\",\"levels\":{\"hierarchy_12\":{\"mdx\":null,\"filters\":[],\"name\":\"hierarchy_12\",\"caption\":\"ГРБС\",\"selection\":{\"type\":\"INCLUSION\",\"members\":[],\"parameterName\":null},\"aggregators\":[],\"measureAggregators\":[]},\"hierarchy_11\":{\"name\":\"hierarchy_11\"}},\"cmembers\":{}}],\"nonEmpty\":true,\"aggregators\":[\"sum\"]}},\"visualTotals\":false,\"visualTotalsPattern\":null,\"lowestLevelsOnly\":false,\"details\":{\"axis\":\"COLUMNS\",\"location\":\"BOTTOM\",\"measures\":[measure_blocks]},\"calculatedMeasures\":[],\"calculatedMembers\":[]},\"cube\":{\"uniqueName\":\"[guidCube].[guidCube].[guidCube].[guidCube]\",\"name\":\"guidCube\",\"connection\":\"guidCube\",\"catalog\":\"guidCube\",\"schema\":\"guidCube\",\"caption\":null,\"visible\":false},\"mdx\":\"WITH\\nSET [~COLUMNS] AS\\n    {[dimension_2].[dimension_2].[hierarchy_4].Members}\\nSET [~ROWS] AS\\n    {[dimension_20].[dimension_20].[hierarchy_12].Members}\\nSELECT\\nNON EMPTY CrossJoin([~COLUMNS], {measure_query}) ON COLUMNS,\\nNON EMPTY [~ROWS] ON ROWS\\nFROM [guidCube]\",\"name\":\"B688E31A-9FC4-65AA-9E32-BDF32F6E7DCC\",\"parameters\":{},\"plugins\":{},\"properties\":{\"saiku.olap.query.automatic_execution\":false,\"saiku.olap.query.nonempty\":true,\"saiku.olap.query.nonempty.rows\":true,\"saiku.olap.query.nonempty.columns\":true,\"saiku.ui.render.mode\":\"table\",\"saiku.olap.query.filter\":true,\"saiku.olap.result.formatter\":\"flattened\",\"org.saiku.query.explain\":true,\"saiku.olap.query.drillthrough\":true,\"org.saiku.connection.scenario\":false},\"metadata\":{},\"queryType\":\"OLAP\",\"type\":\"QUERYMODEL\"}";
    private final String QUERY_TABLE = "{\"queryModel\":{\"axes\":{\"FILTER\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"FILTER\",\"hierarchies\":[],\"nonEmpty\":false,\"aggregators\":[]},\"COLUMNS\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"COLUMNS\",\"hierarchies\":[{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"name\":\"[dimension_2].[dimension_2]\",\"caption\":\"Календарь\",\"dimension\":\"dimension_2\",\"levels\":{\"hierarchy_4\":{\"mdx\":null,\"filters\":[],\"name\":\"hierarchy_4\",\"caption\":\"Месяц\",\"selection\":{\"type\":\"INCLUSION\",\"members\":[listDate],\"parameterName\":null},\"aggregators\":[null],\"measureAggregators\":[]}},\"cmembers\":{}}],\"nonEmpty\":true,\"aggregators\":[]},\"ROWS\":{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,\"hierarchizeMode\":null,\"location\":\"ROWS\",\"hierarchies\":[{\"mdx\":null,\"filters\":[],\"sortOrder\":null,\"sortEvaluationLiteral\":null,                 \"hierarchizeMode\":null,\"name\":\"[dimension_20].[dimension_20]\",\"caption\":\"Организация\",\"dimension\":\"dimension_20\",\"levels\":{\"hierarchy_12\":{\"mdx\":null,\"filters\":[],\"name\":\"hierarchy_12\",\"caption\":\"ГРБС\",\"selection\":{\"type\":\"INCLUSION\",\"members\":[listGrbsOrganization],\"parameterName\":null},\"aggregators\":[],\"measureAggregators\":[]},\"hierarchy_11\":{\"name\":\"hierarchy_11\",\"selection\":{\"type\":\"INCLUSION\",\"members\":[listOrganization]}}},\"cmembers\":{}}],\"nonEmpty\":true,\"aggregators\":[\"sum\"]}},\"visualTotals\":false,\"visualTotalsPattern\":null,\"lowestLevelsOnly\":false,\"details\":{\"axis\":\"COLUMNS\",\"location\":\"BOTTOM\",\"measures\":[measure_blocks]},\"calculatedMeasures\":[],\"calculatedMembers\":[]},\"cube\":{\"uniqueName\":\"[guidCube].[guidCube].[guidCube].[guidCube]\",\"name\":\"guidCube\",\"connection\":\"guidCube\",\"catalog\":\"guidCube\",\"schema\":\"guidCube\",\"caption\":null,\"visible\":false},\"mdx\":\"WITH\\nSET [~COLUMNS] AS\\n    {[dimension_2].[dimension_2].[hierarchy_4].Members}\\nSET [~ROWS] AS\\n    {[dimension_20].[dimension_20].[hierarchy_12].Members}\\nSELECT\\nNON EMPTY CrossJoin([~COLUMNS], {measure_query}) ON COLUMNS,\\nNON EMPTY [~ROWS] ON ROWS\\nFROM [guidCube]\",\"name\":\"B688E31A-9FC4-65AA-9E32-BDF32F6E7DCC\",\"parameters\":{},\"plugins\":{},\"properties\":{\"saiku.olap.query.automatic_execution\":false,\"saiku.olap.query.nonempty\":true,\"saiku.olap.query.nonempty.rows\":true,\"saiku.olap.query.nonempty.columns\":true,\"saiku.ui.render.mode\":\"table\",\"saiku.olap.query.filter\":true,\"saiku.olap.result.formatter\":\"flattened\",\"org.saiku.query.explain\":true,\"saiku.olap.query.drillthrough\":true,\"org.saiku.connection.scenario\":false},\"metadata\":{},\"queryType\":\"OLAP\",\"type\":\"QUERYMODEL\"}";

    private final String NAME_REPLACE_GUID_CUBE = "guidCube";
    private final String NAME_REPLACE_MAESURE_SORT = "measure_sort";
    private final String NAME_REPLACE_MAESURE_BLOCKS = "measure_blocks";
    private final String NAME_REPLACE_MAESURE_QUERY = "measure_query";
    private final String NAME_REPLACE_TYPE_ORGANIZATION = "typeOrganization";
    private final String NAME_REPLACE_LIST_GRBS_ORGANIZATION = "listGrbsOrganization";
    private final String NAME_REPLACE_LIST_ORGANIZATION = "listOrganization";
    private final String NAME_REPLACE_LIST_DATE = "listDate";

    private final String DATE_CONDITION = "{\"uniqueName\":\"[dimension_2].[dimension_2].[year].[half].[quarter].[month]\"}";
    private final String GRBS_ORGANIZATION_CONDITION = "{\"uniqueName\":\"[dimension_20].[dimension_20].[%s]\"}";
    private final String ORGANIZATION_CONDITION = "{\"uniqueName\":\"[dimension_20].[dimension_20].[%s].[%s]\"}";
    private final String TYPE_ORGANIZATION_GRBS = "hierarchy_12";
    private final String TYPE_ORGANIZATION_INATITUTIONS = "hierarchy_11";

    private final String MEASURE_SORT = "[Measures].[measure_%s]";
    private final String MEASURE_BLOCK = "{\"name\":\"measure_%s\",\"uniqueName\":\"[Measures].[measure_%s]\",\"caption\":\"Количество операций созданных вручную\",\"type\":\"EXACT\",\"aggregators\":[\"sum_ROWS\"]}";
    private final String MEASURE_QUERY = "[Measures].[measure_%s]";

    private final String STRING_FIND_ID_MEASURE = "[Measures].[measure_";

    private final String URL_QUERY_TO_CUBE = "%s/saiku/rest/saiku/api/query/execute";
    private final String URL_LOGIN_TO_CUBE = "%s/saiku/rest/saiku/session";
    private final String URL_SAIKU;
    private final String LOGIN_TO_CUBE = "isadmin=true&language=ru&username=admin&password=admin";

    private final RestTemplate restTemplate;

    private final Map<Integer, Integer> mappingTypeMeasure;

    private String cookie;
    private String guidCube;

    public IndicatorService(String urlSaiku) {
        URL_SAIKU = urlSaiku;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        this.restTemplate = restTemplate;

        mappingTypeMeasure = new HashMap<>();
        mappingTypeMeasure.put(744, 1);
        mappingTypeMeasure.put(383, 2);
        mappingTypeMeasure.put(366, 3);
        mappingTypeMeasure.put(10001, 3);
        mappingTypeMeasure.put(362, 3);
        mappingTypeMeasure.put(360, 3);
        mappingTypeMeasure.put(364, 3);
        mappingTypeMeasure.put(365, 3);

    }

    public Panel getPanel(Integer idMeasure, LocalDate beginDate, LocalDate endDate, List<Integer> filterGrbs, List<String> filterOrganisation) throws Exception {
        //авторизуемся к кубу
        guidCube = checkAuthorizationCube();

        String grbsOrganizationCondition = makeGrbsCondition(filterGrbs);
        String organizationCondition = makeOrganizationCondition(filterOrganisation);
        return getInformationPanel(idMeasure, beginDate, endDate, grbsOrganizationCondition, organizationCondition);
    }

    public List<Panel> getPanels(RequestDto requestDto) {
        //авторизуемся к кубу
        guidCube = checkAuthorizationCube();

        String grbsOrganizationCondition = makeGrbsCondition(requestDto.getFilterGrbs());
        String organizationCondition = makeOrganizationCondition(requestDto.getFilterOrganisation());
        return requestDto.getMeasures().stream().map(element -> {
            try {
                return getInformationPanel(element.getIdMeasure(), element.getBeginDate(), element.getEndDate(), grbsOrganizationCondition, organizationCondition);
            } catch (Exception e) {
                LOG.info(e.getMessage());
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<Panel> getCubePanels(Integer idCube, LocalDate beginDate, LocalDate endDate, List<Integer> filterGrbs, List<String> filterOrganisation) throws Exception {
        //авторизуемся к кубу
        guidCube = checkAuthorizationCube();

        String grbsOrganizationCondition = makeGrbsCondition(filterGrbs);
        String organizationCondition = makeOrganizationCondition(filterOrganisation);

        return cubeMeasureDAO.getMeasureIdsFromCube(idCube).stream().map(elem -> {
            try {
                return getInformationPanel(elem.getIdMeasure(), beginDate, endDate, grbsOrganizationCondition, organizationCondition);
            } catch (Exception e) {
                LOG.info(e.getMessage());
            }
            return null;
        }).collect(Collectors.toList());
    }

    public Tables getTable(Integer idMeasure, LocalDate beginDate, LocalDate endDate, List<Integer> filterGrbs, List<String> filterOrganisation) throws Exception {
        //авторизуемся к кубу
        guidCube = checkAuthorizationCube();

        Tables tables = new Tables();
        //вычисляем период и подготавливает условие кубу для периода
        String dateCondition = makeDateAndCondition(beginDate, endDate, false);
        tables.setBeginDate(beginDate);
        tables.setEndDate(endDate);

        String grbsOrganizationCondition = makeGrbsCondition(filterGrbs);
        String organizationCondition = makeOrganizationCondition(filterOrganisation);

        List<Integer> idMeasures = new ArrayList<>();
        idMeasures.add(idMeasure);

        tables.setTables(idMeasures.stream().map(element -> {
            try {
                return getInformationTable(element, dateCondition, grbsOrganizationCondition, organizationCondition);
            } catch (Exception e) {
                LOG.info(e.getMessage());
            }
            return null;
        }).collect(Collectors.toList()));

        return tables;
    }

    public Table getOneTable(Integer idMeasure, LocalDate beginDate, LocalDate endDate, List<Integer> filterGrbs, List<String> filterOrganisation) throws Exception {
        guidCube = checkAuthorizationCube();
        String dateCondition = makeDateAndCondition(beginDate, endDate, false);
        String grbsOrganizationCondition = makeGrbsCondition(filterGrbs);
        String organizationCondition = makeOrganizationCondition(filterOrganisation);
        try {
            return getInformationTable(idMeasure, dateCondition, grbsOrganizationCondition, organizationCondition);
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        return null;
    }

    public Tables getTables(LocalDate beginDate, LocalDate endDate, List<Integer> filterGrbs, List<String> filterOrganisation) throws Exception {
        //авторизуемся к кубу
        guidCube = checkAuthorizationCube();

        Tables tables = new Tables();
        //вычисляем период и подготавливает условие кубу для периода
        String dateCondition = makeDateAndCondition(beginDate, endDate, false);
        tables.setBeginDate(beginDate);
        tables.setEndDate(endDate);

        String grbsOrganizationCondition = makeGrbsCondition(filterGrbs);
        String organizationCondition = makeOrganizationCondition(filterOrganisation);

        List<Table> tableList = new ArrayList<>();
        getHierarchy().stream().forEach(cube ->
            tableList.addAll(cube.getMeasures().stream().map(element -> {
                    Table table = null;
                    try {
                        table = getInformationTable(element.getIdMeasure(), dateCondition, grbsOrganizationCondition, organizationCondition);
                    } catch (Exception e) {
                        LOG.info(e.getMessage());
                    }
                    return table;
                }).collect(Collectors.toList())
            )
        );

        tables.setTables(tableList);

        return tables;
    }

    public List<CubeMeasures> getHierarchy() {
        HashMap<CubeMeasures, List<CubeMeasures.PairData>> map = new HashMap<>();

        cubeMeasureDAO.getCubeMeasures().stream().forEach(element -> {
            if (map.get(element) == null) map.put(element, new ArrayList<>());
            map.get(element).addAll(element.getMeasures());
        });

        return map.entrySet().stream().map(cubeMeasuresListEntry -> {
            CubeMeasures element = cubeMeasuresListEntry.getKey();
            element.setMeasures(cubeMeasuresListEntry.getValue());
            return element;
        }).collect(Collectors.toList());
    }

    private Panel getInformationPanel(Integer idMeasure, LocalDate beginDate, LocalDate endDate, String grbsOrganizationCondition, String organizationCondition) throws Exception {

        //проверяем наличие показателя
        List<CubeMeasure> cubeMeasures = cubeMeasureDAO.getMeasure(idMeasure);
        if (CollectionUtils.isEmpty(cubeMeasures)) throw new Exception();
        CubeMeasure cubeMeasure = cubeMeasures.iterator().next();

        Panel panel = new Panel();
        panel.setName(cubeMeasure.getName());
        panel.setIdMeasure(idMeasure);
        panel.setType(getTypeIndicator(cubeMeasure.getIdTypeMeasure()));

        //вычисляем период и подготавливает условие кубу для периода
        String dateCondition = makeDateAndCondition(beginDate, endDate, false);
        panel.setBeginDate(beginDate);
        panel.setEndDate(endDate);

        Float totalValue = 0f;
        Float subTotalValue = 0f;
        Float denominatorTotalValue = 0f;
        Float numeratorTotalValue = 0f;
        Float lastYearTotalValue = 0f;
        Integer denominatorIdMeasure = null;

        List<Integer> idMeasures = new ArrayList<>();
        idMeasures.add(idMeasure);

        if (panel.getType() == 1 && StringUtils.isNotBlank(cubeMeasure.getFormulaText())) {
            try {
                String formulaText = cubeMeasure.getFormulaText();
                Integer numeratorIdMeasure;

                Integer indexBetweenIdMeasure = formulaText.indexOf("/");
                if (indexBetweenIdMeasure > 0) {
                    String stringWithDenominatorIdMeasure = formulaText.substring(indexBetweenIdMeasure);
                    String stringWithNumeratorIdMeasure = formulaText.substring(indexBetweenIdMeasure - 30, indexBetweenIdMeasure);

                    Integer indexDenominatorIdMeasure = stringWithDenominatorIdMeasure.indexOf(STRING_FIND_ID_MEASURE) + STRING_FIND_ID_MEASURE.length();
                    String denominatorIdMeasureString = stringWithDenominatorIdMeasure.substring(indexDenominatorIdMeasure, indexDenominatorIdMeasure + 3).replaceAll("=|]|,|\\)", "");
                    denominatorIdMeasure = Integer.valueOf(denominatorIdMeasureString);

                    Integer indexNumeratorIdMeasure = stringWithNumeratorIdMeasure.indexOf(STRING_FIND_ID_MEASURE) + STRING_FIND_ID_MEASURE.length();
                    String numeratorIdMeasureString = stringWithNumeratorIdMeasure.substring(indexNumeratorIdMeasure, indexNumeratorIdMeasure + 3).replaceAll("=|]|\\)", "");
                    numeratorIdMeasure = Integer.valueOf(numeratorIdMeasureString);

                    if (denominatorIdMeasure != null && numeratorIdMeasure != null) {
                        idMeasures.add(denominatorIdMeasure);
                        idMeasures.add(numeratorIdMeasure);
                    }
                }
            } catch (NumberFormatException e) {
                LOG.info(String.format("%s - %s", cubeMeasure.getFormulaText(), e.getMessage()));
            }
        }

        CubeResponse response = doCubeQueryRequest(buildQuery(guidCube, idMeasures, dateCondition, grbsOrganizationCondition, organizationCondition));

        Table table = new Table();
        table.setType(panel.getType());
        fillTable(response, table, idMeasures.size());

        List<Panel.PairData> grbsList = new ArrayList<>();
        for (Table.Data parent : table.getParents()) {
            if (panel.getType() == 1) {
                denominatorTotalValue += parent.getSubValue1();
                numeratorTotalValue += parent.getSubValue2();
                subTotalValue += parent.getSubValue1();
            } else totalValue += parent.getValue();
            grbsList.add(new Panel.PairData(parent.getName(), parent.getValue()));
        }
        if (panel.getType() == 1) totalValue = numeratorTotalValue / denominatorTotalValue * 100;

        Collections.sort(grbsList, new Panel.PairData());
        if (grbsList.size() <= QUANTITY_RECORDS) panel.setTableDataGrbs(grbsList);
        else panel.setTableDataGrbs(grbsList.subList(0, 3));

        List<Panel.PairData> organizationList = new ArrayList<>();
        table.getParents().stream().forEach(parent -> organizationList.addAll(parent.getChildren().stream().map(child -> new Panel.PairData(child.getName(), child.getValue())).collect(Collectors.toList())));

        Collections.sort(organizationList, new Panel.PairData());
        if (organizationList.size() <= QUANTITY_RECORDS) panel.setTableDataOrganization(organizationList);
        else panel.setTableDataOrganization(organizationList.subList(0, 3));


        //вычисляем даты предыдущего периода и подготавливает условие кубу для периода
        dateCondition = makeDateAndCondition(beginDate, endDate, true);
        response = doCubeQueryRequest(buildQuery(guidCube, idMeasures, dateCondition, grbsOrganizationCondition, organizationCondition));
        table = new Table();
        table.setType(panel.getType());
        fillTable(response, table, idMeasures.size());

        for (Table.Data parent : table.getParents()) {
            if (panel.getType() == 1) {
                denominatorTotalValue += parent.getSubValue1();
                numeratorTotalValue += parent.getSubValue2();
            } else lastYearTotalValue += parent.getValue();
        }
        if (panel.getType() == 1) lastYearTotalValue = numeratorTotalValue / denominatorTotalValue * 100;

        if (totalValue != null && totalValue > 0 && lastYearTotalValue != null && lastYearTotalValue > 0) panel.setChangePercentValue(String.format("%.2f", totalValue / lastYearTotalValue * 100 - 100));

        panel.setValue(makeValueWithDescription(totalValue, cubeMeasure));
        if (panel.getType() == 1) panel.setSubValue(makeValueWithDescription(subTotalValue, denominatorIdMeasure));
        panel.setLastPeriodValue(makeValueWithDescription(lastYearTotalValue, idMeasure));

        return panel;

    }

    private Table getInformationTable(Integer idMeasure, String dateCondition, String grbsOrganizationCondition, String organizationCondition) throws Exception {

        //проверяем наличие показателя
        List<CubeMeasure> cubeMeasures = cubeMeasureDAO.getMeasure(idMeasure);
        if (CollectionUtils.isEmpty(cubeMeasures)) throw new Exception();
        CubeMeasure cubeMeasure = cubeMeasures.iterator().next();

        Table table = new Table();
        table.setName(cubeMeasure.getName());
        table.setNameValue(cubeMeasure.getName());
        table.setIdMeasure(idMeasure);
        table.setType(getTypeIndicator(cubeMeasure.getIdTypeMeasure()));

        List<Integer> idMeasures = new ArrayList<>();
        idMeasures.add(idMeasure);

        if (table.getType() == 1 && StringUtils.isNotBlank(cubeMeasure.getFormulaText())) {
            try {
                String formulaText = cubeMeasure.getFormulaText();
                Integer denominatorIdMeasure = null;
                Integer numeratorIdMeasure = null;

                Integer indexBetweenIdMeasure = formulaText.indexOf("/");
                if (indexBetweenIdMeasure > 0) {
                    String stringWithDenominatorIdMeasure = formulaText.substring(indexBetweenIdMeasure);
                    String stringWithNumeratorIdMeasure = formulaText.substring(indexBetweenIdMeasure - 30, indexBetweenIdMeasure);

                    Integer indexDenominatorIdMeasure = stringWithDenominatorIdMeasure.indexOf(STRING_FIND_ID_MEASURE) + STRING_FIND_ID_MEASURE.length();
                    String denominatorIdMeasureString = stringWithDenominatorIdMeasure.substring(indexDenominatorIdMeasure, indexDenominatorIdMeasure + 3).replaceAll("=|]|,|\\)", "");
                    denominatorIdMeasure = Integer.valueOf(denominatorIdMeasureString);

                    Integer indexNumeratorIdMeasure = stringWithNumeratorIdMeasure.indexOf(STRING_FIND_ID_MEASURE) + STRING_FIND_ID_MEASURE.length();
                    String numeratorIdMeasureString = stringWithNumeratorIdMeasure.substring(indexNumeratorIdMeasure, indexNumeratorIdMeasure + 3).replaceAll("=|]|\\)", "");
                    numeratorIdMeasure = Integer.valueOf(numeratorIdMeasureString);

                    if (denominatorIdMeasure != null && numeratorIdMeasure != null) {
                        idMeasures.add(denominatorIdMeasure);
                        idMeasures.add(numeratorIdMeasure);

                        cubeMeasures = cubeMeasureDAO.getMeasure(denominatorIdMeasure);
                        if (CollectionUtils.isEmpty(cubeMeasures)) throw new Exception();
                        table.setNameSubValue1(cubeMeasures.iterator().next().getName());

                        cubeMeasures = cubeMeasureDAO.getMeasure(numeratorIdMeasure);
                        if (CollectionUtils.isEmpty(cubeMeasures)) throw new Exception();
                        table.setNameSubValue2(cubeMeasures.iterator().next().getName());

                    }
                }
            } catch (NumberFormatException e) {
                LOG.info(String.format("%s - %s", cubeMeasure.getFormulaText(), e.getMessage()));
            }
        }

        CubeResponse response = doCubeQueryRequest(buildQuery(guidCube, idMeasures, dateCondition, grbsOrganizationCondition, organizationCondition));
        fillTable(response, table, idMeasures.size());

        return table;

    }

    private String checkAuthorizationCube() {
        String result = "";

        try {
//            if (cookie == null) cookie = doCubeLoginRequest();
            cookie = doCubeLoginRequest();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.COOKIE, cookie);

            ResponseEntity<String> response = restTemplate.exchange(String.format(URL_LOGIN_TO_CUBE, URL_SAIKU),
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    String.class);
            LOG.info(response.toString());
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                if (!response.getBody().contains("sessionid")) {
                    cookie = doCubeLoginRequest();
                    checkAuthorizationCube();
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    String responseBody = response.getBody();
                    HashMap<String, String> map = mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(responseBody, HashMap.class);
                    result = map.get("sessionid");
                }
            }

        } catch (RestClientException e) {
            LOG.info(e.getMessage());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String doCubeLoginRequest() {
        String result = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> httpEntity = new HttpEntity<>(LOGIN_TO_CUBE, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(String.format(URL_LOGIN_TO_CUBE, URL_SAIKU), httpEntity, String.class);
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                LOG.info("Can`t get data from Cube. HTTP status code: " + response.getStatusCodeValue());
            } else {
                LOG.info(response.getHeaders().get(HttpHeaders.SET_COOKIE).toString());
                String cookie = response.getHeaders().get(HttpHeaders.SET_COOKIE).iterator().next();
                result = cookie.substring(0, cookie.indexOf(";"));
            }
        } catch (RestClientException e) {
            LOG.info(e.getMessage());
        }

        return result;
    }

    private CubeResponse doCubeQueryRequest(String body) {
        CubeResponse result = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookie);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(String.format(URL_QUERY_TO_CUBE, URL_SAIKU), httpEntity, String.class);
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                LOG.info("Can`t get data from Cube. HTTP status code: " + response.getStatusCodeValue());
            } else {
                ObjectMapper mapper = new ObjectMapper();
                String responseBody = response.getBody();
                result = mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(responseBody, CubeResponse.class);
            }
        } catch (RestClientException e) {
            LOG.info(e.getMessage());
        } catch (JsonParseException e) {
            LOG.info(e.getMessage());
        } catch (JsonMappingException e) {
            LOG.info(e.getMessage());
        } catch (IOException e) {
            LOG.info(e.getMessage());
        }

        return result;
    }

    private String buildQuery(String guidCube, List<Integer> idMeasures, String typeOrganization, String listDate) {
        return QUERY_PANEL.
                replaceAll(NAME_REPLACE_GUID_CUBE, guidCube).
                replaceAll(NAME_REPLACE_MAESURE_SORT, idMeasures.stream().map(e -> String.format(MEASURE_SORT, e)).findFirst().get()).
                replaceAll(NAME_REPLACE_MAESURE_BLOCKS, idMeasures.stream().map(e -> String.format(MEASURE_BLOCK, e, e)).collect(Collectors.joining(", "))).
                replaceAll(NAME_REPLACE_MAESURE_QUERY, idMeasures.stream().map(e -> String.format(MEASURE_QUERY, e)).collect(Collectors.joining(", "))).
                replaceAll(NAME_REPLACE_TYPE_ORGANIZATION, typeOrganization).
                replaceAll(NAME_REPLACE_LIST_DATE, listDate);
    }

    private String buildQuery(String guidCube, List<Integer> idMeasures, String listDate, String listGrbsOrganization, String listOrganization) {

        return QUERY_TABLE.
                replaceAll(NAME_REPLACE_GUID_CUBE, guidCube).
                replaceAll(NAME_REPLACE_MAESURE_BLOCKS, idMeasures.stream().map(e -> String.format(MEASURE_BLOCK, e, e)).collect(Collectors.joining(", "))).
                replaceAll(NAME_REPLACE_MAESURE_QUERY, idMeasures.stream().map(e -> String.format(MEASURE_QUERY, e)).collect(Collectors.joining(", "))).
                replaceAll(NAME_REPLACE_LIST_DATE, listDate).
                replaceAll(NAME_REPLACE_LIST_GRBS_ORGANIZATION, listGrbsOrganization).
                replaceAll(NAME_REPLACE_LIST_ORGANIZATION, listOrganization)
                ;
    }

    private List<Panel.PairData> getIndicatorTable(CubeResponse cubeResponse, List<Panel.PairData> pairDatas) {
        if (cubeResponse.getCellset() == null) return pairDatas;
        for (List<Cell> element : cubeResponse.getCellset()) {
            Panel.PairData pairData = null;
            for (Cell cell : element) {
                if (Cell.Type.valueOf(cell.getType()) == Cell.Type.ROW_HEADER) {
                    pairData = new Panel.PairData();
                    pairData.setName(cell.getValue());
                    pairDatas.add(pairData);
                } else if (Cell.Type.valueOf(cell.getType()) == Cell.Type.DATA_CELL && cell.getProperties() != null && cell.getProperties().getProperty(NAME_PROPERTY_VALUE) != null && pairData != null) {
                    Float value = turnInto(cell.getProperties().getProperty(NAME_PROPERTY_VALUE));
                    if (pairData.getValue() == null) pairData.setValue(value);
                    else pairData.setValue(pairData.getValue() + value);

                }
            }
        }
        Collections.sort(pairDatas, new Panel.PairData());
        if (pairDatas.size() <= QUANTITY_RECORDS) return pairDatas;
        else return pairDatas.subList(0, 3);
    }

    private Float calculateIndicatorTotal(CubeResponse cubeResponse) {
        Float result = 0f;
        if (cubeResponse.getRowTotalsLists() == null) return result;
        for (List<CubeResponse.RowTotal> elements : cubeResponse.getRowTotalsLists()) {
            for (CubeResponse.RowTotal element : elements) {
                for (List<Cell> cells : element.getCells()) {
                    for (Cell cell : cells) {
                        if (Cell.Type.valueOf(cell.getType()) == Cell.Type.DATA_CELL && cell.getValue() != null) {
                            Float value = turnInto(cell.getValue());
                            result += value;
                        }
                    }
                    if (result > 0) return result;
                }
            }
        }

        return result;
    }

    private String makeDateAndCondition(LocalDate beginDate, LocalDate endDate, boolean lastPeriod) throws Exception {
        validateDateRange(beginDate, endDate);

        String result = "";
        if (beginDate == null && endDate == null) return result;

        Integer differentMonthes = 0;
        Integer differentDays = 0;
        if (lastPeriod) differentMonthes = endDate.getYear() * 12 + endDate.getMonthValue() - beginDate.getYear() * 12 - beginDate.getMonthValue() + 1;

        LocalDate startBeginDate = LocalDate.of(beginDate.getYear(), beginDate.getMonthValue(), 1).minusMonths(differentMonthes).minusDays(differentDays);
        LocalDate startEndDate = LocalDate.of(endDate.getYear(), endDate.getMonthValue(), 1).minusMonths(differentMonthes).minusDays(differentDays);

        List<String> dateConditions = new ArrayList<>();
        dateConditions.add(DATE_CONDITION.replaceAll("year", Integer.valueOf(startBeginDate.getYear()).toString()).replaceAll("half", calculateHalfYear(startBeginDate).toString()).replaceAll("quarter", calculateQuarter(startBeginDate).toString()).replaceAll("month", Integer.valueOf(startBeginDate.getMonthValue()).toString()));

        while ((!startBeginDate.equals(startEndDate))) {
            startBeginDate = startBeginDate.plusMonths(1);
            dateConditions.add(DATE_CONDITION.replaceAll("year", Integer.valueOf(startBeginDate.getYear()).toString()).replaceAll("half", calculateHalfYear(startBeginDate).toString()).replaceAll("quarter", calculateQuarter(startBeginDate).toString()).replaceAll("month", Integer.valueOf(startBeginDate.getMonthValue()).toString()));
        }

        return dateConditions.stream().collect(Collectors.joining(", "));

    }

    private String makeGrbsCondition(List<Integer> filterGrbs) {
        if (CollectionUtils.isEmpty(filterGrbs)) return "";
        else return filterGrbs.stream().map(element -> String.format(GRBS_ORGANIZATION_CONDITION, element)).collect(Collectors.joining(", "));
    }

    private String makeOrganizationCondition(List<String> filterOrganisation) {
        if (CollectionUtils.isEmpty(filterOrganisation)) return "";
        else return filterOrganisation.stream().map(element -> String.format(ORGANIZATION_CONDITION, element.substring(0, element.indexOf(".")), element.substring(element.indexOf(".") + 1))).collect(Collectors.joining(", "));
    }

    private void validateDateRange(LocalDate beginDate, LocalDate endDate) throws Exception {
        if (beginDate != null && endDate != null && beginDate.isAfter(endDate)) throw new Exception();
    }

    private Integer calculateQuarter(LocalDate localDate) {
        if (localDate != null) return (localDate.getMonthValue() - 1) / 3 + 1;
        else return 1;
    }

    private Integer calculateHalfYear(LocalDate localDate) {
        if (localDate != null) return localDate.getMonthValue() < 7 ? 1 : 2;
        else return 1;
    }

    private Float turnInto(String stringFloat) {
        Float result = 0f;
        if (StringUtils.isEmpty(stringFloat)) return result;
        else {
            try {
                result = Float.valueOf(stringFloat.replaceAll(",", ".").replaceAll(" ", "").replaceAll(" ", ""));
            } catch (NumberFormatException e) {
                LOG.info(e.getMessage());
            }
        }

        return result;
    }

    private Integer getTypeIndicator(Integer idTypeMeasure) {
        return mappingTypeMeasure.get(idTypeMeasure) != null ? mappingTypeMeasure.get(idTypeMeasure) : 4;
    }

    private String makeValueWithDescription(Float value, Integer idMeasure) {
        List<CubeMeasure> cubeMeasures = cubeMeasureDAO.getMeasure(idMeasure);
        return makeValueWithDescription(value, cubeMeasures.iterator().next());
    }

    private String makeValueWithDescription(Float value, CubeMeasure cubeMeasure) {
        return Math.round(value) + " " + getNumEnding(Math.round(value), cubeMeasure);
    }

    private String getNumEnding(Integer number, CubeMeasure cubeMeasure) {
        String result = "";

        List<String> declines = cubeMeasure.getDeclines();
        if (CollectionUtils.isEmpty(declines)) return cubeMeasure.getShortName();

        Integer number2 = number % 100;
        if (number2 >= 11 && number2 <= 19) {
            result = declines.get(0);
        }
        else {
            number2 = number2 % 10;
            switch (number2)
            {
                case (1): result = declines.get(0); break;
                case (2):
                case (3):
                case (4): result = declines.get(1); break;
                default: result = declines.get(2);
            }
        }
        return result;
    }

    private void fillTable(CubeResponse cubeResponse, Table table, Integer quantityMeasure) {
        if (cubeResponse.getCellset() == null) return;

        String oldGrbs = "";
        table.setParents(new ArrayList<>());
        Table.Data parent = new Table.Data();

        for (List<Cell> row : cubeResponse.getCellset()) {
            Iterator<Cell> rowIterator = row.iterator();
            Cell grbsCell = rowIterator.next();
            if (Cell.Type.valueOf(grbsCell.getType()) != Cell.Type.ROW_HEADER) continue;

            String grbs = grbsCell.getValue();
            if (!grbs.equals(oldGrbs) && cubeResponse.getCellset().indexOf(row) != 2) {
                if (table.getType() == 1 && quantityMeasure == 3) calculatePresent(parent);
                if (StringUtils.isEmpty(parent.getName())) parent.setName(oldGrbs);
                table.getParents().add(parent);

                parent = new Table.Data();
                parent.setName(grbs);
                parent.setChildren(new ArrayList<>());
            }

            Cell organizationCell = rowIterator.next();
            Table.Data child = new Table.Data();
            child.setName(organizationCell.getValue());

            int count = 0;
            while (rowIterator.hasNext()) {
                Cell currentCell = rowIterator.next();
                Float value = turnInto(currentCell.getProperties().getProperty(NAME_PROPERTY_VALUE));
                switch (count % quantityMeasure) {
                    case 0 :
                        if (child.getValue() == null) child.setValue(0f);
                        if (parent.getValue() == null) parent.setValue(0f);
                        child.setValue(child.getValue() + value);
                        parent.setValue(parent.getValue() + value);
                        break;
                    case 1 :
                        if (child.getSubValue1() == null) child.setSubValue1(0f);
                        if (parent.getSubValue1() == null) parent.setSubValue1(0f);
                        child.setSubValue1(child.getSubValue1() + value);
                        parent.setSubValue1(parent.getSubValue1() + value);
                        break;
                    case 2 :
                        if (child.getSubValue2() == null) child.setSubValue2(0f);
                        if (parent.getSubValue2() == null) parent.setSubValue2(0f);
                        child.setSubValue2(child.getSubValue2() + value);
                        parent.setSubValue2(parent.getSubValue2() + value);
                        break;
                }

                count++;
            }
            parent.getChildren().add(child);
            oldGrbs = grbs;
        }
        if (StringUtils.isEmpty(parent.getName())) parent.setName(oldGrbs);
        table.getParents().add(parent);
//        Collections.sort(table.getParents(), new Table.Data());

    }

    private void calculatePresent(Table.Data parent) {
        if (parent.getSubValue1() > 0 && parent.getSubValue2() > 0) parent.setValue(parent.getSubValue2() / parent.getSubValue1() * 100);
        if (!CollectionUtils.isEmpty(parent.getChildren())) parent.getChildren().stream().
                filter(element -> element.getSubValue1() > 0 && element.getSubValue2() > 0).
                forEach(element -> element.setValue(element.getSubValue2() / element.getSubValue1() * 100));
    }

}

package org.saiku.database.dao;

import mondrian.util.Format;
import org.saiku.database.dto.*;
import org.saiku.database.dto.report.Report;
import org.saiku.database.dto.report.ReportMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XmlConfigDAO extends JdbcDaoSupport {

    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private OlapUserDAO olapUserDAO;

    @Autowired
    private ReportDAO reportDAO;

    public void init() {
        parameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
    }

    public String getXml(String userName, String sessionId, List<Integer> measureIds) {
        if (userName==null || userName.length()==0) {
            userName = "admin";
        }
        String scheme = "<Schema name='"+sessionId+"' metamodelVersion=\"4.0\">\n" +
                getPhysicalSchema() +
                 getGroupSettings(sessionId, measureIds!=null?measureIds:getJdbcTemplate().queryForList("SELECT measure_id FROM cube_measure", Integer.class)) +
//                getPermissions(userName) +
                "</Schema>\n";

        return scheme;

//        URL url = Resources.getResource("foms.xml");
//        String scheme = null;
//        try {
//            scheme = Resources.toString(url, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return scheme.replaceAll("foms", sessionId);
    }

    private String getGroupSettings(String sessionId, List<Integer> measureIds) {
        List<Integer> cubeIds = getCubeIdsByMeasureIds(measureIds);
//        for (Integer cubeId : new ArrayList<>(cubeIds)) {
//            if (!getCubeIsVisible(cubeId)) {
//                cubeIds.remove(cubeId);
//            }
//        }
        List<Integer> dimIds = getCommonDimIdsByMeasureIds(measureIds);
        Integer minDtLevel = getMinDtLevel(cubeIds);
        String cube = getDimensions(minDtLevel);

        cube += "<Cube name='"+sessionId+"' visible='true' cache='true' enabled='true'>\n";
        cube += getCommonDimensionSources(dimIds);
        cube += getMeasureGroups(cubeIds, dimIds, measureIds);
        cube += "</Cube>\n";
        return cube;
    }

    private boolean getCubeIsVisible(Integer cubeId) {
        return getJdbcTemplate().queryForObject("SELECT is_visible FROM cubes WHERE cube_id = ?", Boolean.class, cubeId);
    }

    private String getMeasureGroups(List<Integer> cubeIds, final List<Integer> dimIds, final List<Integer> measureIds) {
        final List<Integer> allMeasureIds = getAllMeasureIds(measureIds);
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(cubeIds));
        return parameterJdbcTemplate.query("SELECT * FROM cubes WHERE cube_id IN (:list_id)", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String groups = "<MeasureGroups>\n";
                while (rs.next()) {
                    groups += "<MeasureGroup name='cube_"+rs.getString("cube_id")+"' table='"+rs.getString("table_name")+"' schema='"+rs.getString("schema_name")+"'>\n";
                    Integer cubeId = rs.getInt("cube_id");
                    groups += getForeignKeysByDimIdsAndCubeId(cubeId, dimIds);
                    groups += getMeasuresByCubeIdAndMeasureIds(cubeId, measureIds, allMeasureIds);
                    groups += "</MeasureGroup>\n";
                }
                groups += "</MeasureGroups>\n";
                groups += getCalculatedMembers(measureIds, allMeasureIds);
                return groups;
            }
        });
    }

    private List<Integer> getAllMeasureIds(List<Integer> measureIds) {
        String sql = "SELECT DISTINCT t.child_id FROM "+
                "(" +
                "SELECT m.measure_id, m.child_id\n" +
                "FROM CUBE_MEASURE_LINK m\n" +
                "WHERE m.measure_id IN (:list_id)\n" +
                "UNION ALL\n" +
                "SELECT m2.measure_id, m2.child_id\n" +
                "                  FROM  CUBE_MEASURE_LINK m2,\n" +
                "                    (SELECT m.measure_id, m.child_id\n" +
                "                     FROM CUBE_MEASURE_LINK m\n" +
                "                     WHERE m.measure_id IN (:list_id)\n" +
                "                    ) t\n" +
                "                  WHERE m2.measure_id=t.child_id"+
                ") AS t";

        List<Integer> calcMeasures = parameterJdbcTemplate.queryForList(sql,
                new MapSqlParameterSource().addValue("list_id", measureIds), Integer.class);
//       List<Integer> calcMeasures = parameterJdbcTemplate.queryForList("WITH TAB AS\n" +
//                "(\n" +
//                "SELECT m.measure_id, m.child_id\n" +
//                "  FROM dbo.CUBE_MEASURE_LINK m\n" +
//                "  WHERE m.measure_id IN (:list_id)\n" +
//                "UNION ALL\n" +
//                "SELECT m2.measure_id, m2.child_id\n" +
//                "  FROM TAB t, dbo.CUBE_MEASURE_LINK m2\n" +
//                "  WHERE m2.measure_id=t.child_id\n" +
//                ")\n" +
//                "SELECT DISTINCT t.child_id\n" +
//                "  FROM TAB t", new MapSqlParameterSource().addValue("list_id", measureIds), Integer.class);
        List<Integer> res = new ArrayList<>(measureIds);
        for (Integer calcMeasureId : calcMeasures) {
            if (!res.contains(calcMeasureId)) {
                res.add(calcMeasureId);
            }
        }
        return res;
    }

    private String getMeasuresByCubeIdAndMeasureIds(Integer cubeId, final List<Integer> measureIds, List<Integer> allMeasureIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(allMeasureIds))
                .addValue("cube_id", cubeId);
        return parameterJdbcTemplate.query("SELECT cm.*, cmd.* " +
                "FROM cube_measure cm " +
                "JOIN cube_measure_metod cmd on cmd.metod_id = cm.metod_id " +
                "WHERE cm.cube_id = :cube_id AND cm.measure_id IN (:list_id) AND cm.metod_id < 7", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String groups = "<Measures>\n";
                while (rs.next()) {
                    Integer measureId = rs.getInt("measure_id");
                    String format = rs.getString("format_value");
                    groups += " <Measure name='measure_"+rs.getInt("measure_id")+"' caption='"+rs.getString("m_name")+"' column='"+rs.getString("m_column")+"' " +
                            "aggregator='"+rs.getString("metod_key")+"' visible='"+(measureIds.contains(measureId) && rs.getBoolean("is_visible"))+"' " +
                            (format==null?"":"formatString='"+format+"'") + " />\n";
                }
                groups += "</Measures>\n";
                return groups;
            }
        });
    }

    private String getCalculatedMembers(final List<Integer> measureIds, final List<Integer> allMeasureIds) {
//        List<Integer> calcMeasures = parameterJdbcTemplate.queryForList("SELECT child_id FROM cube_measure_link WHERE measure_id IN (:list_id)", new MapSqlParameterSource().addValue("list_id", measureIds), Integer.class);
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(allMeasureIds));
//                .addValue("calc_list_id", new HashSet<>(calcMeasures));
        return parameterJdbcTemplate.query("SELECT cm.* " +
                "FROM cube_measure cm " +
                "WHERE cm.metod_id > 6 AND cm.measure_id IN (:list_id)", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String groups = "<CalculatedMembers>\n";
                while (rs.next()) {
                    String formula;
                    String format = rs.getString("format_value");
                    Integer measureId = rs.getInt("measure_id");
                    if (rs.getInt("metod_id")==8)  {
                        formula = rs.getString("formula_text");
                    } else {
                        String name = getJdbcTemplate().queryForObject("SELECT measure_id FROM cube_measure WHERE measure_id = (SELECT child_id FROM cube_measure_link WHERE measure_id = ?)", String.class, measureId);
                        name = "measure_" + name;
//                        formula =
//                                "          CASE [dimension_2].[dimension_2].CurrentMember.Level.Name\n" +
//                                "          WHEN 'hierarchy_4' THEN Sum([dimension_2].[dimension_2].CurrentMember.Parent.Parent.Parent.Children.Item(0).Children.Item(0).Children.Item(0) : [dimension_2].[dimension_2].CurrentMember,[Measures].["+name+"])\n" +
//                                "          WHEN 'hierarchy_3' THEN Sum([dimension_2].[dimension_2].CurrentMember.Parent.Parent.Children.Item(0).Children.Item(0) : [dimension_2].[dimension_2].CurrentMember,[Measures].["+name+"])\n" +
//                                "          ELSE Sum([dimension_2].[dimension_2].CurrentMember.Children, [Measures].["+name+"])\n" +
//                                "          END\n";
                        formula = "SUM(YTD(),[Measures].["+name+"])";
                    }
                    groups += "      <CalculatedMember name='measure_"+rs.getInt("measure_id")+"' caption='"+rs.getString("m_name")+"' dimension='Measures' visible='"+(measureIds.contains(measureId) && rs.getBoolean("is_visible"))+"'>\n" +
                            "        <Formula>\n" +
                            formula +
                            "        </Formula>\n" +
                            (format==null?"":"<CalculatedMemberProperty name='FORMAT_STRING' value='"+format+"'/>\n") +
                            "      </CalculatedMember>\n";
                }
                groups += "</CalculatedMembers>\n";
                return groups;
            }
        });
    }

    private List<Hierarchy> getHierarchiesByDimId(Integer dimId) {
        return getJdbcTemplate().query("SELECT * FROM dimension_level WHERE dimension_id = ?", new RowMapper<Hierarchy>() {
            @Override
            public Hierarchy mapRow(ResultSet rs, int rowNum) throws SQLException {
                Hierarchy hierarchy = new Hierarchy();
                hierarchy.setId(rs.getInt("level_id"));
                hierarchy.setName(rs.getString("level_name"));
                hierarchy.setKeyColumn(rs.getString("field_key"));
                hierarchy.setNameColumn(rs.getString("field_value"));
                return hierarchy;
            }
        }, dimId);
    }

    private String getDimensions(final Integer minDtLevel) {
        return getJdbcTemplate().query("SELECT * FROM dimensions", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "";
                while (rs.next()) {
                    if (rs.getInt("dim_type_id")==1) {
                        res += "<Dimension table='dim"+rs.getInt("dimension_id")+"' key='dimension_" + rs.getInt("dimension_id") + "' name='dimension_"+rs.getInt("dimension_id")+"' caption='" + rs.getString("d_name") + "'>\n" +
                                "    <Attributes>\n" +
                                "      <Attribute name='dimension_"+rs.getInt("dimension_id")+"' caption='" + rs.getString("d_name") + "' column='" + rs.getString("field_name") + "'>\n" +
                                "        <Key>\n" +
                                "          <Column name='" + rs.getString("field_id") + "'/>\n" +
                                "        </Key>\n" +
                                "        <Name>\n" +
                                "          <Column name='" + rs.getString("field_id") + "'/>\n" +
                                "        </Name>\n" +
                                "        <Caption>\n" +
                                "          <Column name='" + rs.getString("field_name") + "'/>\n" +
                                "        </Caption>\n" +
                                "        <OrderBy>\n" +
                                "          <Column name='ord'/>\n" +
                                "        </OrderBy>\n" +
                                "      </Attribute>\n" +
                                "    </Attributes>\n" +
                                "</Dimension>\n";
                    } else {
                        if (rs.getInt("dim_type_id")==2 && minDtLevel != null) {
                            res += "<Dimension name='dimension_2' caption='Календарь' table='spr_date' type='TIME' key='dt'>\n" +
                                    "    <Attributes>\n" +
                                    "      <Attribute caption='Год' name='hierarchy_1' keyColumn='dt_year' levelType='TimeYears' hasHierarchy='false'/>\n" +
                                    "      <Attribute caption='Квартал' name='hierarchy_3' levelType='TimeQuarters' hasHierarchy='false'>\n" +
                                    "        <Key>\n" +
                                    "          <Column name='dt_year'/>\n" +
                                    "          <Column name='dt_quarter'/>\n" +
                                    "        </Key>\n" +
                                    "        <Name>\n" +
                                    "          <Column name='dt_quarter'/>\n" +
                                    "        </Name>\n" +
                                    "      </Attribute>\n" +
                                    "      <Attribute caption='Полугодие' name='hierarchy_2' levelType='TimeHalfYear' hasHierarchy='false'>\n" +
                                    "        <Key>\n" +
                                    "          <Column name='dt_year'/>\n" +
                                    "          <Column name='dt_half_year'/>\n" +
                                    "        </Key>\n" +
                                    "        <Name>\n" +
                                    "          <Column name='dt_half_year'/>\n" +
                                    "        </Name>\n" +
                                    "      </Attribute>\n" +
                                    "      <Attribute name='Номер месяца' levelType='TimeMonths' hasHierarchy='false'>\n" +
                                    "        <Key>\n" +
                                    "          <Column name='dt_year'/>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "        </Key>\n" +
                                    "        <Name>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "        </Name>\n" +
                                    "      </Attribute>\n" +
                                    "      <Attribute caption='День' name='hierarchy_5' levelType='TimeDays' hasHierarchy='false'>\n" +
                                    "        <Key>\n" +
                                    "          <Column name='dt_year'/>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "          <Column name='dt_day'/>\n" +
                                    "        </Key>\n" +
                                    "        <Name>\n" +
                                    "          <Column name='dt_day'/>\n" +
                                    "        </Name>\n" +
                                    "      </Attribute>\n" +
                                    "      <Attribute caption='Месяц' name='hierarchy_4' hasHierarchy='false'>\n" +
                                    "        <Key>\n" +
                                    "          <Column name='dt_year'/>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "        </Key>\n" +
                                    "        <Caption>\n" +
                                    "          <Column name='dt_month_name'/>\n" +
                                    "        </Caption>\n" +
                                    "        <Name>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "        </Name>\n" +
                                    "        <OrderBy>\n" +
                                    "          <Column name='dt_month'/>\n" +
                                    "        </OrderBy>\n" +
                                    "      </Attribute>\n" +
                                    "      <Attribute name='Date' keyColumn='dt_value' hasHierarchy='false'/>\n" +
                                    "      <Attribute name='dt' keyColumn='date_id' hasHierarchy='false'/>\n" +
                                    "    </Attributes>\n";
                            res += "    <Hierarchies>\n" +
                                    "    <Hierarchy name='dimension_2' caption='Календарь' hasAll='true'>\n";
                            if (minDtLevel>0) {
                                res += "    <Level attribute='hierarchy_1' caption='Год'/>\n";
                            }
                            if (minDtLevel>1) {
                                res += "    <Level attribute='hierarchy_2' caption='Полугодие'/>\n";
                            }
                            if (minDtLevel>2) {
                                res += "    <Level attribute='hierarchy_3' caption='Квартал'/>\n";
                            }
                            if (minDtLevel>3) {
                                res += "    <Level attribute='hierarchy_4' caption='Месяц'/>\n";
                            }
                            if (minDtLevel>4) {
                                res += "    <Level attribute='hierarchy_5' caption='День'/>\n";
                            }
                            res += "    </Hierarchy>\n" +
                                    "    </Hierarchies>\n" +
                                    "</Dimension>\n";
                        } else if (rs.getInt("dim_type_id")!=2) {
                            List<Hierarchy> hierarchyList = getHierarchiesByDimId(rs.getInt("dimension_id"));
                            res += "<Dimension table='"+rs.getString("table_name").split("\\.")[1]+"' key='hierarchy_"+hierarchyList.get(hierarchyList.size()-1).getId()+"' name='dimension_"+rs.getInt("dimension_id")+"' caption='" + rs.getString("d_name") + "'>\n" +
                                    "    <Attributes>\n";
                            for (Hierarchy hierarchy : hierarchyList) {
                                res += "      <Attribute name='hierarchy_"+hierarchy.getId()+"' captionColumn='"+hierarchy.getNameColumn()+"' keyColumn='"+hierarchy.getKeyColumn()+"' nameColumn='"+hierarchy.getKeyColumn()+"' hasHierarchy='false'/>\n";
                            }
                            res += "    </Attributes>\n" +
                                    "<Hierarchies>\n" +
                                    "      <Hierarchy name='dimension_"+rs.getInt("dimension_id")+"' caption='"+rs.getString("d_name")+"' hasAll='true'>\n";
                            for (Hierarchy hierarchy : hierarchyList) {
                                res += "        <Level attribute='hierarchy_"+hierarchy.getId()+"' caption='"+hierarchy.getName()+"'/>\n";
                            }
                            res += "    </Hierarchy>\n" +
                                    "    </Hierarchies>\n" +
                                    "</Dimension>\n";
                        }
                    }
                }
                return res;
            }
        });
    }

    private String getPhysicalSchema() {
        String schema = "<PhysicalSchema>\n";
        schema += getCubeTables() + getQueries();
        schema += "</PhysicalSchema>\n";
        return schema;
    }

    private String getQueries() {
        return getJdbcTemplate().query("SELECT * FROM dimensions", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                StringBuilder res = new StringBuilder();
                while (rs.next()) {
                    if (rs.getInt("dim_type_id")==1) {
                        String where = rs.getString("where_text");
                        res.append("<Query alias='dim"+rs.getInt("dimension_id")+"'>\n" +
                                "      <ExpressionView>\n" +
                                "        <SQL dialect=\"postgresql\"><![CDATA[\n" +
//                                "           select \""+rs.getString("field_id")+"\", CAST ("+rs.getString("field_name")+" AS VARCHAR(MAX)) AS "+rs.getString("field_name")+" FROM " + rs.getString("table_name") + " " + (where!=null?"WHERE "+where+"\n":"\n") +
                                "           select \""+rs.getString("field_id")+"\", "+rs.getString("field_name")+", "+rs.getString("field_order")+" AS ord FROM " + rs.getString("table_name") + " " + (where!=null?"WHERE "+where+"\n":"\n") +
                                "          ]]></SQL>\n" +
                                "      </ExpressionView>\n" +
                                "    </Query>\n");
                    } else {
                        if (rs.getInt("dim_type_id")==4) {
                            String[] table = rs.getString("table_name").split("\\.");
                            if (!res.toString().contains(table[1])) {
                                res.append("<Table schema='" + table[0] + "' name='" + table[1] + "'/>\n");
                            }

                        }
                    }
                }
                return res.toString();
            }
        });
    }

    private String getCubeTables() {
        return getJdbcTemplate().query("SELECT * FROM cubes", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "";
                while (rs.next()) {
                    res += "<Table schema='"+rs.getString("schema_name")+"' name='"+rs.getString("table_name")+"'/>\n";
                }
                res += "<Table schema='public' name='spr_date'/>\n";
                return res;
            }
        });
    }

    private String getCommonDimensionSources(List<Integer> dimIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(dimIds));
        return parameterJdbcTemplate.query("SELECT * FROM dimensions d\n" +
                "WHERE d.dimension_id IN (:list_id)", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                StringBuilder sources = new StringBuilder("<Dimensions>\n");
                while (rs.next()) {
                    sources.append("    <Dimension source='dimension_"+rs.getInt("dimension_id")+"'/>\n");
                }
                sources.append("</Dimensions>\n");
                return sources.toString();
            }
        });
    }

    private String getAllDimensionSources() {
        return parameterJdbcTemplate.query("SELECT * FROM dimensions", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                StringBuilder sources = new StringBuilder("<Dimensions>\n");
                while (rs.next()) {
                    sources.append("    <Dimension source='"+rs.getString("d_name")+"'/>\n");
                }
                sources.append("</Dimensions>\n");
                return sources.toString();
            }
        });
    }

    private String getForeignKeysByDimIdsAndCubeId(final Integer cubeId, final List<Integer> dimIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(dimIds))
                .addValue("cube_id", cubeId);

        return parameterJdbcTemplate.query("SELECT d.*, cd.* FROM CUBE_DIMENSION cd\n" +
                "JOIN DIMENSIONS d ON d.dimension_id = cd.dimension_id\n" +
                "WHERE cd.cube_id = :cube_id AND d.dimension_id IN (:list_id)" +
                "ORDER BY d_order", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "<DimensionLinks>\n";
                while (rs.next()) {
                    res += "    <ForeignKeyLink dimension='dimension_"+rs.getInt("dimension_id")+"' foreignKeyColumn='"+rs.getString("d_column")+"'/>\n";
                }
                res += getNoLinksByCubeIdAndDimIds(cubeId, dimIds);
                res += "</DimensionLinks>\n";
                return res;
            }
        });
    }

    private String getAllForeignKeysByCubeId(final Integer cubeId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("cube_id", cubeId);
        return parameterJdbcTemplate.query("SELECT d.*, cd.* FROM CUBE_DIMENSION cd\n" +
                "JOIN DIMENSIONS d ON d.dimension_id = cd.dimension_id\n" +
                "WHERE cd.cube_id = :cube_id\n" +
                "ORDER BY d_order", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "<DimensionLinks>\n";
                while (rs.next()) {
                    res += "    <ForeignKeyLink dimension='"+rs.getString("d_name")+"' foreignKeyColumn='"+rs.getString("d_column")+"'/>\n";
                }
                res += getNoLinksByCubeId(cubeId);
                res += "</DimensionLinks>\n";
                return res;
            }
        });
    }

    private String getNoLinksByCubeId(Integer cubeId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("cube_id", cubeId);

        return parameterJdbcTemplate.query("SELECT * FROM dimensions\n" +
                "WHERE dimension_id NOT IN (SELECT dimension_id FROM cube_dimension WHERE cube_id = :cube_id)", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "";
                while (rs.next()) {
//                    res += "    <NoLink dimension='dimension_"+rs.getInt("dimension_id")+"'/>\n";
                    res += "    <ForeignKeyLink dimension='dimension_"+rs.getInt("dimension_id")+"' foreignKeyColumn='undefined_id'/>\n";
                }
                return res;
            }
        });
    }

    private String getNoLinksByCubeIdAndDimIds(Integer cubeId, final List<Integer> dimIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("cube_id", cubeId)
                .addValue("list_id", new HashSet<>(dimIds));

        return parameterJdbcTemplate.query("SELECT * FROM dimensions\n" +
                "WHERE dimension_id NOT IN (SELECT dimension_id FROM cube_dimension WHERE cube_id = :cube_id) AND dimension_id IN (:list_id)", source, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "";
                while (rs.next()) {
//                    res += "    <NoLink dimension='dimension_"+rs.getInt("dimension_id")+"'/>\n";
                    res += "    <ForeignKeyLink dimension='dimension_"+rs.getInt("dimension_id")+"' foreignKeyColumn='undefined_id'/>\n";
                }
                return res;
            }
        });
    }

    public List<Integer> getCommonDimIdsByMeasureIds(List<Integer> measureIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(measureIds));
        return parameterJdbcTemplate.queryForList(
                "SELECT DISTINCT(dimension_id)\n" +
                        "FROM cube_dimension " +
                        "WHERE cube_id IN (SELECT cube_id FROM cube_measure WHERE measure_id IN (:list_id))"
                , source, Integer.class);
    }

    private List<Integer> getAllDimIds() {
        return getJdbcTemplate().queryForList("SELECT dimension_id FROM dimensions", Integer.class);
    }

    private List<Integer> getCubeIdsByMeasureIds(List<Integer> measureIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(getAllMeasureIds(measureIds)));
        return parameterJdbcTemplate.queryForList("SELECT DISTINCT(cube_id) " +
                "FROM cube_measure " +
                "WHERE measure_id IN (:list_id)", source, Integer.class);
    }

    private Integer getMinDtLevel(List<Integer> cubeIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(cubeIds));
        return parameterJdbcTemplate.queryForObject("SELECT MAX(dt_level_id) AS level " +
                "FROM cube_dimension " +
                "WHERE dimension_id = 2 AND cube_id IN (:list_id)", source, Integer.class);
    }

    public List<FactGroup> getAllFacts(String userName) {
        Integer userId = olapUserDAO.getUserIdByUsername(userName);
        String sql = "SELECT * FROM cubes WHERE is_visible = true";
        if (userId != null && olapUserDAO.checkFoms(userId)) {
            sql += " AND cube_id in (4,5,10)";
        }
        return getJdbcTemplate().query(sql,
                new RowMapper<FactGroup>() {
                    @Override
                    public FactGroup mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
                        FactGroup factGroup = new FactGroup(resultSet.getString("cube_name"));
                        List<Fact> factList = getJdbcTemplate().query(
                                "SELECT * FROM cube_measure cm \n" +
                                        "LEFT JOIN CUBE_MEASURE_UNIT cmu ON cmu.unit_id = cm.unit_id \n" +
                                        "WHERE cm.cube_id = ? AND cm.is_visible = true \n" +
                                        "ORDER BY m_order", new RowMapper<Fact>() {
                                    @Override
                                    public Fact mapRow(ResultSet rs, int rowNum) throws SQLException {
                                        Fact fact = new Fact(rs.getString("m_name"));
                                        fact.setId(rs.getInt("measure_id"));
                                        Date updateDate = rs.getDate("last_date");
                                        String strDate = (updateDate != null ? new SimpleDateFormat("dd.MM.yyyy").format(updateDate) : "");
                                        fact.setUpdateDate(strDate);
                                        Integer depth = rs.getInt("bit_depth");
                                        Double value = rs.getDouble("last_value");
                                        String formatExp = rs.getString("format_value");
                                        String strValue = String.format(Locale.forLanguageTag("ru-RU"), "%."+depth+"f", value);;
                                        if (formatExp!=null) {
                                            strValue = new Format(formatExp,Locale.forLanguageTag("ru-RU")).format(value);
                                        }
                                        fact.setValue(strValue);
                                        fact.setUnitId(rs.getInt("unit_id"));
                                        if (rs.wasNull()) {
                                            fact.setUnitId(null);
                                        }
                                        fact.setUnitName(rs.getString("unit_name"));
                                        fact.setUnitNameShort(rs.getString("unit_name_short"));
                                        return fact;
                                    }
                                }, resultSet.getInt("cube_id"));
                        factGroup.setFacts(factList);
                        Integer minYear = getJdbcTemplate().query("SELECT value_min_year FROM cube_dimension WHERE cube_id =? AND dimension_id = 2", new ResultSetExtractor<Integer>() {
                            @Override
                            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                                if (rs.next()) {
                                    return rs.getInt("value_min_year");
                                } else {
                                    return null;
                                }
                            }
                        }, resultSet.getInt("cube_id"));
                        factGroup.setMinYear(minYear);
                        return factGroup;
                    }
                });
    }

    public List<FactGroup> getFactsByGroupId(Integer groupId, String userName) {
        Integer userId = olapUserDAO.getUserIdByUsername(userName);
        Integer groupTypeId = (groupId.equals(0)?0:getJdbcTemplate().queryForObject("SELECT group_type_id FROM groups WHERE group_id = ?", Integer.class, groupId));
        switch (groupTypeId) {
            case 0 : {
                FactGroup factGroup = new FactGroup("Без группировки");
                String sql = "SELECT cube_id FROM cubes WHERE is_visible = true";
                if (userId != null && olapUserDAO.checkFoms(userId)) {
                    sql += " AND cube_id in (4,5,10)";
                }
                List<Integer> cubeIds = getJdbcTemplate().queryForList(sql, Integer.class);
                List<Integer> measureIds = parameterJdbcTemplate.queryForList("SELECT measure_id FROM cube_measure WHERE is_visible = true AND cube_id IN (:list_id)",
                        new MapSqlParameterSource().addValue("list_id",  new HashSet<>(cubeIds)), Integer.class);
                List<Fact> factList = getFactsByMeasureIds(measureIds, "m_name");
                factGroup.setFacts(factList);
                Integer minYear = parameterJdbcTemplate.query("SELECT value_min_year FROM cube_dimension WHERE cube_id IN (:list_id) AND dimension_id = 2",
                        new MapSqlParameterSource().addValue("list_id", new HashSet<>(cubeIds)),
                        new ResultSetExtractor<Integer>() {
                            @Override
                            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                                if (rs.next()) {
                                    return rs.getInt("value_min_year");
                                } else {
                                    return null;
                                }
                            }
                        });
                factGroup.setMinYear(minYear);
                List<FactGroup> factGroupList = new ArrayList<>();
                factGroupList.add(factGroup);
                return factGroupList;
            }
            case 1 : {
                return getAllFacts(userName);
            }
            case 2 : {
                String sql1 = "SELECT cube_id FROM cubes WHERE is_visible = true";
                String sql2 = "SELECT measure_id FROM cube_measure WHERE unit_id = :unit_id AND is_visible = true AND cube_id IN (:list_id)";
                if (userId != null && olapUserDAO.checkFoms(userId)) {
                    sql1 += " AND cube_id in (4,5,10)";
                    sql2 += " AND cube_id in (4,5,10)";
                }
                List<Integer> cubeIds = getJdbcTemplate().queryForList(sql1, Integer.class);
                String finalSql = sql2;
                return parameterJdbcTemplate.query(
                        "SELECT DISTINCT(cm.unit_id), cmu.* " +
                                "FROM cube_measure cm \n" +
                                "JOIN CUBE_MEASURE_UNIT cmu ON cmu.unit_id = cm.unit_id\n" +
                                "WHERE cm.is_visible = true AND cm.cube_id IN (:list_id) \n" +
                                "ORDER BY cmu.unit_name",
                        new MapSqlParameterSource().addValue("list_id", new HashSet<>(cubeIds)),
                        new RowMapper<FactGroup>() {
                            @Override
                            public FactGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                                FactGroup factGroup = new FactGroup(rs.getString("unit_name"));
                                List<Integer> measureIds = parameterJdbcTemplate.queryForList(finalSql, new MapSqlParameterSource().addValue("list_id", new HashSet<>(cubeIds)).addValue("unit_id",rs.getInt("unit_id")), Integer.class);
                                factGroup.setFacts(getFactsByMeasureIds(measureIds, "m_name"));
                                return factGroup;
                            }
                        });
            }
            case 3 : {
                String sql1 = "SELECT * FROM cubes " +
                        "WHERE cube_id IN (SELECT cube_id FROM groups_cube WHERE group_value_id = ?) AND is_visible = true";
                String sql2 = "SELECT measure_id FROM cube_measure WHERE cube_id = ? AND is_visible = true";
                if (userId != null && olapUserDAO.checkFoms(userId)) {
                    sql1 += " AND cube_id in (4,5,10)";
                    sql2 += " AND cube_id in (4,5,10)";
                }
                String finalSql1 = sql1;
                String finalSql2 = sql2;
                return getJdbcTemplate().query("SELECT * FROM groups_value WHERE group_id = ?", new RowMapper<FactGroup>() {
                    @Override
                    public FactGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        FactGroup factGroup = new FactGroup(rs.getString("group_value"));
                        factGroup.setFactGroups(getJdbcTemplate().query(
                                finalSql1,
                                new RowMapper<FactGroup>() {
                                    @Override
                                    public FactGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                                        FactGroup factGroupIn = new FactGroup(rs.getString("cube_name"));
                                        List<Integer> measureIds = getJdbcTemplate().queryForList(finalSql2, Integer.class, rs.getInt("cube_id"));
                                        factGroupIn.setFacts(getFactsByMeasureIds(measureIds, "m_order"));
                                        return factGroupIn;
                                    }
                                }, rs.getInt("group_value_id")));
                        return factGroup;
                    }
                }, groupId);
            }
            case 4 : {
                String sql1 = "SELECT cube_id FROM cubes WHERE is_visible = true";
                String sql = "SELECT measure_id FROM groups_measure WHERE group_value_id = :group_value_id AND measure_id IN (SELECT measure_id FROM cube_measure WHERE cube_id IN(:list_id))";
                if (userId != null && olapUserDAO.checkFoms(userId)) {
                    sql1 += " AND cube_id in (4,5,10)";
                    sql += " AND measure_id IN (SELECT measure_id FROM cube_measure WHERE cube_id in (4,5,10))";
                }
                List<Integer> cubeIds = getJdbcTemplate().queryForList(sql1, Integer.class);
                String finalSql = sql;
                return getJdbcTemplate().query("SELECT * FROM groups_value WHERE group_id = ?", new RowMapper<FactGroup>() {
                    @Override
                    public FactGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        FactGroup factGroup = new FactGroup(rs.getString("group_value"));
                        List<Integer> measureIds = parameterJdbcTemplate.queryForList(finalSql, new MapSqlParameterSource().addValue("list_id", new HashSet<>(cubeIds)).addValue("group_value_id", rs.getInt("group_value_id")), Integer.class);
                        factGroup.setFacts(measureIds.size()>0?getFactsByMeasureIds(measureIds, "m_name"):new ArrayList<>());
                        return factGroup;
                    }
                }, groupId);
            }
        }
        return null;
    }

    public List<Fact> getFactsByMeasureIds(List<Integer> measureIds, String order) {
        return parameterJdbcTemplate.query(
                "SELECT * FROM cube_measure cm \n" +
                        "LEFT JOIN CUBE_MEASURE_UNIT cmu ON cmu.unit_id = cm.unit_id \n" +
                        "WHERE cm.measure_id IN (:list_id) AND cm.is_visible = true \n" +
                        "ORDER BY " + order, new MapSqlParameterSource().addValue("list_id", new HashSet<>(measureIds)),
                new RowMapper<Fact>() {
                    @Override
                    public Fact mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Fact fact = new Fact(rs.getString("m_name"));
                        fact.setId(rs.getInt("measure_id"));
                        Date updateDate = rs.getDate("last_date");
                        String strDate = (updateDate != null ? new SimpleDateFormat("dd.MM.yyyy").format(updateDate) : "");
                        fact.setUpdateDate(strDate);
                        Integer depth = rs.getInt("bit_depth");
                        Double value = rs.getDouble("last_value");
                        String formatExp = rs.getString("format_value");
                        String strValue = String.format(Locale.forLanguageTag("ru-RU"), "%."+depth+"f", value);;
                        if (formatExp!=null) {
                            strValue = new Format(formatExp,Locale.forLanguageTag("ru-RU")).format(value);
                        }
                        fact.setValue(strValue);
                        fact.setUnitId(rs.getInt("unit_id"));
                        if (rs.wasNull()) {
                            fact.setUnitId(null);
                        }
                        fact.setUnitName(rs.getString("unit_name"));
                        fact.setUnitNameShort(rs.getString("unit_name_short"));
                        return fact;
                    }
                });
    }

//    public List<CubeGroup> getCubeGroups() {
//        return getJdbcTemplate().query("SELECT * FROM cube_group", new RowMapper<CubeGroup>() {
//            @Override
//            public CubeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
//                CubeGroup cubeGroup = new CubeGroup();
//                cubeGroup.setName(rs.getString("group_name"));
//                cubeGroup.setFactGroups(getFacts(rs.getInt("group_id")));
//                return cubeGroup;
//            }
//        });
//    }

    public List<Unit> getAllUnits() {
        return getJdbcTemplate().query("SELECT * FROM cube_measure_unit", new RowMapper<Unit>() {
            @Override
            public Unit mapRow(ResultSet rs, int rowNum) throws SQLException {
                Unit unit = new Unit();
                unit.setUnitId(rs.getInt("unit_id"));
                unit.setUnitGroupId(rs.getInt("unit_group_id"));
                unit.setUnitName(rs.getString("unit_name"));
                unit.setUnitNameShort(rs.getString("unit_name_short"));
                unit.setUnitKey(rs.getString("unit_key"));
                return unit;
            }
        });
    }

    public List<Dimension> getDimensionsByMeasureIds(List<Integer> measureIds) {
        List<Integer> dimIdList = getCommonDimIdsByMeasureIds(measureIds);
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(dimIdList));
        return parameterJdbcTemplate.query("SELECT * FROM dimensions WHERE dimension_id IN (:list_id)", source, new RowMapper<Dimension>() {
            @Override
            public Dimension mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dimension dimension = new Dimension();
                dimension.setName(rs.getString("d_name"));
                dimension.setDimId(rs.getInt("dimension_id"));
                return dimension;
            }
        });
    }

    public CubesDimensions getCubesDimensionsByMeasureIds(List<Integer> measureIds) {
        CubesDimensions cubesDimensions = new CubesDimensions();
        cubesDimensions.setDimensionList(getDimensionsByMeasureIds(measureIds));
        Integer minDtLevel = getMinDtLevel(getCubeIdsByMeasureIds(measureIds));
        List<DateLevel> dtLevelList = getJdbcTemplate().query("SELECT * FROM cube_dimension_dt_level WHERE dt_level_id <= ?", new RowMapper<DateLevel>() {
            @Override
            public DateLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DateLevel(rs.getInt("dt_level_id"), rs.getString("level_name"));
            }
        }, minDtLevel);
        cubesDimensions.setDateLevelList(dtLevelList);
        return cubesDimensions;
    }

    public String getDimensionHeader(Integer dimId, final Boolean isColumn) {
        return getJdbcTemplate().query("SELECT d_name, d_key FROM dimensions WHERE dimension_id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = isColumn ? "[~COLUMNS_" : "[~ROWS_";
                if (rs.next()) {
                    String name = rs.getString("d_name");
                    String key = rs.getString("d_key");
                    res += name + "_" + key +"]";
                }
                return res;
            }
        }, dimId);
    }

    public String getDimensionName(Integer dimId, final Boolean isColumn) {
        return getJdbcTemplate().query("SELECT d_name, d_key FROM dimensions WHERE dimension_id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = isColumn ? "[~COLUMNS_" : "[~ROWS_";
                if (rs.next()) {
                    String name = rs.getString("d_name");
                    String key = rs.getString("d_key");
                    res += name + "_" + key +"] AS " + "{[" + name + "].[" + rs.getString("d_key") + "].[" + rs.getString("d_key") + "].Members}\n";
                }
                return res;
            }
        }, dimId);
    }

    public String getMeasureName(Integer measureId) {
        return getJdbcTemplate().query("SELECT m_name FROM cube_measure WHERE measure_id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String res = "[Measures].[";
                if (rs.next()) {
                    res += rs.getString("m_name") + "]";
                }
                return res;
            }
        }, measureId);
    }

    public List<Measure> getMeasures(List<Integer> measureIds) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("list_id", new HashSet<>(measureIds));
        return parameterJdbcTemplate.query("SELECT * FROM CUBE_MEASURE WHERE measure_id IN (:list_id)", source, new RowMapper<Measure>() {
            @Override
            public Measure mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Measure(rs.getInt("measure_id"), rs.getString("m_name"), null);
            }
        });
    }

    public String getPermissions(String userName) {
        String[] users = {"admin", "test1", "test2", "test3", "foms", "foms2"};
        String permissions = "";
        for (String user : users) {
            permissions += "<Role name='ROLE_"+(user.equals("admin")?"ADMIN":"USER_" + user.toUpperCase())+"'>\n" +
                    "    <SchemaGrant access='"+((user.equals(userName))?"all":"none")+"'>\n" +
                    "    </SchemaGrant>\n" +
                    "  </Role>";
        }
        return permissions;
    }

    public List<Group> getAllGroups() {
        List<Group> groups = getJdbcTemplate().query(
                "SELECT * FROM groups g \n" +
                        "JOIN groups_type gt ON gt.group_type_id = g.group_type_id \n" +
                        "WHERE g.is_visible = true", new RowMapper<Group>() {
                    @Override
                    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Group group = new Group();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        return group;
                    }
                });
        Group group = new Group();
        group.setGroupId(0);
        group.setGroupName("Без группировки");
        groups.add(group);
        return groups;
    }

    @Transactional
    public String getSchemeByReportId(Integer reportId, String userName, String sessionId) {
        Report report = reportDAO.getReportById(userName, reportId);
        List<ReportMeasure> reportMeasures = report.getReportMeasures();
        ArrayList<Integer> measureIds = new ArrayList<>();
        for (ReportMeasure reportMeasure : reportMeasures) {
            measureIds.add(reportMeasure.getMeasureId());
        }
        return getXml(userName, sessionId, measureIds);
    }
}

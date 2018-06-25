package ru.parma.mirs.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.parma.mirs.service.json.CubeMeasure;
import ru.parma.mirs.service.json.CubeMeasures;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CubeMeasureDAO extends JdbcDaoSupport {

    @Autowired
    public CubeMeasureDAO(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public List<CubeMeasure> getMeasure(Integer idMeasure) {
        return getJdbcTemplate().query("SELECT a1.m_name, a1.unit_id, a1.formula_text, a3.unit_name_short, a2.p1, a2.p2, a2.p3 FROM cube_measure a1 left join measure_decline a2 ON a1.unit_id = a2.unit_id left join cube_measure_unit a3 ON a1.unit_id = a3.unit_id WHERE a1.measure_id = ?", (rs, rowNum) -> {
            final CubeMeasure cubeMeasure = new CubeMeasure();
            cubeMeasure.setName(rs.getString("m_name"));
            cubeMeasure.setIdTypeMeasure(rs.getInt("unit_id"));
            cubeMeasure.setFormulaText(rs.getString("formula_text"));
            cubeMeasure.setShortName(rs.getString("unit_name_short"));

            if (!StringUtils.isEmpty(rs.getString("p1")) || !StringUtils.isEmpty(rs.getString("p2")) || !StringUtils.isEmpty(rs.getString("p3"))) {
                List<String> declines = new ArrayList<>();
                declines.add(rs.getString("p1"));
                declines.add(rs.getString("p2"));
                declines.add(rs.getString("p3"));

                cubeMeasure.setDeclines(declines);
            }

            return cubeMeasure;
        }, idMeasure);
    }

    public List<CubeMeasures> getCubeMeasures() {
        return getJdbcTemplate().query("SELECT a2.cube_id, a2.cube_name, a1.measure_id, a1.m_name FROM cube_measure a1 INNER JOIN cubes a2 ON a1.cube_id = a2.cube_id", (rs, rowNum) -> {
            final CubeMeasures cubeMeasures = new CubeMeasures();
            cubeMeasures.setIdCube(rs.getInt("cube_id"));
            cubeMeasures.setNameCube(rs.getString("cube_name"));
            CubeMeasures.PairData measure = new CubeMeasures.PairData();
            measure.setIdMeasure(rs.getInt("measure_id"));
            measure.setNameMeasure(rs.getString("m_name"));
            List<CubeMeasures.PairData> pairDataList = new ArrayList<>();
            pairDataList.add(measure);
            cubeMeasures.setMeasures(pairDataList);

            return cubeMeasures;
        });
    }

    public List<CubeMeasures.PairData> getMeasureIdsFromCube(Integer idCube) {
        return getJdbcTemplate().query("SELECT a1.measure_id FROM cube_measure a1 WHERE a1.cube_id = ?",
                (rs, rowNum) -> {
                    final CubeMeasures.PairData measure = new CubeMeasures.PairData();
                    measure.setIdMeasure(rs.getInt("measure_id"));
                    return measure;
                },
                idCube);
    }


}

package org.saiku.service.olap;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.saiku.database.dao.XmlConfigDAO;
import org.saiku.database.dto.CubesDimensions;
import org.saiku.database.dto.Dimension;
import org.saiku.database.dto.Measure;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SchemeService {

    @Autowired
    private XmlConfigDAO xmlConfigDAO;

    private OlapDiscoverService olapDiscoverService;
    public void setOlapDiscoverService(OlapDiscoverService os) {
        this.olapDiscoverService = os;
    }

    public CellSet getCellSet(CubesDimensions cubesDimensions) throws Exception {
        OlapConnection con = olapDiscoverService.getNativeConnection("foms");
        OlapStatement stmt = con.createStatement();
        CellSet cs = stmt.executeOlapQuery(createMDX(cubesDimensions));
        return cs;
    }

    private String createMDX(CubesDimensions cubesDimensions) {
        String mdx = "WITH\n";
        List<Dimension> dimensionList = cubesDimensions.getDimensionList();
        List<Dimension> dimensionRows = new ArrayList<>();
        List<Dimension> dimensionColumns = new ArrayList<>();
        for (Dimension dimension : dimensionList) {
            mdx += "SET " +  xmlConfigDAO.getDimensionName(dimension.getDimId(), dimension.getIsColumn());
            if (dimension.getIsColumn()) {
                dimensionColumns.add(dimension);
            } else {
                dimensionRows.add(dimension);
            }
        }
        String measures = "{";
        List<Measure> measuresList = cubesDimensions.getMeasures();
        List<Integer> measureIds = new ArrayList<>();
        for (Measure measure : measuresList) {
            measureIds.add(measure.getId());
        }
        for (Integer measureId : measureIds) {
            measures += xmlConfigDAO.getMeasureName(measureId) + ", ";
        }
        measures = measures.substring(0, measures.length()-2) + "}";
        mdx += "SELECT\n";
        if (dimensionColumns.size()==0) {
            mdx += "NON EMPTY " + measures + " ON COLUMNS,\n" ;
        } else if (dimensionColumns.size()==1) {
            mdx += "NON EMPTY CrossJoin([~COLUMNS], " + measures + ") ON COLUMNS,\n";
        } else if (dimensionColumns.size()>1) {
            mdx += "NON EMPTY CrossJoin(NonEmptyCrossJoin(";
            for (Dimension dimensionColumn : dimensionColumns) {
                mdx += xmlConfigDAO.getDimensionHeader(dimensionColumn.getDimId(), dimensionColumn.getIsColumn()) + ", ";
            }
            mdx = mdx.substring(0, mdx.length()-2);
            mdx += ", " + measures + "ON COLUMNS,\n";
        }
        if (dimensionRows.size()==1) {
            Dimension dimensionRow = dimensionRows.get(0);
            mdx += "NON EMPTY " + xmlConfigDAO.getDimensionHeader(dimensionRow.getDimId(), dimensionRow.getIsColumn()) + " ON ROWS\n" ;
        } else {
            mdx += "NON EMPTY NonEmptyCrossJoin(";
            for (Dimension dimensionRow : dimensionRows) {
                mdx += xmlConfigDAO.getDimensionHeader(dimensionRow.getDimId(), dimensionRow.getIsColumn()) + ", ";
            }
            mdx = mdx.substring(0, mdx.length()-2);
            mdx +=") ON ROWS\n";
        }
        mdx += "FROM [Тест]";
        return mdx;
    }

//    private static Cell[] convert(AbstractBaseCell[] acells, Cell.Type headertype) {
//        Cell[]  cells = new Cell[acells.length];
//        for (int i = 0; i < acells.length; i++) {
//            cells[i] = convert(acells[i], headertype);
//        }
//        return cells;
//    }

//    private List<Cell[]> format(CellSet cs) {
//        ICellSetFormatter cf = cff.forName("");
//        CellDataSet cellSet = OlapResultSetUtil.cellSet2Matrix(cs, cf);
//        QueryResult qr = RestUtil.convert(cs)
//    }
}

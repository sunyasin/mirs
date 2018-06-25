
package ru.parma.mirs.service.json.cube;

import java.util.List;

public class CubeResponse {

    private List<List<Cell>> cellset = null;

    private List<List<RowTotal>> rowTotalsLists;

    public List<List<Cell>> getCellset() {
        return cellset;
    }

    public void setCellset(List<List<Cell>> cellset) {
        this.cellset = cellset;
    }

    public List<List<RowTotal>> getRowTotalsLists() {
        return rowTotalsLists;
    }

    public void setRowTotalsLists(List<List<RowTotal>> rowTotalsLists) {
        this.rowTotalsLists = rowTotalsLists;
    }

    public static class RowTotal {
        List<List<Cell>> cells = null;

        public List<List<Cell>> getCells() {
            return cells;
        }

        public void setCells(List<List<Cell>> cells) {
            this.cells = cells;
        }
    }
}

package org.saiku.service.util.export;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.query2.ThinHierarchy;
import org.saiku.olap.util.SaikuProperties;
import org.saiku.olap.util.formatter.ICellSetFormatter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by kalinin on 13.10.2017.
 */
public class OtsExporter {

    @SneakyThrows
    public static byte[] exportOts(CellDataSet table,
                                   ICellSetFormatter formatter,
                                   List<ThinHierarchy> filters,
                                   String reportName) {
        return FormatConvereter.build("ots", table, formatter, filters, reportName);
    }
}

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
 * Created by kalinin on 15.11.2017.
 */
public class FormatConvereter {

    @SneakyThrows
    public static byte[] build(String format, CellDataSet table,
                                   ICellSetFormatter formatter,
                                   List<ThinHierarchy> filters, String reportName) {
        UUID uuid = UUID.randomUUID();
        String[] values = uuid.toString().split("-");
        String tmpCtg = (Long.decode("0x" + values[0]) % 32) + "";
        String tmpSybCtg = (Long.decode("0x" + values[1]) % 32) + "";
        String pathTofile = System.getProperty("java.io.tmpdir") + "/tmpexport/"
                + tmpCtg + "/" + tmpSybCtg;

        String filename = uuid + ".xlsx";
        String filenameNew = uuid + "." + format;
        String formatNew = format;

        File file = new File(pathTofile + "/" + filename);
        file.mkdir();
        byte[] excelDoc = ExcelExporter.exportExcel(table, formatter, filters, reportName);
        FileUtils.writeByteArrayToFile(file, excelDoc);

        String python = SaikuProperties.webPython;
        String unoconv = SaikuProperties.webUnoconv;

        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            process = rt.exec(new String[]{python, unoconv, "--format=" + formatNew, pathTofile + "/" + filename});
            byte[] bytes = IOUtils.toByteArray(process.getErrorStream());
            process.destroy();
            if(bytes.length > 0) {
                throw new IllegalArgumentException(new String(bytes));
            }
        } catch (IOException e) {
            if(process != null) {
                process.destroy();
            }
        }
        File fileOds = new File(pathTofile + "/" + filenameNew);
        return FileUtils.readFileToByteArray(fileOds);
    }
}

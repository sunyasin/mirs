package ru.parma.mirs.loader.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import ru.parma.mirs.loader.model.LogRecord;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class LoaderLogger extends JdbcDaoSupport {

    // типы сообщений в логе
    public enum MsgType {
        COMMON(0),  //обычное
        START(1),   //начало загрузки модуля
        END(2),     //конец загрузки
        ERROR(-1);  // общая ошибка

        private int code;

        MsgType( int code ) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    // логируемые модули
    public enum LogModule {
        GENERIC,    // для GenericInicators
        HRM,        // для HRM inicators
        HRMCORP,    // для HRM inicators CORP
        ACCOUNTING, // для AccountingInfo
        STAFF_DETAILS, // для куба "сведения по кадрам" [fact_staff_details]
        DAILY, // для куба "сведения по кадрам" [fact_staff_details]
        USER_ACTIVITY, // для куба "активность поль-телей" [fact_user_activity]
        HRM_DATES,
    }

    @Autowired
    public LoaderLogger( DataSource dataSource ) {
        super();
        setDataSource(dataSource);
    }


    public List<LogRecord> getLog( String moduleName, LocalDate date ) {
        String module = StringUtils.isEmpty(moduleName) ? "" : "module = '" + moduleName + "' AND ";
        String datetime = date == null ? new Date().toString() : date.toString();
        List<LogRecord> result = new ArrayList<>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(
                "select datetime, msg_type, msg, module from loader_log where " + module + " datetime >=  to_date(?,'YYYY-MM-DD') order by datetime", datetime);
        for (Map row : rows) {
            LogRecord item = new LogRecord();
            item.dateTime = (Date) row.get("datetime");
            item.module = (String) row.get("module");
            item.msg = (String) row.get("msg");
            item.msgType = (Integer) row.get("msg_type");
            result.add(item);
        }
        return result;
    }

    public String logError( String moduleName, String message ) {
        return log(moduleName, message, MsgType.ERROR);
    }

    public String logStart( String moduleName, String message ) {
        return log(moduleName, message == null ? "START" : message, MsgType.START);
    }

    public String logEnd( String moduleName, String message ) {
        return log(moduleName, message == null ? "END" : message, MsgType.END);
    }

    public String log( String moduleName, String message ) {
        return log(moduleName, message, MsgType.COMMON);
    }

    public String log( String moduleName, String message, MsgType msgType ) {
        Date date = new Date();
        getJdbcTemplate().update("INSERT INTO loader_log " +
                "(datetime, msg, module, msg_type) VALUES " +
                "(?,        ?,    ?,       ?)", date, message, moduleName, msgType.getCode());
        return message;
    }
}

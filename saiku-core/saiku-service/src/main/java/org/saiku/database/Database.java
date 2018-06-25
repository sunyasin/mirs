package org.saiku.database;

import org.saiku.database.dao.XmlConfigDAO;
import org.saiku.datasources.datasource.SaikuDatasource;
import org.saiku.service.datasource.IDatasourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Properties;


/**
 * Created by bugg on 01/05/14.
 */
public class Database {

    @Autowired
    ServletContext servletContext;

    @Autowired
    private XmlConfigDAO xmlConfigDAO;

    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private IDatasourceManager dsm;

    public void setDatasourceManager(IDatasourceManager dsm) {
        this.dsm = dsm;
    }

    public IDatasourceManager getDsm() {
        return dsm;
    }

    public void addSchema(String userName, String sessionId) {
        String schema = xmlConfigDAO.getXml(userName, sessionId, null);
        try {
            dsm.addSchema(schema, "/datasources/" + sessionId + ".xml", sessionId);
            try {
                Properties p = new Properties();
                p.setProperty("driver", "mondrian.olap4j.MondrianOlap4jDriver");
                p.setProperty("location", "jdbc:mondrian:Jdbc=" + jdbcUrl +
                        ";Catalog=mondrian:///datasources/"+sessionId+".xml;JdbcDrivers=org.postgresql.Driver;Locale=ru_RU");
                p.setProperty("username", jdbcUser);
                p.setProperty("password", jdbcPassword);
                p.setProperty("id", "4432dd20-fcae-11e3-a3ac-0800200c9a66");
                p.setProperty("security.enabled", "true");
                p.setProperty("security.type", "one2one");

                SaikuDatasource ds = new SaikuDatasource(sessionId, SaikuDatasource.Type.OLAP, p);
                dsm.addDatasource(ds);
            } catch (Exception e) {
                log.error("Can't add data source to repo", e);
            }
        } catch (Exception e) {
            log.error("Can't add schema file to repo", e);
        }
    }

    public List<String> getUsers() throws java.sql.SQLException
    {
        //Stub for EE.
        return null;
    }

    public void addUsers(List<String> l) throws java.sql.SQLException
    {
        //Stub for EE.
    }

}

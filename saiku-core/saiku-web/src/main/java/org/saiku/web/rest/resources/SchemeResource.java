/*
 *   Copyright 2015 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.saiku.web.rest.resources;

import org.modelmapper.ModelMapper;
import org.saiku.database.Database;
import org.saiku.database.dao.ReportDAO;
import org.saiku.database.dao.XmlConfigDAO;
import org.saiku.database.dto.CubesDimensions;
import org.saiku.database.dto.FactGroup;
import org.saiku.database.dto.Group;
import org.saiku.database.dto.Unit;
import org.saiku.database.dto.report.Report;
import org.saiku.olap.util.formatter.CellSetFormatterFactory;
import org.saiku.web.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для схемы
 */
@Component
@Path("/api")
@XmlAccessorType(XmlAccessType.NONE)
public class SchemeResource {

    @Autowired
    ServletContext servletContext;

    @Autowired
    private Database database;

    @Autowired
    private SessionService sessionService;

    private CellSetFormatterFactory cff = new CellSetFormatterFactory();

    @Autowired
    private XmlConfigDAO xmlConfigDAO;

    @Autowired
    private ReportDAO reportDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @GET
    @Produces( {"application/json"})
    @Path("/scheme/{userName}/{sessionId}/{measureIds}")
    public CubesDimensions getScheme(@PathParam("measureIds") String measureIds, @PathParam("userName") String userName, @PathParam("sessionId") String sessionId) throws Exception {
        List<Integer> measureList;
        if (measureIds.length()>0) {
            String[] measureMas = measureIds.split(",");
            measureList = new ArrayList<>();
            for (String measure : measureMas) {
                measureList.add(Integer.valueOf(measure));
            }
        } else {
            measureList = null;
        }
        database.getDsm().addSchema(xmlConfigDAO.getXml(userName, sessionId, measureList), "/datasources/"+sessionId+".xml", sessionId);
        CubesDimensions cubesDimensions = xmlConfigDAO.getCubesDimensionsByMeasureIds(measureList);
        cubesDimensions.setMeasures(xmlConfigDAO.getMeasures(measureList));
        return cubesDimensions;
    }

    @GET
    @Produces( {"application/json"})
    @Path("/facts")
    public List<FactGroup> getAllFacts() {
        String userName = (String) sessionService.getSession().get("username");
        return xmlConfigDAO.getAllFacts(userName);
    }

    /**
     * Получение сгруппированных показателей
     * @param groupId идентификатор группы
     * @return
     */
    @GET
    @Produces( {"application/json"})
    @Path("/facts/{groupId}")
    public List<FactGroup> getFactsByGroupId(@PathParam("groupId") Integer groupId) {
        String userName = (String) sessionService.getSession().get("username");
        return xmlConfigDAO.getFactsByGroupId(groupId, userName);
    }

    @GET
    @Produces( {"application/json"})
    @Path("/units")
    public List<Unit> getAllUnits() {
        return xmlConfigDAO.getAllUnits();
    }

    @GET
    @Consumes({"application/json" })
    @Produces( {"application/json"})
    @Path("/dimensions/{measureIds}")
    public CubesDimensions getAllDimensions(@PathParam("measureIds") String measureIds) {
        List<Integer> measureList;
        if (measureIds.length()>0) {
            String[] measureMas = measureIds.split(",");
            measureList = new ArrayList<>();
            for (String measure : measureMas) {
                measureList.add(Integer.valueOf(measure));
            }
        } else {
            measureList = null;
        }
        CubesDimensions cubesDimensions = xmlConfigDAO.getCubesDimensionsByMeasureIds(measureList);
        cubesDimensions.setMeasures(xmlConfigDAO.getMeasures(measureList));
        return cubesDimensions;
    }

    /**
     * Получение списка всех отчётов
     */
    @GET
    @Produces( {"application/json"})
    @Path("/reports")
    public List<Report> getAllReports() {
        String userName = (String) sessionService.getSession().get("username");
        List<Report> reports = reportDAO.getAllReports(userName);
        return reports;
    }

    /**
     * Удаление отчёта по идентификатору
     * @param reportId идентификатор отчёта
     */
    @DELETE
    @Path("/report/{id}")
    public void deleteReport(@PathParam("id") Integer reportId) {
        reportDAO.deleteReport(reportId);
    }

    /**
     * Сохранение/обновление отчёта
     */
    @POST
    @Produces( {"application/json"})
    @Consumes( {"application/json"})
    @Path("/report")
    public Report saveReport(Report report) {
        return reportDAO.saveReport(report);
    }

    /**
     * Получение списка всех групп
     */
    @GET
    @Produces( {"application/json"})
    @Path("/groups")
    public List<Group> getAllGroups() {
        return xmlConfigDAO.getAllGroups();
    }

    /**
     * Получение отчёта по идентификатору
     */
    @GET
    @Produces( {"application/json"})
    @Path("/scheme/report/{reportId}")
    public Report getSchemeByReport(@PathParam("reportId") Integer reportId) throws Exception {
        Map<String, Object> sessionMap = sessionService.getSession();
        String userName = (String)sessionMap.get("username");
        String sessionId = (String)sessionMap.get("sessionid");
        database.getDsm().addSchema(xmlConfigDAO.getSchemeByReportId(reportId, userName, sessionId), "/datasources/"+sessionId+".xml", sessionId);
        return reportDAO.getReportById(userName, reportId);
    }

    /**
     * Получение списка идентификаторов измерений по идентификаторам показателей
     */
    @GET
    @Consumes({"application/json" })
    @Produces( {"application/json"})
    @Path("/dimensions/ids/{measureIds}")
    public List<Integer> getAllDimensionIds(@PathParam("measureIds") String measureIds) {
        List<Integer> measureList;
        if (measureIds.length()>0) {
            String[] measureMas = measureIds.split(",");
            measureList = new ArrayList<>();
            for (String measure : measureMas) {
                measureList.add(Integer.valueOf(measure));
            }
        } else {
            measureList = null;
        }
        return xmlConfigDAO.getCommonDimIdsByMeasureIds(measureList);
    }
}

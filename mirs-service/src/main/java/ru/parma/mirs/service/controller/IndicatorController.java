package ru.parma.mirs.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.parma.mirs.service.json.*;
import ru.parma.mirs.service.service.IndicatorService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/indicator")
public class IndicatorController {

    @Autowired public IndicatorService indicatorService;

    @RequestMapping(path = "/panel", method = RequestMethod.GET)
    public @ResponseBody Panel getOnePanel(
            @RequestParam("idMeasure") Integer idMeasure,
            @RequestParam(name = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "filterGrbs", required = false) List<Integer> filterGrbs,
            @RequestParam(name = "filterOrganisation", required = false) List<String> filterOrganisation) throws Exception{
        return indicatorService.getPanel(idMeasure, beginDate, endDate, filterGrbs, filterOrganisation);
    }

    @RequestMapping(path = "/panel", method = RequestMethod.POST)
    public @ResponseBody List<Panel> getPanels(@RequestBody RequestDto requestDtos) {
        return indicatorService.getPanels(requestDtos);
    }

    @RequestMapping(path = "/cubePanels", method = RequestMethod.GET)
    public @ResponseBody List<Panel> getCubePanels(
            @RequestParam("idCube") Integer idCube,
            @RequestParam(name = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "filterGrbs", required = false) List<Integer> filterGrbs,
            @RequestParam(name = "filterOrganisation", required = false) List<String> filterOrganisation) throws Exception {
        return indicatorService.getCubePanels(idCube, beginDate, endDate, filterGrbs, filterOrganisation);
    }

    @RequestMapping(path = "/table", method = RequestMethod.GET)
    public @ResponseBody Table getOneTable(
            @RequestParam("idMeasure") Integer idMeasure,
            @RequestParam(name = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "filterGrbs", required = false) List<Integer> filterGrbs,
            @RequestParam(name = "filterOrganisation", required = false) List<String> filterOrganisation) throws Exception{
        return indicatorService.getOneTable(idMeasure, beginDate, endDate, filterGrbs, filterOrganisation);
    }

    @RequestMapping(path = "/table", method = RequestMethod.POST)
    public @ResponseBody Tables getTables(
            @RequestParam(name = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "filterGrbs", required = false) List<Integer> filterGrbs,
            @RequestParam(name = "filterOrganisation", required = false) List<String> filterOrganisation) throws Exception{
        return indicatorService.getTables(beginDate, endDate, filterGrbs, filterOrganisation);
    }

    @RequestMapping(path = "/hierarchy", method = RequestMethod.GET)
    public @ResponseBody List<CubeMeasures> getHierarchy() {
        return indicatorService.getHierarchy();
    }
}

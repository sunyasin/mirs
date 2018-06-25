package ru.parma.mirs.loader.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.parma.mirs.loader.model.LoaderFormDto;
import ru.parma.mirs.loader.model.LogForm;
import ru.parma.mirs.loader.model.LogRecord;
import ru.parma.mirs.loader.service.LoaderLogger;
import ru.parma.mirs.loader.service.PeriodicWorker;
import ru.parma.mirs.loader.validator.LoaderFormValidator;
import ru.parma.mirs.loader.validator.LogFormValidator;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class LoaderController {

    private static final Logger LOG = LogManager.getLogger();
    private LoaderFormValidator loaderValidator;
    private LogFormValidator logValidator;
    private PeriodicWorker worker;
    private LoaderLogger logger;

    @Autowired
    public LoaderController( LoaderFormValidator customerValidator,
                             LogFormValidator logValidator,
                             PeriodicWorker worker,
                             LoaderLogger logger ) {
        this.loaderValidator = customerValidator;
        this.logValidator = logValidator;
        this.logger = logger;
        this.worker = worker;
    }

    @RequestMapping(path = "/load", method = RequestMethod.POST)
    public String processSubmit( @ModelAttribute("loader-data") LoaderFormDto formData,
                                 @ModelAttribute("log-data") LogForm logData,
                                 BindingResult result,
                                 SessionStatus status) {

        loaderValidator.validate(formData, result);
        if (result.hasErrors()) {
            return "LoaderForm";
        }
        boolean runInThreadOld = worker.isRunInThread();
        worker.setRunInThread(true); // принудительно запускаем загрузку в потоках
        String endPeriod = formData.getEndPeriod();
        if (formData.isDoLoadAccounting()) {
            worker.loadFactBuh(endPeriod);
        }
        if (formData.isDoLoadHrmIndicatorsCorp() || formData.isDoLoadHrmIndicators() || formData.isDoLoadGenericIndicators()
                || formData.isDoLoadHrmIndicatorsAndWorkTime()) {
            worker.loadIndicators(formData);
        }
        if (formData.isDoLoadDaily()) {
            worker.loadDaily(formData);
        }
        this.worker.setRunInThread(runInThreadOld);
        status.setComplete();
        return "LoaderForm";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        model.addAttribute("loader-data", new LoaderFormDto());
        model.addAttribute("log-data", new LogForm());
        return "LoaderForm";
    }

    @RequestMapping(path = "/log", method = RequestMethod.GET)
    public ModelAndView getLog( @ModelAttribute("log-data") LogForm formData,
                                ModelMap model,
                                BindingResult result ) {
        logValidator.validate(formData, result);
        if (result.hasErrors()) {
            return new ModelAndView("LoaderForm");
        }

        LocalDate ldt = LocalDate.now();
        if (!StringUtils.isEmpty(formData.getLogDate())) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            ldt = LocalDate.parse(formData.getLogDate(), dtf);
        }
        List<LogRecord> data = logger.getLog(formData.getModuleName(), ldt);
        model.addAttribute("logArea", data.toString());
        ModelAndView mv = new ModelAndView("LogResultForm");
        mv.addObject("row_list", data);
        return mv;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute("moduleList")
    public Map<String, String> moduleList() {

        Map<String, String> items = new LinkedHashMap<String, String>();
        Arrays.stream(LoaderLogger.LogModule.values()).forEach(item -> {
            items.put(item.name(), item.name());
        });
        return items;
    }
}
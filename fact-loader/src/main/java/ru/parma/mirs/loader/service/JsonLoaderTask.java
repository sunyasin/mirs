package ru.parma.mirs.loader.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.parma.mirs.loader.dto.json.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
public class JsonLoaderTask {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger();

    @Value("${api_login}")
    private String login;

    @Value("${api_password}")
    private String pass;

    private ObjectMapper mapper;

    @Autowired
    public JsonLoaderTask( ObjectMapper mapper ) {
        this.mapper = mapper;
    }

    public HttpURLConnection getUrlContent( String url) throws IOException {

        return getUrlContent(url, true);
    }

    public HttpURLConnection getUrlContent( String url, boolean doBasicAuth ) throws IOException {

        log.debug("--> connecting to " + url);

        URL docUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) docUrl.openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);

        if (doBasicAuth) {
            byte[] creds = (login + ":" + pass).getBytes();
            String encbytes = Base64.getEncoder().encodeToString(creds);
            con.setRequestProperty("Authorization", "Basic " + encbytes);
        }
        con.connect();
        //InputStream is = con.getInputStream();
        log.debug("<-- got stream size =" + con.getInputStream().available() + " from " + url);
        return con;
    }


    public OrganizationList loadOrganizations(String url) throws IOException {

        HttpURLConnection is = getUrlContent(url);
        try {
            OrganizationList result = mapper.readValue(is.getInputStream(), new TypeReference<OrganizationList>() {
            });
            return result;
        } finally {
            closeHttpConnection(is);
        }
    }

    private void closeHttpConnection(HttpURLConnection is) throws IOException {

        if (is != null) {
            is.getInputStream().close();
            is.disconnect();
        }
    }

    public FactBuhList parseFactBuhList(String url) throws IOException {

        HttpURLConnection is = getUrlContent(url);
        try {
            FactBuhList result = mapper.readValue(is.getInputStream(), new TypeReference<FactBuhList>() {
            });
            return result;
        } finally {
            closeHttpConnection(is);
        }
    }

    public ListOfDataAreaEmpList loadHRMIndicators(String url) throws IOException {

        HttpURLConnection is = getUrlContent(url);
        try {
            ListOfDataAreaEmpList result = mapper.readValue(is.getInputStream()/*GetHRMIndicators()*/, new TypeReference<ListOfDataAreaEmpList>() {
            });
            return result;
        } finally {
            closeHttpConnection(is);
        }
    }

    private String factBuhDataBGU() {
        return factBuhDataZKGU();
    }

    private String factBuhDataZKGU() {
        return "{\n" +
                "\"dataAreas\": [\n" +
                "{\n" +
                "\"id\": 20,\n" +
                "\"begin\": \"2018-05-01T12:00:00\",\n" +
                "\"end\": \"2018-05-09T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902292738\",\n" +
                "\"totalOperations\": 23,\n" +
                "\"manualOperations\": 2,\n" +
                "\"correctiveOperations\": 0,\n" +
                "\"lastOperationDate\": \"2018-05-07T12:00:05\",\n" +
                "\"closingDate\": \"1979-12-31T12:00:00\",\n" +
                "\"accountBalance\": 0\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 24,\n" +
                "\"begin\": \"2018-05-01T12:00:00\",\n" +
                "\"end\": \"2018-05-09T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"1111111111\",\n" +
                "\"totalOperations\": 0,\n" +
                "\"manualOperations\": 0,\n" +
                "\"correctiveOperations\": 0,\n" +
                "\"lastOperationDate\": \"0001-01-01T00:00:00\",\n" +
                "\"closingDate\": \"1979-12-31T12:00:00\",\n" +
                "\"accountBalance\": 0\n" +
                "},\n" +
                "{\n" +
                "\"inn\": \"5907005112\",\n" +
                "\"totalOperations\": 0,\n" +
                "\"manualOperations\": 0,\n" +
                "\"correctiveOperations\": 0,\n" +
                "\"lastOperationDate\": \"2018-03-31T12:00:01\",\n" +
                "\"closingDate\": \"1979-12-31T12:00:00\",\n" +
                "\"accountBalance\": 6164605.68\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 38,\n" +
                "\"begin\": \"2018-05-01T12:00:00\",\n" +
                "\"end\": \"2018-05-09T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5904101918\",\n" +
                "\"totalOperations\": 0,\n" +
                "\"manualOperations\": 0,\n" +
                "\"correctiveOperations\": 0,\n" +
                "\"lastOperationDate\": \"2018-04-04T12:00:00\",\n" +
                "\"closingDate\": \"1979-12-31T12:00:00\",\n" +
                "\"accountBalance\": 1827112.57\n" +
                "}\n" +
                "]\n" +
                "}]}";
    }


    /*
    Метод GetHRMIndicators: возвращает показатели кадрового учета детализировано по сотрудникам

    ЗикГУ КОРП (тест): https://accounting.permkrai.ru/tfresh/int/hrmtestcorp/hs/PTG_Exchange/GetHRMIndicators
    ЗикГУ (тест): https://accounting.permkrai.ru/tfresh/int/hrmtest/hs/PTG_Exchange/GetHRMIndicators
    ЗикГУ КОРП: https://accounting.permkrai.ru/fresh/int/hrmcorp/hs/PTG_Exchange/GetHRMIndicators
    ЗикГУ: https://accounting.permkrai.ru/fresh/int/hrm/hs/PTG_Exchange/GetHRMIndicators

     */
    private String GetHRMIndicators() {

        return "{\n" +
                "\"dataAreas\": [\n" +
                "{\n" +
                "\"id\": 139,\n" +
                "\"begin\": \"2018-05-01T12:00:00\",\n" +
                "\"end\": \"2018-05-15T11:59:59\",\n" +
                "\"employees\": [\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"6b2e9ec8-7263-11e5-a5d1-00155d22da94\",\n" +
                "\"position\": \"Главный специалист /Отдел по организации и контрольной работе/\",\n" +
                "\"positionGUID\": \"da897cae-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"138-923-071 79\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"35f6267d-41f9-11e7-b7e7-00155da540cf\",\n" +
                "\"position\": \"Заместитель министра /Администрация/\",\n" +
                "\"positionGUID\": \"ce2f6587-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"058-793-793 35\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"d666f22d-7654-11e2-8490-00148585c501\",\n" +
                "\"position\": \"Зав.сектором,консультант /Сектор контроля качества/\",\n" +
                "\"positionGUID\": \"d490bd97-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"037-461-016 38\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"30ead119-b976-11e7-b85a-00155da540cf\",\n" +
                "\"position\": \"Главный специалист /Отдел по организации и контрольной работе/\",\n" +
                "\"positionGUID\": \"da897cae-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"106-338-456 43\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"8edcbf43-c39c-11e3-8a7b-00155dcb9b0d\",\n" +
                "\"position\": \"Главный специалист /Мобилизационный отдел/\",\n" +
                "\"positionGUID\": \"ce2f65dd-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"061-146-015 11\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"1e2abcef-0ee6-11e7-a15a-00155da540cf\",\n" +
                "\"position\": \"Ведущий специалист /Отдел по организации лекарственного обеспечения/\",\n" +
                "\"positionGUID\": \"e9faba0f-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"047-301-655 35\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"bafdfc99-8198-11e7-96b5-00155da540cf\",\n" +
                "\"position\": \"Главный специалист /Отдел по организации бюджетного учета, отчетности и анализа/\",\n" +
                "\"positionGUID\": \"d490be10-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"061-147-858 50\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": true,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"b3446986-fc83-11e5-8a4b-00155d22da94\",\n" +
                "\"position\": \"Ведущий специалист /Отдел по организации лекарственного обеспечения/\",\n" +
                "\"positionGUID\": \"e9faba0f-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"140-164-172 11\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"ecf380cd-5657-11e3-993a-00155d18540b\",\n" +
                "\"position\": \"Главный специалист /Сектор ицензирования фармацевтической деятельности, деятельности с оборотом нарк\",\n" +
                "\"positionGUID\": \"ce2f6633-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"061-228-317 26\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"1c359547-e4c4-11e7-a49d-00155da540cf\",\n" +
                "\"position\": \"Начальник управления /УПРАВЛЕНИЕ СТРАТЕГИЧЕСКОГО РАЗВИТИЯ/\",\n" +
                "\"positionGUID\": \"da897d2d-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"052-099-105 42\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"cc9ceaec-767f-11e2-8490-00148585c501\",\n" +
                "\"position\": \"Начальник отдела /Отдел нормирования труда и заработной платы/\",\n" +
                "\"positionGUID\": \"e9faba59-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"051-458-744 60\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"52a8930a-e183-11e7-aef1-00155da540cf\",\n" +
                "\"position\": \"Главный специалист /Отдел по организации первичной и специализированной медико-санитарной помощи/\",\n" +
                "\"positionGUID\": \"da897ced-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"134-828-091 69\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"cc9ceae9-767f-11e2-8490-00148585c501\",\n" +
                "\"position\": \"Главный специалист /Отдел экономического анализа и планирования фин. деятельности гос. учреждений/\",\n" +
                "\"positionGUID\": \"d490bddf-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"081-557-845 90\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"def0fa02-a59e-11e2-99f3-38607723d008\",\n" +
                "\"position\": \"Ведущий специалист /Отдел по кадровой, работе, аттестации и повышения квалификации мед.кадров, орган\",\n" +
                "\"positionGUID\": \"d490bda0-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"155-215-171 38\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": true,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"32603c20-d00a-11e7-a3e2-00155da540cf\",\n" +
                "\"position\": \"Заместитель министра /Администрация/\",\n" +
                "\"positionGUID\": \"ce2f6587-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"074-260-736 58\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": true,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"7f661429-f74d-11e6-bf67-00155da540cf\",\n" +
                "\"position\": \"Консультант /Отдел финансового контроля/\",\n" +
                "\"positionGUID\": \"d490bdec-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"042-081-367 17\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"5b75b5dc-0b38-11e8-88db-00155d239f5c\",\n" +
                "\"position\": \"Зав.сектором,консультант /Сектор по организации и совершенствонванию медиц.реабилитации, паллиативно\",\n" +
                "\"positionGUID\": \"5b75b5e1-0b38-11e8-88db-00155d239f5c\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"055-167-753 72\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"6abb696c-742a-11e2-848d-00148585c501\",\n" +
                "\"position\": \"Консультант /Отдел по организации медицинской помощи детям/\",\n" +
                "\"positionGUID\": \"e256347a-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"031-029-640 02\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"ed35be4b-a7d9-11e2-84bf-00148585c501\",\n" +
                "\"position\": \"Ведущий специалист /Отдел по организации высокотехнологической медицинской помощи и лечению за преда\",\n" +
                "\"positionGUID\": \"e25634ef-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"058-495-364 08\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": false,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": true,\n" +
                "\"hasExperience\": true,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": false,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"f8cf22c4-7b0a-11e2-9bf9-002215d823fe\",\n" +
                "\"position\": \"Главный специалист /Отдел по организации бюджетного учета, отчетности и анализа/\",\n" +
                "\"positionGUID\": \"d490be10-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"105-063-467 16\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"3abc711c-74c5-11e2-9bf3-002215d823fe\",\n" +
                "\"position\": \"Начальник отдела /ОТДЕЛ ПО РАБОТЕ С ОБРАЩЕНИЯМИ ГРАЖДАН И ЮРИДИЧЕСКИМИ ЛИЦАМИ/\",\n" +
                "\"positionGUID\": \"865d10d2-1620-11e8-ac64-00155d239f5c\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"052-234-539 26\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "},\n" +
                "{\n" +
                "\"organizationINN\": \"5902293308\",\n" +
                "\"employeeGUID\": \"663308ad-ceaa-11e7-a3e2-00155da540cf\",\n" +
                "\"position\": \"Главный специалист /Сектор контроля качества/\",\n" +
                "\"positionGUID\": \"ce2f663f-0740-11e8-83e8-5800e359e104\",\n" +
                "\"rate\": 1,\n" +
                "\"snils\": \"043-353-529 35\",\n" +
                "\"hasPersonnelNumber\": true,\n" +
                "\"hasBirthDate\": true,\n" +
                "\"hasBirthPlace\": true,\n" +
                "\"hasGender\": true,\n" +
                "\"hasINN\": true,\n" +
                "\"hasSpecialty\": false,\n" +
                "\"hasCareer\": false,\n" +
                "\"hasExperience\": false,\n" +
                "\"hasEducation\": false,\n" +
                "\"hasFamily\": false,\n" +
                "\"hasAddressInfo\": false,\n" +
                "\"hasIdentificationDoc\": true,\n" +
                "\"hasStaffDoc\": true,\n" +
                "\"salaryAccrualCount\": 0,\n" +
                "\"salaryPaymentCount\": 0\n" +
                "}" +
                "]}]}";
    }


    private String staticGenericIndicators() {

        return "{\n" +
                "\"dataAreas\": [\n" +
                "{\n" +
                "\"id\": 139,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902293308\",\n" +
                "\"totalStaffPositions\": 135,\n" +
                "\"totalFullTimePositions\": 152,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-03-26T04:44:11\",\n" +
                "\"lastPayrollOperationDate\": \"2018-03-26T04:44:11\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 141,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290586\",\n" +
                "\"totalStaffPositions\": 21,\n" +
                "\"totalFullTimePositions\": 23,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-04-19T12:00:00\",\n" +
                "\"lastPayrollOperationDate\": \"2018-04-20T12:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 161,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902293298\",\n" +
                "\"totalStaffPositions\": 0,\n" +
                "\"totalFullTimePositions\": 0,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2017-12-01T02:10:05\",\n" +
                "\"lastPayrollOperationDate\": \"0001-01-01T00:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 167,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902293192\",\n" +
                "\"totalStaffPositions\": 63,\n" +
                "\"totalFullTimePositions\": 72,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-08T04:35:40\",\n" +
                "\"lastPayrollOperationDate\": \"2018-01-31T12:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 260,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290709\",\n" +
                "\"totalStaffPositions\": 149,\n" +
                "\"totalFullTimePositions\": 0,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-03-27T01:21:23\",\n" +
                "\"lastPayrollOperationDate\": \"0001-01-01T00:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 262,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902291090\",\n" +
                "\"totalStaffPositions\": 47,\n" +
                "\"totalFullTimePositions\": 73,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-08T12:00:00\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-08T12:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 263,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290586\",\n" +
                "\"totalStaffPositions\": 21,\n" +
                "\"totalFullTimePositions\": 23,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-03-29T12:00:02\",\n" +
                "\"lastPayrollOperationDate\": \"2018-03-29T12:00:02\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 264,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902221423\",\n" +
                "\"totalStaffPositions\": 38,\n" +
                "\"totalFullTimePositions\": 40,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-03-31T12:00:01\",\n" +
                "\"lastPayrollOperationDate\": \"2018-03-31T12:00:01\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 265,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290931\",\n" +
                "\"totalStaffPositions\": 43,\n" +
                "\"totalFullTimePositions\": 135,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-02-28T12:00:08\",\n" +
                "\"lastPayrollOperationDate\": \"2018-02-28T12:00:08\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 266,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290723\",\n" +
                "\"totalStaffPositions\": 173,\n" +
                "\"totalFullTimePositions\": 14,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-03-06T12:00:00\",\n" +
                "\"lastPayrollOperationDate\": \"0001-01-01T00:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 302,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902293298\",\n" +
                "\"totalStaffPositions\": 4,\n" +
                "\"totalFullTimePositions\": 4,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-10T04:20:52\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-10T04:20:52\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 306,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290709\",\n" +
                "\"totalStaffPositions\": 187,\n" +
                "\"totalFullTimePositions\": 238.5,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-10T12:00:00\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-10T12:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 307,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290931\",\n" +
                "\"totalStaffPositions\": 43,\n" +
                "\"totalFullTimePositions\": 136,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-03T02:09:46\",\n" +
                "\"lastPayrollOperationDate\": \"2018-04-26T12:00:00\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 308,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902290723\",\n" +
                "\"totalStaffPositions\": 173,\n" +
                "\"totalFullTimePositions\": 14,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-07T03:11:24\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-07T03:11:24\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 309,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902221423\",\n" +
                "\"totalStaffPositions\": 38,\n" +
                "\"totalFullTimePositions\": 40,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-10T09:51:25\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-10T09:51:25\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"id\": 328,\n" +
                "\"end\": \"2018-05-10T11:59:59\",\n" +
                "\"organizations\": [\n" +
                "{\n" +
                "\"inn\": \"5902293308\",\n" +
                "\"totalStaffPositions\": 155,\n" +
                "\"totalFullTimePositions\": 182,\n" +
                "\"hrmClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"payrollClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"salaryAccountingClosingDate\": \"0001-01-01T00:00:00\",\n" +
                "\"lastHRMOperationDate\": \"2018-05-08T02:47:58\",\n" +
                "\"lastPayrollOperationDate\": \"2018-05-04T02:40:42\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "]\n" +
                "}";
    }

    public ApplicationInfo loadDataAreas( String url) throws IOException {

        HttpURLConnection is = getUrlContent(url);
        try {
            ApplicationInfo result = mapper.readValue(is.getInputStream(), new TypeReference<ApplicationInfo>() {
            });
            return result;
        } finally {
            closeHttpConnection(is);
        }
    }

    public OrgStat loadStatistics( String url ) throws IOException {

        HttpURLConnection is = getUrlContent(url);
        try {
            OrgStat result = mapper.readValue(is.getInputStream(), new TypeReference<OrgStat>() {
            });
            return result;
        }
        finally {
            closeHttpConnection(is);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass( String pass ) {
        this.pass = pass;
    }

    public ListOfDataAreaOrgList loadGenericIndicatorsNew( String url ) throws IOException {
        HttpURLConnection is = getUrlContent(url, true);
        try {
            ListOfDataAreaOrgList result = mapper.readValue(is.getInputStream(), new TypeReference<ListOfDataAreaOrgList>() {
            });
            return result;
        }
        finally {
            closeHttpConnection(is);
        }
    }

    public ListOfEmployeeDates loadHRMIndicatorsDates( String url ) throws IOException {

        HttpURLConnection is = getUrlContent(url, true);
        try {
            ListOfEmployeeDates result = mapper.readValue(is.getInputStream(), new TypeReference<ListOfEmployeeDates>() {
            });
            return result;
        }
        finally {
            closeHttpConnection(is);
        }
    }
}

package org.saiku.loader;

import org.junit.Test;
import org.saiku.service.loader.JsonLoaderTask;
import org.saiku.service.loader.dto.json.GenericIndicators;
import org.saiku.service.loader.dto.json.ListOfDataAreaEmpList;
import org.saiku.service.loader.dto.json.ListOfDataAreaOrgList;
import org.saiku.service.loader.dto.json.OrganizationList;

import static org.junit.Assert.assertTrue;

public class LoaderTest {
    private String apiLogin = "Parma";
    private String apiPass = "Parma345";

    @Test
    public void jsonLoaderTest() throws Exception {
        String HRMIndicatorsUrl = "https://accounting.permkrai.ru/tfresh/int/hrmtestcorp/hs/PTG_Exchange/GetHRMIndicators";

        JsonLoaderTask fact = new JsonLoaderTask(apiLogin, apiPass);
        ListOfDataAreaEmpList result = fact.loadHRMIndicators(HRMIndicatorsUrl);

        assertTrue(!result.dataAreas.isEmpty());
        System.out.println("size=" + result.dataAreas.size());
        System.out.println("result=" + result);
    }

    @Test
    public void loadGenericIndicatorsTest() throws Exception {
//        String genericIndicators_zikgu_corp = "https://accounting.permkrai.ru/fresh/int/hrmcorp/hs/PTG_Exchange/GetGenericIndicators";
        String genericIndicators_zikgu_corp = "https://accounting.permkrai.ru/tfresh/int/hrmtestcorp/hs/PTG_Exchange/GetGenericIndicators";
        String genericIndicators_zikgu = "https://accounting.permkrai.ru/fresh/int/hrm/hs/PTG_Exchange/GetGenericIndicators";


        JsonLoaderTask fact = new JsonLoaderTask(apiLogin, apiPass);
        ListOfDataAreaOrgList result = fact.loadGenericIndicatorsNew(genericIndicators_zikgu_corp);

        assertTrue(!result.dataAreas.isEmpty());
    }

    @Test
    public void loadOrgList() throws Exception {

        String url = "http://budget.gov.ru/epbs/registry/ubpandnubp/data";
        JsonLoaderTask fact = new JsonLoaderTask(apiLogin, apiPass);
        OrganizationList result = fact.loadOrganizations(url);

        assertTrue(!result.orgList.isEmpty());
        System.out.println("size=" + result.orgList.size());
        System.out.println("result=" + result);
    }
}

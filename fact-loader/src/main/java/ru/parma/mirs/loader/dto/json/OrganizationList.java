package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// для списка организаций с офц.сайта http://budget.gov.ru/epbs/registry/ubpandnubp/data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationList {
    public int pageCount;
    public int pageNum;
    public int pageSize;
    public int recordCount;

    @JsonProperty("data")
    public List<Organization> orgList;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Organization {
        public OrgInfo info;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrgInfo {
        public String status;
        public String recordNum;
        public String inclusionDate;
        public String exclusionDate;
        public String updateNum;
        public String code;
        public String ogrn;
        public String isUch;
        public String isOGV;
        public String isObosob;
        public String ougvCode;
        public String ougvName;
        public String regionCode;
        public String regionName;
        public String oktmoCode;
        public String oktmoName;
        public String okatoCode;
        public String okatoName;
        public String budgetLvlCode;
        public String budgetLvlName;
        public String budgetCode;
        public String budgetName;
        public String creatorKindCode;
        public String founderKindCode;
        public String creatorKindName;
        public String founderKindName;
        public String creatorPlaceCode;
        public String founderPlaceCode;
        public String creatorPlaceName;
        public String founderPlaceName;
        public String parentCode;
        public String parentName;
        public String activityKind;

        public String fullName;
        public String shortName;
        public String inn;
        public String kpp;
        public Integer kbkCode;
        public String kbkName;
    }
}

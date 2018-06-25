package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/* структура json от сервиса https://accounting.permkrai.ru/tfresh/a/sm/hs/PTG_SM_AdmStat/GetAdmStat
{
"admstat": [
    {
        "Abonent": "Министерство образования и науки",
        "countUserAll": 280,
        "countUserAbonent": 1,
        "countUserDate": 0,
        "countObjectNew": "",
        "countObjectChange": "",
        "countReport": "",
        "timeWork": "",
        "maxLoginArea": "",
        "meanLoginArea": ""
    },
    ...
    ]
 */
public class OrgStat {
    @JsonProperty("abonents")
    public List<OrgStatDetails> abonents;

    public static class OrgStatObject {
        public String type;
        public String name;
        public Integer countNew;
        public Integer countChanges;
        public Integer countReports;
        public Float timeWork;
        public Integer maxLoginIB;
        public Integer meanLoginIB;
    }

    public static class OrgStatSubsystem {
        public String id;
        public String name;
        public List<OrgStatObject> objects;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrgStatDetails {
        public String inn;
        public String name;
        public Integer countUsers;
        public Integer countNewUsers;
        public List<OrgStatSubsystem> subsystems;

        @Override
        public String toString() {
            return "OrgStatDetails{" +
                    "inn='" + inn + '\'' +
                    ", name='" + name + '\'' +
                    ", countUsers=" + countUsers +
                    ", countNewUsers=" + countNewUsers +
                    ", subsystems=" + subsystems +
                    '}';
        }
    }
}

package ru.parma.mirs.loader.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
"subsystems": [
{
    "id": "acc",
    "name": "Бухгалтерия государственного учреждения",
    "webAddresses": [
    {
        "applicationAddress": "https://accounting.permkrai.ru/fresh/a/acc",
        "managementAddress": "https://accounting.permkrai.ru/fresh/int/acc/",
        "dataAreas": [
            20,...]
    }
    },
    ....
    ]
 */
public class ApplicationInfo {
    public enum AppSubsystem {
        ACC, HRM, HRMCORP;
    }

    public List<Subsystem> subsystems;

    public Set<Integer> getDataAreasForSubsystem( AppSubsystem subsystem ) {
        if (subsystems != null) {
            for (Subsystem item : subsystems) {
                if (subsystem.name().equalsIgnoreCase(item.id)) {
                    if (item.webAddresses != null && !item.webAddresses.isEmpty())
                        return item.webAddresses.get(0).dataAreas;
                }
            }
        }
        return Collections.emptySet();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Addresses {
        public TreeSet<Integer> dataAreas;
    }

    public static class Subsystem {
        public String id;
        public String name;
        public List<Addresses> webAddresses;
    }
}

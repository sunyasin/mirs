package ru.parma.mirs.loader.dto.json;

public enum OrgStatSubsystemEnum {
    BGU("Бухгалтерия государственного учреждения", SubsystemEnum.BGU.ordinal()),
    ZIKGU("Зарплата и кадры государственного учреждения", SubsystemEnum.SALARY.ordinal()),;

    private String id;
    private int mirsCode;

    OrgStatSubsystemEnum( String id, int mirsCode ) {

        this.id = id;
        this.mirsCode = mirsCode;
    }

    public static OrgStatSubsystemEnum getById( String find ) {

        for (OrgStatSubsystemEnum item : OrgStatSubsystemEnum.values()) {
            if (item.getId().equalsIgnoreCase(find))
                return item;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getMirsCode() {
        return mirsCode;
    }
}

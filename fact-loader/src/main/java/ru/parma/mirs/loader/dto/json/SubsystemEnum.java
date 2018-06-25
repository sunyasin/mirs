package ru.parma.mirs.loader.dto.json;

public enum SubsystemEnum {
    //Зарплата и кадры государственного учреждения
    BUH_ACC_SALARY(1),//1	"Бухгалтерский учет зарплаты
    BGU(2),           //2. Бухгалтерия государственного учреждения - Бухучет
    STAFF_ACC(4),     //4	Кадровый учет
    SALARY(5)        //5	Зарплата
    ;

    private int id;

    SubsystemEnum(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }
}

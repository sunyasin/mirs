package ru.parma.mirs.loader.dto.json;

public enum CartValue {
    __zeroDummy(false),
    NUMBER(true),     //1	Табельный Номер
    BIRTHDAY(false),   //2	Дата Рождения
    GENDER(true),      //3	Пол
    INN(false),        //4	ИНН
    SNILS(false),      //5	Страховой Номер ПФР
    BERTHPLACE(true),  //6	Место Рождения
    ID_PASSPORT(false),//7	Сведения о документе, удостоверяющем личность
    ADDRESS(false),    //8	Адрес регистрации или проживания
    SPECIALITY(true),  //9	Сведения о специальности
    CAREER(true),      //10	Сведения о трудовой деятельности
    EXPERIENCE(true),  //11	Сведения о стаже
    EDUCATION(true),   //12	Сведения об образовании
    FAMILY(true),      //13	Сведения о семье
    ;
    public boolean isMandatoryForStaff; // обязательный только для штатных сотрудников
    public boolean isMandatoryForAll;   // обязательный для рядовых сотрудников

    CartValue(boolean isMandatoryForStaff) {

        this.isMandatoryForStaff = isMandatoryForStaff;
        this.isMandatoryForAll = !isMandatoryForStaff;
    }
}

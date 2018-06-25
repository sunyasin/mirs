
CREATE TABLE spr_accrual
(
  id         SERIAL NOT NULL
    CONSTRAINT spr_accrual_pkey
    PRIMARY KEY,
  name       VARCHAR(100),
  sort_order INTEGER DEFAULT 0
);
CREATE UNIQUE INDEX spr_accrual_id_uindex  ON spr_accrual (id);
CREATE INDEX spr_accrual_name_index  ON spr_accrual (name);
COMMENT ON TABLE spr_accrual IS 'виды начислений';

CREATE TABLE IF NOT EXISTS  DIMENSION_TYPE
(
  dim_type_id SERIAL NOT NULL
    CONSTRAINT PK_DIMENSION_TYPE
    PRIMARY KEY,
  type_name   VARCHAR(100) NOT NULL,
  type_key    VARCHAR(50)
);
COMMENT ON TABLE DIMENSION_TYPE IS 'Типы измерений';
COMMENT ON COLUMN DIMENSION_TYPE.dim_type_id IS 'идентификатор типа измерения';
COMMENT ON COLUMN DIMENSION_TYPE.type_name IS 'наименвоание типа измерения';
COMMENT ON COLUMN DIMENSION_TYPE.type_key IS 'ключ типа измерения';

CREATE TABLE IF NOT EXISTS  DIMENSIONS
(
  dimension_id    SERIAL NOT NULL
    CONSTRAINT PK_DIMENSIONS
    PRIMARY KEY,
  dim_type_id     INT
    CONSTRAINT FK_DIMENSIONS_TYPE
    REFERENCES DIMENSION_TYPE,
  d_name          VARCHAR(200) NOT NULL,
  d_key           VARCHAR(50),
  d_desc          VARCHAR,
  field_id        VARCHAR(50),
  field_name      VARCHAR(50),
  field_parent_id VARCHAR(50),
  field_order     VARCHAR(50),
  table_name      VARCHAR(50),
  where_text      VARCHAR
);

COMMENT ON TABLE DIMENSIONS IS 'Справочник измерений куба';
COMMENT ON COLUMN DIMENSIONS.dimension_id IS 'идентификатор измерения';
COMMENT ON COLUMN DIMENSIONS.dim_type_id IS 'идентификатор типа измерения';
COMMENT ON COLUMN DIMENSIONS.d_name IS 'наименование измерения';
COMMENT ON COLUMN DIMENSIONS.d_key IS 'ключ измерения';
COMMENT ON COLUMN DIMENSIONS.d_desc IS 'описание измерения';
COMMENT ON COLUMN DIMENSIONS.field_id IS 'поле для идентификатора значений измерения';
COMMENT ON COLUMN DIMENSIONS.field_name IS 'поле для наименования значений измерения';
COMMENT ON COLUMN DIMENSIONS.field_parent_id IS 'поле для предка значений измерения';
COMMENT ON COLUMN DIMENSIONS.field_order IS 'поле для сортировки значений измерения';
COMMENT ON COLUMN DIMENSIONS.table_name IS 'наименование таблицы или подзапрос для выбора значений измерения';
COMMENT ON COLUMN DIMENSIONS.where_text IS 'условия выбора значений измерений';


CREATE TABLE IF NOT EXISTS  CUBES
(
  cube_id       SERIAL NOT NULL
    CONSTRAINT PK_CUBES
    PRIMARY KEY,
  group_id      INT,
  cube_name     VARCHAR(200) NOT NULL,
  host          VARCHAR(50),
  database_name VARCHAR(50),
  schema_name   VARCHAR(50),
  table_name    VARCHAR(50),
  is_visible    BOOLEAN NOT NULL
);

COMMENT ON TABLE CUBES IS 'Описание кубов для анализа';
COMMENT ON COLUMN CUBES.cube_id IS 'Идентификатор куба';
COMMENT ON COLUMN CUBES.group_id IS 'идентификатор группы кубов';
COMMENT ON COLUMN CUBES.cube_name IS 'наименование куба';
COMMENT ON COLUMN CUBES.host IS 'адрес сервера, где лежит куб';
COMMENT ON COLUMN CUBES.database_name IS 'БД, где лежит куб';
COMMENT ON COLUMN CUBES.schema_name IS 'наименование схемы, где лежит куб';
COMMENT ON COLUMN CUBES.table_name IS 'наименование таблицы, где лежит куб';
COMMENT ON COLUMN CUBES.is_visible IS 'флаг видимости';


CREATE TABLE IF NOT EXISTS  CUBE_DIMENSION_DT_LEVEL
(
  dt_level_id INT NOT NULL PRIMARY KEY,
  level_name  VARCHAR(50)
);
COMMENT ON TABLE CUBE_DIMENSION_DT_LEVEL IS 'Уровень даты для измерения по';
COMMENT ON COLUMN CUBE_DIMENSION_DT_LEVEL.dt_level_id IS 'идентификатор уровня даты';
COMMENT ON COLUMN CUBE_DIMENSION_DT_LEVEL.level_name IS 'наименование уровня';

CREATE TABLE IF NOT EXISTS  CUBE_DIMENSION
(
  cube_dim_id    SERIAL
    CONSTRAINT PK_CUBE_DIMENSION
    PRIMARY KEY,
  cube_id        INT           NOT NULL
    CONSTRAINT FK_CUBE_DIMENSION_CUBE
    REFERENCES CUBES,
  dimension_id   INT           NOT NULL
    CONSTRAINT FK_CUBE_DIMENSION_DIMENSIONS
    REFERENCES DIMENSIONS,
  d_column       VARCHAR(50)  NOT NULL,
  d_order        INT DEFAULT 1 NOT NULL,
  is_visible     BOOLEAN DEFAULT TRUE NOT NULL,
  dt_level_id    INT
    CONSTRAINT FK_CUBE_DIMENSION_DT_LEVEL
    REFERENCES CUBE_DIMENSION_DT_LEVEL,
  value_min_year INT
);
COMMENT ON TABLE CUBE_DIMENSION IS 'измерения куба';
COMMENT ON COLUMN CUBE_DIMENSION.cube_dim_id IS 'Идентификатор измерения куба';
COMMENT ON COLUMN CUBE_DIMENSION.cube_id IS 'Идентификатор куба';
COMMENT ON COLUMN CUBE_DIMENSION.dimension_id IS 'Идентификатор измерения';
COMMENT ON COLUMN CUBE_DIMENSION.d_column IS 'поле в таблице куба';
COMMENT ON COLUMN CUBE_DIMENSION.d_order IS 'порядок сортировки в кубе';
COMMENT ON COLUMN CUBE_DIMENSION.is_visible IS 'флаг видимости';
COMMENT ON COLUMN CUBE_DIMENSION.dt_level_id IS 'идентификатор уровня даты';
COMMENT ON COLUMN CUBE_DIMENSION.value_min_year IS 'минимальное значение года для календаря';

CREATE INDEX IF NOT EXISTS cube_dimension_cube_id_index ON public.cube_dimension (cube_id);


CREATE TABLE IF NOT EXISTS  CUBE_GROUP
(
  group_id   SERIAL NOT NULL
    CONSTRAINT PK_CUBE_GROUP
    PRIMARY KEY,
  group_name VARCHAR(500) NOT NULL
);
COMMENT ON TABLE CUBE_GROUP IS 'Группы кубов';
COMMENT ON COLUMN CUBE_GROUP.group_id IS 'идентификатор группы кубов';
COMMENT ON COLUMN CUBE_GROUP.group_name IS 'наименование группы';

CREATE TABLE IF NOT EXISTS  CUBE_MEASURE_METOD
(
  metod_id   SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE_METOD
    PRIMARY KEY,
  metod_name VARCHAR(100) NOT NULL,
  metod_key  VARCHAR(50)  NOT NULL
);
COMMENT ON TABLE CUBE_MEASURE_METOD IS 'методы агрегации фактов по куб';
COMMENT ON COLUMN CUBE_MEASURE_METOD.metod_id IS 'идентификатор метода агрегации';
COMMENT ON COLUMN CUBE_MEASURE_METOD.metod_name IS 'наименование метода';
COMMENT ON COLUMN CUBE_MEASURE_METOD.metod_key IS 'ключ метода агрегации';


CREATE TABLE IF NOT EXISTS  CUBE_MEASURE_UNIT_GROUP
(
  unit_group_id  SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE_UNIT_GROUP
    PRIMARY KEY,
  unit_group_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE CUBE_MEASURE_UNIT_GROUP IS 'группы ЕИ';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_GROUP.unit_group_id IS 'идентификатор группы ЕИ';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_GROUP.unit_group_name IS 'наименование группы ЕИ';

CREATE TABLE IF NOT EXISTS  CUBE_MEASURE_UNIT
(
  unit_id         SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE_UNIT
    PRIMARY KEY,
  unit_group_id   INT
    CONSTRAINT FK_CUBE_MEASURE_UNIT_GROUP
    REFERENCES CUBE_MEASURE_UNIT_GROUP,
  unit_name       VARCHAR(100) NOT NULL,
  unit_name_short VARCHAR(100),
  unit_key        VARCHAR(100)
);
COMMENT ON TABLE CUBE_MEASURE_UNIT IS 'единицы измерения фактов по ку';
COMMENT ON COLUMN CUBE_MEASURE_UNIT.unit_group_id IS 'идентификатор группы ЕИ';
COMMENT ON COLUMN CUBE_MEASURE_UNIT.unit_name IS 'наименование ЕИ';
COMMENT ON COLUMN CUBE_MEASURE_UNIT.unit_name_short IS 'абревиатура ЕИ';
COMMENT ON COLUMN CUBE_MEASURE_UNIT.unit_key IS 'ключ ЕИ';


CREATE TABLE IF NOT EXISTS  CUBE_MEASURE
(
  measure_id   SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE
    PRIMARY KEY,
  cube_id      INT           NOT NULL
    CONSTRAINT FK_CUBE_MEASURE_CUBE
    REFERENCES CUBES,
  metod_id     INT
    CONSTRAINT FK_CUBE_MEASURE_METOD
    REFERENCES CUBE_MEASURE_METOD,
  unit_id      INT
    CONSTRAINT FK_CUBE_MEASURE_UNIT
    REFERENCES CUBE_MEASURE_UNIT,
  m_name       VARCHAR(200) NOT NULL
    CONSTRAINT UNIQUE_CUBE_MEASURE
    UNIQUE,
  m_column     VARCHAR(50),
  m_key        VARCHAR(50),
  m_desc       VARCHAR,
  m_order      INT DEFAULT 1 NOT NULL,
  bit_depth    INT DEFAULT 0 NOT NULL,
  is_visible   BOOLEAN DEFAULT TRUE NOT NULL,
  format_value VARCHAR(100),
  last_date    DATE,
  last_value   NUMERIC(32, 5),
  formula_text VARCHAR
);
CREATE INDEX IF NOT EXISTS cube_measure_cube_id_index ON public.CUBE_MEASURE (cube_id);
COMMENT ON TABLE CUBE_MEASURE IS 'Факты куба';
COMMENT ON COLUMN CUBE_MEASURE.measure_id IS 'идентификатор факта куба';
COMMENT ON COLUMN CUBE_MEASURE.cube_id IS 'Идентификатор куба';
COMMENT ON COLUMN CUBE_MEASURE.metod_id IS 'идентификатор метода агрегации';
COMMENT ON COLUMN CUBE_MEASURE.unit_id IS 'идентификатор ЕИ';
COMMENT ON COLUMN CUBE_MEASURE.m_name IS 'наименование факта';
COMMENT ON COLUMN CUBE_MEASURE.m_column IS 'название поля в таблице куба';
COMMENT ON COLUMN CUBE_MEASURE.m_key IS 'ключ факта';
COMMENT ON COLUMN CUBE_MEASURE.m_desc IS 'описание факта';
COMMENT ON COLUMN CUBE_MEASURE.m_order IS 'порядок сортировки в кубе';
COMMENT ON COLUMN CUBE_MEASURE.bit_depth IS 'разрядность значения';
COMMENT ON COLUMN CUBE_MEASURE.is_visible IS 'флаг видимости';
COMMENT ON COLUMN CUBE_MEASURE.format_value IS 'Формат вывода значений показателя в таблице';
COMMENT ON COLUMN CUBE_MEASURE.last_date IS 'последняя дата, на которую есть данные';
COMMENT ON COLUMN CUBE_MEASURE.last_value IS 'максимальное значение на последнюю дату';
COMMENT ON COLUMN CUBE_MEASURE.formula_text IS 'текст формулы для расчета значений показателя';

CREATE TABLE IF NOT EXISTS  CUBE_MEASURE_UNIT_CONVERT
(
  rec_id       SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE_UNIT_CONVERT
    PRIMARY KEY,
  unit_from_id INT NOT NULL
    CONSTRAINT FK_MEASURE_UNIT_CONVERT_FROM
    REFERENCES CUBE_MEASURE_UNIT,
  unit_to_id   INT NOT NULL
    CONSTRAINT FK_MEASURE_UNIT_CONVERT_TO
    REFERENCES CUBE_MEASURE_UNIT,
  rate         NUMERIC(18) DEFAULT 1 NOT NULL
);
COMMENT ON TABLE CUBE_MEASURE_UNIT_CONVERT IS 'коневертация данных из одних измерений в другие';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_CONVERT.rec_id IS 'идентификатор факта куба';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_CONVERT.unit_from_id IS 'идентификатор ЕИ - из которых нужно перевести';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_CONVERT.unit_to_id IS 'идентификатор ЕИ - в которые переводим';
COMMENT ON COLUMN CUBE_MEASURE_UNIT_CONVERT.rate IS 'коэффициент перевода';

CREATE TABLE IF NOT EXISTS  CUBE_MEASURE_LINK
(
  measure_link_id SERIAL NOT NULL
    CONSTRAINT PK_CUBE_MEASURE_LINK
    PRIMARY KEY,
  measure_id      INT NOT NULL
    CONSTRAINT FK_CUBE_MEASURE_LINK_MEASURE
    REFERENCES CUBE_MEASURE,
  child_id        INT NOT NULL
    CONSTRAINT FK_CUBE_MEASURE_LINK_CHILD
    REFERENCES CUBE_MEASURE
);

CREATE INDEX IF NOT EXISTS cube_measure_link_measure_id_index ON public.cube_measure_link (measure_id);
COMMENT ON TABLE CUBE_MEASURE_LINK IS 'Перечень показатателей, от которых зависит расчетный показатель';
COMMENT ON COLUMN CUBE_MEASURE_LINK.measure_link_id IS 'идентификатор зависимости показателя при расчете';
COMMENT ON COLUMN CUBE_MEASURE_LINK.measure_id IS 'идентификатор расчетного факта куба';
COMMENT ON COLUMN CUBE_MEASURE_LINK.child_id IS 'факт, который используется при расчете нашего расчетного факта';


CREATE TABLE IF NOT EXISTS  DIMENSION_LEVEL
(
  level_id     SERIAL NOT NULL
    CONSTRAINT PK_DIMENSION_LEVEL
    PRIMARY KEY,
  dimension_id INT NOT NULL
    CONSTRAINT FK_DIMENSION_LEVEL_DIMENSION
    REFERENCES DIMENSIONS,
  level_name   VARCHAR(100) NOT NULL,
  level_order  INT DEFAULT 1 NOT NULL,
  field_key    VARCHAR(100) NOT NULL,
  field_value  VARCHAR(50)  NOT NULL
);
CREATE INDEX IF NOT EXISTS I_DIMENSION_LEVEL_DIMENSION ON public.dimension_level (dimension_id);
COMMENT ON TABLE DIMENSION_LEVEL IS 'Уровни иерархии для сложных спр-ков для измерений';
COMMENT ON COLUMN DIMENSION_LEVEL.level_id IS 'идентификатор уровня';
COMMENT ON COLUMN DIMENSION_LEVEL.dimension_id IS 'идентификатор измерения';
COMMENT ON COLUMN DIMENSION_LEVEL.level_name IS 'наименование уровня';
COMMENT ON COLUMN DIMENSION_LEVEL.level_order IS 'порядок уровня по измерению';
COMMENT ON COLUMN DIMENSION_LEVEL.field_key IS 'поле для идентификатора значения уровня';
COMMENT ON COLUMN DIMENSION_LEVEL.field_value IS 'поле для значений уровня';

CREATE TABLE IF NOT EXISTS  GROUPS_TYPE
(
  group_type_id INT NOT NULL
    CONSTRAINT PK_GROUPS_TYPE
    PRIMARY KEY,
  type_name     VARCHAR(100) NOT NULL
);
COMMENT ON TABLE GROUPS_TYPE IS 'Тип групп данных для анализа';
COMMENT ON COLUMN GROUPS_TYPE.group_type_id IS 'идентификатор типа группы';
COMMENT ON COLUMN GROUPS_TYPE.type_name IS 'наименование типа группы';

CREATE TABLE IF NOT EXISTS  GROUPS
(
  group_id SERIAL NOT NULL
    CONSTRAINT PK_GROUPS
    PRIMARY KEY,
  group_type_id INT NOT NULL
    CONSTRAINT FK_GROUPS_TYPE
    REFERENCES GROUPS_TYPE,
  group_name    VARCHAR(100) NOT NULL,
  is_visible    BOOLEAN DEFAULT TRUE NOT NULL
);
COMMENT ON TABLE GROUPS IS 'Группы данных для анализа';
COMMENT ON COLUMN GROUPS.group_id IS 'идентификатор группы для анализа';
COMMENT ON COLUMN GROUPS.group_type_id IS 'идентификатор типа группы';
COMMENT ON COLUMN GROUPS.group_name IS 'наименование группы для анализ';
COMMENT ON COLUMN GROUPS.is_visible IS 'флаг видимости группы';

CREATE TABLE IF NOT EXISTS  GROUPS_VALUE
(
  group_value_id SERIAL NOT NULL
    CONSTRAINT PK_GROUPS_VALUE
    PRIMARY KEY,
  group_id       INT NOT NULL
    CONSTRAINT FK_GROUPS_VALUE_GROUP
    REFERENCES GROUPS,
  group_value    VARCHAR(300) NOT NULL,
  is_visible     BOOLEAN DEFAULT TRUE NOT NULL
);
COMMENT ON TABLE GROUPS_VALUE IS 'Значения групп данных';
COMMENT ON COLUMN GROUPS_VALUE.group_value_id IS 'идентификатор значения группы';
COMMENT ON COLUMN GROUPS_VALUE.group_id IS 'идентификатор группы для анализа';
COMMENT ON COLUMN GROUPS_VALUE.group_value IS 'значение группы';
COMMENT ON COLUMN GROUPS_VALUE.is_visible IS 'флаг видимости значения группы';


CREATE TABLE IF NOT EXISTS  GROUPS_CUBE
(
  group_value_id INT NOT NULL
    CONSTRAINT FK_GROUPS_CUBE_GROUP
    REFERENCES GROUPS_VALUE,
  cube_id        INT NOT NULL
    CONSTRAINT FK_GROUPS_CUBE_CUBE
    REFERENCES CUBES,
  CONSTRAINT PK_GROUPS_CUBE
  PRIMARY KEY (group_value_id, cube_id)
);

COMMENT ON TABLE GROUPS_CUBE IS 'Привязка кубов к группам данных';
COMMENT ON COLUMN GROUPS_CUBE.group_value_id IS 'идентификатор значения группы';
COMMENT ON COLUMN GROUPS_CUBE.cube_id IS 'Идентификатор куба';


CREATE TABLE IF NOT EXISTS  GROUPS_MEASURE
(
  group_value_id INT NOT NULL
    CONSTRAINT FK_GROUPS_MEASURE_GROUP
    REFERENCES GROUPS_VALUE,
  measure_id     INT NOT NULL
    CONSTRAINT FK_GROUPS_MEASURE_MEASURE
    REFERENCES CUBE_MEASURE,
  CONSTRAINT PK_GROUPS_MEASURE
  PRIMARY KEY (group_value_id, measure_id)
);

COMMENT ON TABLE GROUPS_MEASURE IS 'привязка показателей к группам';
COMMENT ON COLUMN GROUPS_MEASURE.group_value_id IS 'идентификатор значения группы';
COMMENT ON COLUMN GROUPS_MEASURE.measure_id IS 'идентификатор факта куба';

CREATE TABLE IF NOT EXISTS  REPORT_VIEW
(
  view_id   SERIAL NOT NULL
    CONSTRAINT PK_REPORT_VIEW
    PRIMARY KEY,
  view_name VARCHAR(100) NOT NULL,
  view_key  VARCHAR(30)
);
COMMENT ON TABLE REPORT_VIEW IS 'справочник видов отображения отчетов';
COMMENT ON COLUMN REPORT_VIEW.view_id IS 'идентификатор вида отображения';
COMMENT ON COLUMN REPORT_VIEW.view_name IS 'наименование вида отображения';
COMMENT ON COLUMN REPORT_VIEW.view_key IS 'ключ вида отображения';


CREATE TABLE IF NOT EXISTS  USERS
(
  user_id          SERIAL NOT NULL
    CONSTRAINT PK_USERS
    PRIMARY KEY,
  user_login       VARCHAR(128)  NOT NULL,
  user_password    VARCHAR(128)  NOT NULL,
  user_name        VARCHAR(500),
  is_visible       BOOLEAN DEFAULT TRUE NOT NULL,
  user_id_external INT
);
COMMENT ON TABLE USERS  IS 'спр-к пользователей системы';
COMMENT ON COLUMN USERS.user_id IS 'идентификатор пользователя';
COMMENT ON COLUMN USERS.user_login IS 'логин';
COMMENT ON COLUMN USERS.user_password IS 'пароль';
COMMENT ON COLUMN USERS.user_name IS 'ФИО пользователя';
COMMENT ON COLUMN USERS.is_visible IS 'флаг видимости пользователя';
COMMENT ON COLUMN USERS.user_id_external IS 'идентификатор пользователя во внешней системе';

CREATE TABLE IF NOT EXISTS  REPORT_PLACE_MEASURE
(
  place_measure_id SERIAL NOT NULL
    CONSTRAINT PK_REPORT_PLACE_MEASURE
    PRIMARY KEY,
  place_name       VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_PLACE_MEASURE  IS 'Место размещения в отчете показателей';
COMMENT ON COLUMN REPORT_PLACE_MEASURE.place_measure_id IS 'идентификатор место расположения показателей в отчете';
COMMENT ON COLUMN REPORT_PLACE_MEASURE.place_name IS 'наименование места';

CREATE TABLE IF NOT EXISTS  REPORTS
(
  report_id        SERIAL NOT NULL
    CONSTRAINT PK_REPORTS
    PRIMARY KEY,
  view_id          INT NOT NULL
    CONSTRAINT FK_REPORTS_VIEW
    REFERENCES REPORT_VIEW,
  r_name           VARCHAR NOT NULL,
  dt_create        TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  dt_change        TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  user_id          INT NOT NULL
    CONSTRAINT FK_REPORTS_USER
    REFERENCES USERS,
  place_measure_id INT
    CONSTRAINT FK_REPORTS_PLACE
    REFERENCES REPORT_PLACE_MEASURE,
  user_name        VARCHAR(500)
);
CREATE INDEX IF NOT EXISTS reports_user_id_index ON public.reports (user_id);
COMMENT ON TABLE REPORTS  IS 'Сохраненные отчеты по пользователям';
COMMENT ON COLUMN REPORTS.report_id IS 'идентификатор отчета';
COMMENT ON COLUMN REPORTS.view_id IS 'наименование вида отображения';
COMMENT ON COLUMN REPORTS.r_name IS 'наименование отчета';
COMMENT ON COLUMN REPORTS.dt_create IS 'дата-время создания отчета';
COMMENT ON COLUMN REPORTS.dt_change IS 'дата-время последнего обновления';
COMMENT ON COLUMN REPORTS.user_id IS 'идентификатор пользователя, кот.создал отчет';
COMMENT ON COLUMN REPORTS.user_name IS 'ФИО пользователя, кот.создал отчет';
COMMENT ON COLUMN REPORTS.place_measure_id IS 'идентификатор место расположения показателей в отчете';

CREATE TABLE IF NOT EXISTS  REPORT_DIMENSION
(
  report_dim_id SERIAL NOT NULL
    CONSTRAINT PK_REPORT_DIMENSION
    PRIMARY KEY,
  report_id     INT NOT NULL
    CONSTRAINT FK_REPORT_DIMENSION_REPORT
    REFERENCES REPORTS,
  dimension_id  INT NOT NULL
    CONSTRAINT FK_REPORT_DIMENSION_DIM
    REFERENCES DIMENSIONS,
  place_id      INT NOT NULL
    CONSTRAINT FK_REPORT_DIMENSION_PLACE
    REFERENCES REPORT_PLACE,
  is_hide_null  BOOLEAN DEFAULT FALSE NOT NULL,
  is_visible    BOOLEAN DEFAULT TRUE NOT NULL,
  place_order   INT DEFAULT 1 NOT NULL,
  level_id      INT
    CONSTRAINT FK_REPORT_DIMENSION_LEVEL
    REFERENCES DIMENSION_LEVEL,
  dt_level_id   INT
    CONSTRAINT FK_REPORT_DIMENSION_DT_LEVEL
    REFERENCES CUBE_DIMENSION_DT_LEVEL
);
CREATE INDEX IF NOT EXISTS I_REPORT_DIMENSION_REPORT ON REPORT_DIMENSION (report_id);
COMMENT ON TABLE REPORT_DIMENSION  IS 'Сохранненый набор измерений в отчете';
COMMENT ON COLUMN REPORT_DIMENSION.report_dim_id IS 'идентификатор измерения для отчета';
COMMENT ON COLUMN REPORT_DIMENSION.report_id IS 'идентификатор отчета';
COMMENT ON COLUMN REPORT_DIMENSION.dimension_id IS 'идентификатор измерения';
COMMENT ON COLUMN REPORT_DIMENSION.place_id IS 'идентификатор место расположения в отчете';
COMMENT ON COLUMN REPORT_DIMENSION.level_id IS 'идентификатор уровня';
COMMENT ON COLUMN REPORT_DIMENSION.dt_level_id IS 'идентификатор уровня даты';
COMMENT ON COLUMN REPORT_DIMENSION.is_hide_null IS 'признак скрытия пустых значений';
COMMENT ON COLUMN REPORT_DIMENSION.is_visible IS 'флаг видимости';
COMMENT ON COLUMN REPORT_DIMENSION.place_order IS 'порядок сортировки по расположению в отчете';


CREATE TABLE IF NOT EXISTS  REPORT_PLACE
(
  place_id   SERIAL NOT NULL
    CONSTRAINT PK_REPORT_PLACE
    PRIMARY KEY,
  place_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_PLACE  IS 'Место размещения в отчете';
COMMENT ON COLUMN REPORT_PLACE.place_id IS 'идентификатор место расположения в отчете';

CREATE TABLE IF NOT EXISTS  REPORT_INTERVAL_TYPE
(
  interval_type_id VARCHAR(15) NOT NULL
    CONSTRAINT PK_REPORT_INTERVAL_TYPE
    PRIMARY KEY,
  type_name        VARCHAR(50) NOT NULL
);
COMMENT ON TABLE REPORT_INTERVAL_TYPE  IS 'Справочник типов интервалов значений';
COMMENT ON COLUMN REPORT_INTERVAL_TYPE.interval_type_id IS 'идентификатор типа интервала значений';
COMMENT ON COLUMN REPORT_INTERVAL_TYPE.type_name IS 'наименвоание типа интервала значений';

CREATE TABLE IF NOT EXISTS  REPORT_RESULT
(
  result_id   SERIAL NOT NULL
    CONSTRAINT PK_REPORT_RESULT
    PRIMARY KEY,
  result_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_RESULT  IS 'Тип результата факта в отчете';
COMMENT ON COLUMN REPORT_RESULT.result_id IS 'идентификатор типа результата по отчету';
COMMENT ON COLUMN REPORT_RESULT.result_name IS 'наименование результата';

CREATE TABLE IF NOT EXISTS  REPORT_SPR_TOTAL
(
  total_key  VARCHAR(10)  NOT NULL
    CONSTRAINT PK_REPORT_SPR_TOTAL
    PRIMARY KEY,
  total_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_SPR_TOTAL  IS 'Справочник итогов для отчета';
COMMENT ON COLUMN REPORT_SPR_TOTAL.total_key IS 'идентификатор итога';
COMMENT ON COLUMN REPORT_SPR_TOTAL.total_name IS 'наименование итога';

CREATE TABLE IF NOT EXISTS  REPORT_MEASURE
(
  report_measure_id SERIAL NOT NULL
    CONSTRAINT PK_REPORT_MEASURE
    PRIMARY KEY,
  report_id         INT NOT NULL
    CONSTRAINT FK_REPORT_MEASURE_REPORT
    REFERENCES REPORTS,
  measure_id        INT NOT NULL
    CONSTRAINT FK_REPORT_MEASURE_MEASURE
    REFERENCES CUBE_MEASURE,
  result_id         INT
    CONSTRAINT FK_REPORT_MEASURE_RESULT
    REFERENCES REPORT_RESULT,
  is_hide_null      BOOLEAN DEFAULT FALSE NOT NULL,
  is_visible        BOOLEAN DEFAULT TRUE NOT NULL,
  place_order       INT NOT NULL,
  formula_text      VARCHAR,
  total_row         VARCHAR(10)
    CONSTRAINT FK_REPORT_MEASURE_TOTAL_ROW
    REFERENCES REPORT_SPR_TOTAL,
  total_column      VARCHAR(10)
    CONSTRAINT FK_REPORT_MEASURE_TOTAL_COL
    REFERENCES REPORT_SPR_TOTAL
);
CREATE INDEX IF NOT EXISTS I_REPORT_MEASURE_REPORT ON REPORT_MEASURE (report_id);
CREATE INDEX IF NOT EXISTS I_REPORT_MEASURE_MEASURE ON REPORT_MEASURE (measure_id);

COMMENT ON TABLE REPORT_MEASURE  IS 'Факты для сохраненного отчета';
COMMENT ON COLUMN REPORT_MEASURE.report_measure_id IS 'идентификатор факта куба в отчете';
COMMENT ON COLUMN REPORT_MEASURE.report_id IS 'идентификатор отчета';
COMMENT ON COLUMN REPORT_MEASURE.measure_id IS 'идентификатор факта куба';
COMMENT ON COLUMN REPORT_MEASURE.result_id IS 'идентификатор типа результата по отчету';
COMMENT ON COLUMN REPORT_MEASURE.is_hide_null IS 'признак скрытия пустых значений';
COMMENT ON COLUMN REPORT_MEASURE.is_visible IS 'флаг видимости';
COMMENT ON COLUMN REPORT_MEASURE.place_order IS 'порядок сортировки по расположению в отчете';
COMMENT ON COLUMN REPORT_MEASURE.formula_text IS 'текст формулы для расчета значений показателя';
COMMENT ON COLUMN REPORT_MEASURE.total_row IS 'идентификатор итога - по колонкам';
COMMENT ON COLUMN REPORT_MEASURE.total_column IS 'идентификатор итога - по строкам';

CREATE TABLE IF NOT EXISTS  REPORT_MEASURE_LINK
(
  report_measure_link_id SERIAL NOT NULL
    CONSTRAINT PK_REPORT_MEASURE_LINK
    PRIMARY KEY,
  report_measure_id      INT NOT NULL
    CONSTRAINT FK_REPORT_MEAS_LINK_REP_M
    REFERENCES REPORT_MEASURE,
  measure_id             INT NOT NULL
    CONSTRAINT FK_REPORT_MEAS_LINK_MEASURE
    REFERENCES CUBE_MEASURE
);
COMMENT ON TABLE REPORT_MEASURE_LINK  IS 'Сохраненный отчет: зависимостть расчетных показателей';
COMMENT ON COLUMN REPORT_MEASURE_LINK.report_measure_link_id IS 'идентификатор записи';
COMMENT ON COLUMN REPORT_MEASURE_LINK.report_measure_id IS 'идентификатор факта куба в отчете';
COMMENT ON COLUMN REPORT_MEASURE_LINK.measure_id IS 'идентификатор факта куба';

CREATE TABLE IF NOT EXISTS  REPORT_SORT_TYPE
(
  sort_type VARCHAR(10)  NOT NULL
    CONSTRAINT PK_REPORT_SORT_TYPE
    PRIMARY KEY,
  type_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_SORT_TYPE  IS 'Тип сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT_TYPE.sort_type IS 'тип сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT_TYPE.type_name IS 'наименвоание типа сортировки';

CREATE TABLE IF NOT EXISTS  REPORT_SORT_ORDER
(
  sort_order VARCHAR(10)  NOT NULL
    CONSTRAINT PK_REPORT_SORT_ORDER
    PRIMARY KEY,
  order_name VARCHAR(100) NOT NULL
);
COMMENT ON TABLE REPORT_SORT_ORDER  IS 'Порядок сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT_ORDER.sort_order IS 'порядок сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT_ORDER.order_name IS 'наименование порядка сортировки';

CREATE TABLE IF NOT EXISTS  REPORT_SORT
(
  report_sort_id   SERIAL NOT NULL
    CONSTRAINT PK_REPORT_SORT
    PRIMARY KEY,
  report_id        INT NOT NULL
    CONSTRAINT FK_REPORT_SORT_REPORT
    REFERENCES REPORTS,
  sort_type        VARCHAR(10)
    CONSTRAINT FK_REPORT_SORT_TYPE
    REFERENCES REPORT_SORT_TYPE,
  sort_order       VARCHAR(10)
    CONSTRAINT FK_REPORT_SORT_ORDER
    REFERENCES REPORT_SORT_ORDER,
  sort_measure     VARCHAR(100),
  total_key        VARCHAR(10)
    CONSTRAINT FK_REPORT_SORT_TOTAL
    REFERENCES REPORT_SPR_TOTAL,
  interval_type_id VARCHAR(15)
    CONSTRAINT FK_REPORTS_INTERVAL_TYPE
    REFERENCES REPORT_INTERVAL_TYPE,
  interval_count   INT,
  interval_param   VARCHAR(200)
);
CREATE INDEX IF NOT EXISTS I_REPORT_SORT_REPORT  ON REPORT_SORT (report_id);
COMMENT ON TABLE REPORT_SORT  IS 'Информация о сортировке колонок/строк в сохраненном отчете';
COMMENT ON COLUMN REPORT_SORT.report_sort_id IS 'идентификатор записи';
COMMENT ON COLUMN REPORT_SORT.sort_type IS 'тип сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT.sort_order IS 'порядок сортировки в отчете';
COMMENT ON COLUMN REPORT_SORT.sort_measure IS 'показатель, по которому производится сортировка';
COMMENT ON COLUMN REPORT_SORT.total_key IS 'идентификатор итога';
COMMENT ON COLUMN REPORT_SORT.interval_type_id IS 'идентификатор типа интервала значений';
COMMENT ON COLUMN REPORT_SORT.interval_count IS 'количество записей для вывода';
COMMENT ON COLUMN REPORT_SORT.interval_param IS 'показатель или измерение условия интервала значений';


CREATE TABLE IF NOT EXISTS  ROLES
(
  role_id   SERIAL NOT NULL
    CONSTRAINT PK_ROLES
    PRIMARY KEY,
  role_name VARCHAR(200) NOT NULL,
  role_key  VARCHAR(50),
  role_desc VARCHAR(2000)
);
COMMENT ON TABLE ROLES IS 'справочник ролей';
COMMENT ON COLUMN ROLES.role_id IS 'идентификатор роли';
COMMENT ON COLUMN ROLES.role_name IS 'наименование роли';
COMMENT ON COLUMN ROLES.role_key IS 'ключ роли';
COMMENT ON COLUMN ROLES.role_desc IS 'описание роли';

CREATE TABLE IF NOT EXISTS  USERS_ROLE
(
  user_id INT NOT NULL
    CONSTRAINT FK_USERS_ROLE_USER
    REFERENCES USERS,
  role_id INT NOT NULL
    CONSTRAINT FK_USERS_ROLE_ROLE
    REFERENCES ROLES,
  CONSTRAINT PK_USERS_ROLE
  PRIMARY KEY (user_id, role_id)
);

CREATE INDEX IF NOT EXISTS I_USERS_ROLE_USER  ON USERS_ROLE (user_id);
COMMENT ON TABLE USERS_ROLE IS 'Связь ролей с пользователями';
COMMENT ON COLUMN USERS_ROLE.role_id IS 'идентификатор роли';
COMMENT ON COLUMN USERS_ROLE.user_id IS 'идентификатор пользователя';


CREATE TABLE IF NOT EXISTS  REPORT_DIMENSION_VALUE
(
  report_dim_value_id SERIAL NOT NULL
    CONSTRAINT PK_REPORT_DIMENSION_VALUE
    PRIMARY KEY,
  report_dim_id       INT           NOT NULL
    CONSTRAINT FK_REPORT_DIMENSION_VALUE_DIM
    REFERENCES REPORT_DIMENSION,
  value_text          VARCHAR(500),
  is_include          BOOLEAN DEFAULT TRUE NOT NULL
);
CREATE INDEX IF NOT EXISTS I_REP_DIMENSION_VALUE_DIM  ON REPORT_DIMENSION_VALUE (report_dim_id);
COMMENT ON TABLE REPORT_DIMENSION_VALUE IS 'Выбранные значения по измерению в отчете';
COMMENT ON COLUMN REPORT_DIMENSION_VALUE.report_dim_value_id IS 'идентификатор значения для измерения куба в отчете';
COMMENT ON COLUMN REPORT_DIMENSION_VALUE.report_dim_id IS 'идентификатор измерения для отчета';
COMMENT ON COLUMN REPORT_DIMENSION_VALUE.value_text IS 'значение измерения';
COMMENT ON COLUMN REPORT_DIMENSION_VALUE.is_include IS 'флаг, что значение включено в выборку';


CREATE VIEW V_USERS_ROLE AS
  SELECT r.role_id,u.user_id,u.user_login,s.role_key,u.user_id_external
  FROM USERS u, USERS_ROLE r, ROLES s
  WHERE r.user_id=u.user_id
        AND s.role_id=r.role_id;


CREATE TABLE IF NOT EXISTS spr_grbs (
  id      SERIAL PRIMARY KEY,
  grbs_name VARCHAR(255) NOT NULL UNIQUE
);
COMMENT ON TABLE spr_grbs IS 'Справочник ГРБС';

-- auto-generated definition
CREATE TABLE spr_org
(
  id             SERIAL      NOT NULL
    CONSTRAINT spr_org_pkey
    PRIMARY KEY,
  grbs_id        INTEGER
    CONSTRAINT fk_spr_org_grbs
    REFERENCES spr_grbs,
  org_name       VARCHAR(255),
  related        VARCHAR(255),
  employee_count INTEGER,
  outstaff_count INTEGER,
  sort_order     INTEGER,
  inn            VARCHAR(20) NOT NULL
    CONSTRAINT spr_org_inn_pk
    UNIQUE,
  kbk_code       INTEGER,
  kbk_name       VARCHAR(255),
  full_name      VARCHAR(255),
  kpp            VARCHAR(10)
);
CREATE INDEX i_spr_org_grbs_id
  ON spr_org (grbs_id);
COMMENT ON TABLE spr_org IS 'Справочник фактов об организациях';
COMMENT ON COLUMN spr_org.id IS 'уникальный номер записи';
COMMENT ON COLUMN spr_org.grbs_id IS 'ссылка на описание ГРБС';
COMMENT ON COLUMN spr_org.org_name IS 'название организации';
COMMENT ON COLUMN spr_org.related IS 'Сопоставление';
COMMENT ON COLUMN spr_org.employee_count IS 'штатная численность';
COMMENT ON COLUMN spr_org.outstaff_count IS 'внештатных сотрудников';

CREATE TABLE spr_subsystem
(
  id          SERIAL            NOT NULL
    CONSTRAINT spr_subsystem_pkey
    PRIMARY KEY,
  name        VARCHAR(50) NOT NULL,
  sort_order  INTEGER DEFAULT 1,
  level1_name VARCHAR(50) NOT NULL,
  level1_id   INTEGER NOT NULL
);
COMMENT ON TABLE spr_subsystem IS 'Справочник измерения Подсистема';

CREATE TABLE IF NOT EXISTS spr_object_type (
  id      SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);
COMMENT ON TABLE spr_object_type IS 'Справочник типов Oбъект';

CREATE TABLE IF NOT EXISTS spr_object (
  id        SERIAL PRIMARY KEY,
  type_id   INT NOT NULL
  CONSTRAINT FK_SPR_OBJECT_TYPE
  REFERENCES spr_object_type,
  name      VARCHAR(50) NOT NULL UNIQUE,
  sort_order INT DEFAULT 1
);
CREATE UNIQUE INDEX spr_object_name_type_id_uindex
  ON public.spr_object (name, type_id);
COMMENT ON TABLE spr_object IS 'Справочник измерения Oбъект';

CREATE TABLE IF NOT EXISTS spr_cart_value (
  id      SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  sort_order INT DEFAULT 1
);
COMMENT ON TABLE spr_cart_value IS 'Справочник измерения Показатель карточки';

-- старые foms7 справочники
CREATE TABLE SPR_REGION
(
  region_id     INT           NOT NULL
    CONSTRAINT PK_SPR_REGION
    PRIMARY KEY,
  r_name        VARCHAR(100)  NOT NULL,
  country_id    INT           NOT NULL,
  country_name  VARCHAR(100),
  district_id   INT,
  district_name VARCHAR(100),
  r_order       INT           NOT NULL,
  okato         INT,
  is_visible    BOOLEAN DEFAULT true NOT NULL,
  tf_code       INT,
  okato_code    VARCHAR(10)
);

CREATE TABLE SPR_VIEW_MO
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_VIEW_MO
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(100)  NOT NULL,
  v_name_short VARCHAR(500),
  v_key        VARCHAR(100),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_VMP_GROUP
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_VMP_GROUP
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(13)   NOT NULL,
  v_name_short VARCHAR(10),
  v_key        VARCHAR(50),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_VMP_PROFIL
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_VMP_PROFIL
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(100)  NOT NULL,
  v_name_short VARCHAR(100),
  v_key        VARCHAR(100),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_MO
(
  mo_id     INT NOT NULL
    CONSTRAINT PK_SPR_MO
    PRIMARY KEY,
  NAME      VARCHAR(200),
  ORD       INT,
  TF_OKATO  VARCHAR(10),
  TERRID    INT,
  MCOD      VARCHAR(10),
  NAME_FULL VARCHAR(400),
  INN       VARCHAR(12),
  OGRN      VARCHAR(15),
  KPP       VARCHAR(9),
  INDEX_J   VARCHAR(6),
  ADDR_J    VARCHAR(400),
  OKOPF     VARCHAR(5),
  VEDPRI    INT,
  ORG       INT,
  FAM_RUK   VARCHAR(100),
  IM_RUK    VARCHAR(100),
  OT_RUK    VARCHAR(100),
  PHONE     VARCHAR(100),
  FAX       VARCHAR(100),
  E_MAIL    VARCHAR(100),
  WWW       VARCHAR(100),
  D_BEGIN   TIMESTAMP WITH TIME ZONE,
  D_END     TIMESTAMP WITH TIME ZONE,
  D_UVED    TIMESTAMP WITH TIME ZONE,
  D_EDIT    TIMESTAMP WITH TIME ZONE,
  IS_MAIN   SMALLINT,
  PARENT_id INT
);
CREATE INDEX I_SPR_MO_CODE  ON SPR_MO (MCOD);
CREATE INDEX I_SPR_MO_PARENT  ON SPR_MO (PARENT_id);

CREATE TABLE SPR_DATE
(
  date_id       SERIAL
    CONSTRAINT PK_SPR_DATE
    PRIMARY KEY,
  dt_value      DATE         NOT NULL,
  dt_year       INT          NOT NULL,
  dt_half_year  INT          NOT NULL,
  dt_quarter    INT          NOT NULL,
  dt_month      INT          NOT NULL,
  dt_month_name VARCHAR(50) NOT NULL,
  dt_day        INT          NOT NULL
);
CREATE INDEX I_SPR_DATE_VALUE  ON SPR_DATE (dt_value);
CREATE INDEX I_SPR_DATE_YEAR_MONTH_DAY  ON SPR_DATE (dt_year, dt_month, dt_day);

CREATE TABLE SPR_VMP_VIEW_NEW
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_VMP_VIEW_NEW
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(600)  NOT NULL,
  v_name_short VARCHAR(500),
  v_key        VARCHAR(100),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_VMP_METHOD_NEW
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_VMP_METHOD_NEW
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(350)  NOT NULL,
  v_name_short VARCHAR(500),
  v_key        VARCHAR(100),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_AGE
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_AGE
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(20)   NOT NULL,
  v_name_short VARCHAR(20),
  v_min        INT           NOT NULL,
  v_max        INT           NOT NULL,
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE SPR_GENDER
(
  id           INT           NOT NULL
    CONSTRAINT PK_SPR_GENDER
    PRIMARY KEY,
  parent_id    INT,
  v_name       VARCHAR(20)   NOT NULL,
  v_name_short VARCHAR(20),
  v_key        VARCHAR(20),
  v_order      INT DEFAULT 1 NOT NULL,
  is_visible   BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE employee
(
  id                   SERIAL       NOT NULL
    CONSTRAINT pk_employee
    PRIMARY KEY,
  guid                 VARCHAR(36)  NOT NULL,
  position             VARCHAR(256) NOT NULL,
  rate                 INTEGER,
  snils                CHAR(14)     NOT NULL,
  salary_accrual_count INTEGER,
  salary_payment_count INTEGER,
  org_inn              VARCHAR(20),
  position_guid        VARCHAR(36),
  data_area            INTEGER,
  sort_order           INTEGER DEFAULT 0
);
CREATE UNIQUE INDEX employee_guid_uindex  ON employee (guid);
CREATE INDEX employee_snils_index  ON employee (snils);

/*
-- Создание триггерной функции
CREATE FUNCTION trigger_fact_employee_cart_update () RETURNS trigger AS
'BEGIN
   NEW.mandatory_count := 0;
   IF ( NEW.has_id_number>0 ) THEN
     NEW.mandatory_count := NEW.mandatory_count + 1;
   END IF;
return NEW;
END;
' LANGUAGE  plpgsql;

-- Создание триггера для подсчета обязат и необязат полей при вставке
CREATE TRIGGER tr_fact_employee_cart_update
AFTER INSERT OR UPDATE ON fact_employee_cart FOR EACH ROW
EXECUTE PROCEDURE trigger_fact_employee_cart_update();
*/

-----------------------------------------
--------------- КУБЫ---------------------
-----------------------------------------
--        КУБ - дисциплина бухгалтерского учета
CREATE TABLE fact_cube_buh
(
  id                   SERIAL                                   NOT NULL
    CONSTRAINT cube_fact_buh_pkey
    PRIMARY KEY,
  data_area            INTEGER                                  NOT NULL,
  operation_count      INTEGER,
  hand_operation_count INTEGER,
  correction_count     INTEGER,
  last_operation_date  TIMESTAMP,
  account_balance      NUMERIC(32, 2),
  closed_date          TIMESTAMP,
  undefined_id         INTEGER DEFAULT '-2147483648' :: INTEGER NOT NULL,
  subsystem_id         INTEGER
    CONSTRAINT fact_cube_buh_spr_subsystem_id_fk
    REFERENCES spr_subsystem,
  org_id               INTEGER
    CONSTRAINT fact_cube_buh_spr_org_id_fk
    REFERENCES spr_org,
  calendar_id          INTEGER
    CONSTRAINT fact_cube_buh_spr_date_date_id_fk
    REFERENCES spr_date,
  closed_periods_count INTEGER
);
COMMENT ON COLUMN fact_cube_buh.data_area IS 'область данных';
COMMENT ON COLUMN fact_cube_buh.operation_count IS 'кол-во операций';
COMMENT ON COLUMN fact_cube_buh.hand_operation_count IS 'кол-во ручных операций';
COMMENT ON COLUMN fact_cube_buh.correction_count IS 'кол-во коррекций';
COMMENT ON COLUMN fact_cube_buh.account_balance IS 'Сумма остатков на лицевых счетах на начало года';
COMMENT ON COLUMN fact_cube_buh.subsystem_id IS 'подсистема';
COMMENT ON COLUMN fact_cube_buh.org_id IS 'организация';
COMMENT ON COLUMN fact_cube_buh.calendar_id IS 'период';

--        КУБ - статистика по карточке сотрудника
CREATE TABLE fact_employee_cart
(
  id                SERIAL  NOT NULL
    CONSTRAINT cube_vmp_fact6_pkey
    PRIMARY KEY,
  data_area         INTEGER NOT NULL,
  org_id            INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact6_spr_org_id_fk
    REFERENCES spr_org,
  calendar_id       INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact6_spr_date_date_id_fk
    REFERENCES spr_date,
  undefined_id      INTEGER DEFAULT '-2147483647' :: INTEGER,
  has_mandatory_5   VARCHAR,
  has_mandatory_8   VARCHAR,
  employee_guid     VARCHAR,
  cart_value_id     INTEGER NOT NULL
    CONSTRAINT fact_employee_cart_spr_cart_value_id_fk
    REFERENCES spr_cart_value,
  cart_value_filled VARCHAR,
  staff_guid        VARCHAR
);
CREATE UNIQUE INDEX fact_employee_cart_data_area_org_id_calendar_id_employee_guid_u
  ON fact_employee_cart (data_area, org_id, calendar_id, employee_guid, cart_value_id);
COMMENT ON COLUMN fact_employee_cart.has_mandatory_5 IS 'если заполнены 5 обязательных атрибутов';
COMMENT ON COLUMN fact_employee_cart.has_mandatory_8 IS 'если заполнены 8 обязательных атрибутов';
COMMENT ON COLUMN fact_employee_cart.cart_value_filled IS ' если заполнен (true) соответствующий атрибут карточки';
COMMENT ON COLUMN fact_employee_cart.staff_guid IS 'кол-во штатных сотрудников';

--        КУБ - сведения по кадрам
CREATE TABLE fact_staff_details
(
  id                        SERIAL  NOT NULL
    CONSTRAINT cube_vmp_fact5_pkey
    PRIMARY KEY,
  calendar_id               INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact5_spr_date_date_id_fk
    REFERENCES spr_date,
  org_id                    INTEGER
    CONSTRAINT cube_vmp_fact5_spr_org_id_fk
    REFERENCES spr_org,
  undefined_id              INTEGER DEFAULT '-2147483647' :: INTEGER,
  has_position_count        INTEGER,
  employee_count            INTEGER NOT NULL,
  position_guid_count       INTEGER NOT NULL,
  rate_sum                  INTEGER NOT NULL,
  total_staff_positions     INTEGER NOT NULL,
  total_full_time_positions NUMERIC(10, 2),
  faired_count              INTEGER DEFAULT 0,
  department_id             INTEGER
    CONSTRAINT fact_staff_details_spr_department_id_fk
    REFERENCES spr_department,
  position_id               INTEGER
    CONSTRAINT fact_staff_details_spr_position_id_fk
    REFERENCES spr_position
);
COMMENT ON COLUMN fact_staff_details.has_position_count IS 'пользователей с заполненным position';
COMMENT ON COLUMN fact_staff_details.position_guid_count IS 'Количество уникальных не пустых positionGUID';
COMMENT ON COLUMN fact_staff_details.rate_sum IS 'Сумма долей ставок (rate), на которые приняты сотрудники';
COMMENT ON COLUMN fact_staff_details.total_full_time_positions IS 'cумма долей ставок в штатном расписании ';
COMMENT ON COLUMN fact_staff_details.faired_count IS 'уволенные';


-- КУБ Дисциплина ведения кадрового учета
CREATE TABLE fact_staff_stat
(
  id                  SERIAL  NOT NULL
    CONSTRAINT fact_staff_stat_id_pk
    PRIMARY KEY,
  data_area           INTEGER NOT NULL,
  staff_doc_percent   NUMERIC(5, 2),
  two_pays_count      VARCHAR,
  two_pays_percent    NUMERIC(5, 2),
  two_accrues_percent NUMERIC(5, 2),
  two_accrues_count   VARCHAR,
  has_staff_doc       VARCHAR,
  employee_guid       VARCHAR,
  guid_with_position  VARCHAR,
  subsystem_id        INTEGER,
    CONSTRAINT fact_staff_stat_spr_subsystem_id_fk
    REFERENCES spr_subsystem,
  org_id              INTEGER
    CONSTRAINT fact_staff_stat_spr_org_id_fk
    REFERENCES spr_org,
  calendar_id         INTEGER
    CONSTRAINT fact_staff_stat_spr_date_date_id_fk
    REFERENCES spr_date,
  undefined_id        INTEGER DEFAULT '-2147483647' :: INTEGER
);

-- промежуточные факты generic_indicators из сервиса
CREATE TABLE facts_generic_indicators
(
  id                             SERIAL    NOT NULL
    CONSTRAINT facts_generic_indicators_pkey
    PRIMARY KEY,
  inn                            VARCHAR   NOT NULL,
  total_staff_positions          INTEGER   NOT NULL,
  total_full_time_positions      NUMERIC(10, 2) NOT NULL,
  hrm_closing_date               DATE      NOT NULL,
  payroll_closing_date           DATE      NOT NULL,
  salary_accounting_closing_date DATE      NOT NULL,
  last_hrm_operation_date        DATE      NOT NULL,
  last_payroll_operation_date    DATE      NOT NULL,
  data_area                      INTEGER   NOT NULL,
  updated_date                   TIMESTAMP NOT NULL,
  period_end                     DATE      NOT NULL
);
CREATE UNIQUE INDEX facts_generic_indicators_inn_period_end_uindex
  ON facts_generic_indicators (inn);
COMMENT ON TABLE facts_generic_indicators IS 'сведения по датам операций, датам закрытия периодов и показатели штатного расписания';

-- справочник сотрудников
CREATE TABLE employee
(
  id                   SERIAL       NOT NULL
    CONSTRAINT pk_employee
    PRIMARY KEY,
  guid                 VARCHAR(36)  NOT NULL,
  position             VARCHAR(256) NOT NULL,
  rate                 INTEGER,
  snils                CHAR(14)     NOT NULL,
  salary_accrual_count INTEGER,
  salary_payment_count INTEGER,
  name                 VARCHAR(256),
  org_inn              VARCHAR(20),
  position_guid        VARCHAR(36)
);
CREATE UNIQUE INDEX employee_guid_uindex
  ON employee (guid);

--временная для ежедневных показателей
CREATE TABLE load_daily
(
  last_operation_date DATE                    NOT NULL,
  org_inn             VARCHAR(20)             NOT NULL,
  date_end            DATE                    NOT NULL,
  updated             TIMESTAMP DEFAULT now() NOT NULL,
  CONSTRAINT fact_daily_org_id_calendar_id_pk
  PRIMARY KEY (org_inn, date_end)
);

-- куб активность пользователей
CREATE TABLE fact_user_activity
(
  id                      SERIAL  NOT NULL
    CONSTRAINT cube_vmp_fact2_pkey
    PRIMARY KEY,
  days_from_last_activity INTEGER,
  online_time             INTEGER,
  login_count             INTEGER,
  subsystem_id            INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact2_spr_subsystem_id_fk
    REFERENCES spr_subsystem,
  org_id                  INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact2_spr_org_id_fk
    REFERENCES spr_org,
  calendar_id             INTEGER NOT NULL
    CONSTRAINT cube_vmp_fact2_spr_date_date_id_fk
    REFERENCES spr_date,
  undefined_id            INTEGER DEFAULT '-2147483647' :: INTEGER
);

CREATE TABLE fact_connections
(
  id                SERIAL NOT NULL
    CONSTRAINT cube_vmp_fact1_pkey
    PRIMARY KEY,
  new_users_count   INTEGER,
  users_count_total INTEGER,
  org_id            INTEGER
    CONSTRAINT cube_vmp_fact1_spr_org_id_fk
    REFERENCES spr_org,
  undefined_id      INTEGER   DEFAULT '-2147483647' :: INTEGER,
  calendar_id       INTEGER
    CONSTRAINT cube_vmp_fact1_spr_date_date_id_fk
    REFERENCES spr_date,
  last_updated      TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE fact_connections IS 'Сведения о подключении пользователей организации ';
COMMENT ON COLUMN fact_connections.new_users_count IS 'Количество зарегистрированных пользователей';
COMMENT ON COLUMN fact_connections.users_count_total IS 'Количество зарегистрированных пользователей всего';
COMMENT ON COLUMN fact_connections.org_id IS 'организация';
COMMENT ON COLUMN fact_connections.calendar_id IS 'период';

CREATE TABLE fact_object_stat
(
  id                SERIAL NOT NULL
    CONSTRAINT fact_object_stat_pkey
    PRIMARY KEY,
  reports_generated INTEGER,
  objects_updated   INTEGER,
  objects_created   INTEGER,
  subsystem_id      INTEGER
    CONSTRAINT fact_object_stat_spr_subsystem_id_fk
    REFERENCES spr_subsystem,
  object_id         INTEGER
    CONSTRAINT fact_object_stat_spr_object_id_fk
    REFERENCES spr_object,
  org_id            INTEGER
    CONSTRAINT fact_object_stat_spr_org_id_fk
    REFERENCES spr_org,
  calendar_id       INTEGER
    CONSTRAINT fact_object_stat_spr_date_date_id_fk
    REFERENCES spr_date,
  undefined_id      INTEGER   DEFAULT '-2147483647' :: INTEGER,
  last_updated      TIMESTAMP DEFAULT now()
);

-- % function
CREATE OR REPLACE FUNCTION percent(part BIGINT, total BIGINT)
  RETURNS FLOAT AS $$
BEGIN
  IF (total = 0)
  THEN
    RETURN 0;
  END IF;
  RETURN part * 100.0 / total;
END;
$$ LANGUAGE plpgsql;

-- Склонения показателей
CREATE TABLE if NOT EXISTS measure_decline
(
	decline_id serial NOT NULL
		CONSTRAINT measure_decline_pkey
			PRIMARY KEY,
	unit_id INTEGER NOT NULL,
	p1 VARCHAR(20),
	p2 VARCHAR(20),
	p3 VARCHAR(20)
)
;

comment ON TABLE measure_decline IS 'Склонения показателей'
;

-- fact_salary - структура ЗП
CREATE TABLE fact_salary
(
  calendar_id   INTEGER                                  NOT NULL
    CONSTRAINT fact_salary_spr_date_date_id_fk
    REFERENCES spr_date,
  org_id        INTEGER                                  NOT NULL
    CONSTRAINT fact_salary_spr_org_id_fk
    REFERENCES spr_org,
  depart_id     INTEGER                                  NOT NULL
    CONSTRAINT fact_salary_spr_department_id_fk
    REFERENCES spr_department,
  position_id   INTEGER                                  NOT NULL
    CONSTRAINT fact_salary_spr_position_id_fk
    REFERENCES spr_position,
  snils         VARCHAR(20)                              NOT NULL,
  id            SERIAL                                   NOT NULL
    CONSTRAINT fact_salary_id_pk
    PRIMARY KEY,
  payed_salary  NUMERIC(20, 2),
  undefined_id  INTEGER DEFAULT '-2147483647' :: INTEGER NOT NULL,
  employee_guid VARCHAR(40),
  accrual_id    INTEGER
    CONSTRAINT fact_salary_spr_accrual_id_fk
    REFERENCES spr_accrual
);
CREATE INDEX fact_salary_calendar_id_index
  ON fact_salary (calendar_id);
CREATE INDEX fact_salary_snils_index
  ON fact_salary (snils);
COMMENT ON TABLE fact_salary IS 'Структура заработной платы';
COMMENT ON COLUMN fact_salary.payed_salary IS 'произведенные начисления по заработной плате'

-- учет рабочего времени
CREATE TABLE fact_worktime
(
  id                SERIAL                                   NOT NULL
    CONSTRAINT fact_worktime_id_pk
    PRIMARY KEY,
  calendar_id       INTEGER                                  NOT NULL
    CONSTRAINT fact_worktime_spr_date_date_id_fk
    REFERENCES spr_date,
  org_id            INTEGER                                  NOT NULL
    CONSTRAINT fact_worktime_spr_org_id_fk
    REFERENCES spr_org,
  depart_id         INTEGER                                  NOT NULL
    CONSTRAINT fact_worktime_spr_department_id_fk
    REFERENCES spr_department,
  position_id       INTEGER                                  NOT NULL
    CONSTRAINT fact_worktime_spr_position_id_fk
    REFERENCES spr_position,
  snils             VARCHAR(20)                              NOT NULL,
  work_time         NUMERIC(11, 2),
  listing_count     INTEGER,
  listing_count_avg NUMERIC(11, 2),
  undefined_id      INTEGER DEFAULT '-2147483647' :: INTEGER NOT NULL,
  employee_guid     VARCHAR(40)
);
CREATE INDEX fact_worktime_calendar_id_index
  ON fact_worktime (calendar_id);
CREATE INDEX fact_worktime_employee_guid_index
  ON fact_worktime (employee_guid);
COMMENT ON TABLE fact_worktime IS 'куб сведений о рабочем времени';
COMMENT ON COLUMN fact_worktime.listing_count IS 'списочная численность';
COMMENT ON COLUMN fact_worktime.listing_count_avg IS 'для среднесписочной численности будет расчитано автоматом';

-- подразделения
CREATE TABLE spr_department
(
  id         SERIAL       NOT NULL
    CONSTRAINT spr_department_pkey
    PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  sort_order INTEGER DEFAULT 0,
  guid       VARCHAR(40)  NOT NULL
);
CREATE UNIQUE INDEX spr_department_name_uindex
  ON spr_department (name);
CREATE UNIQUE INDEX spr_department_guid_uindex
  ON spr_department (guid);

--справочник должностей
CREATE TABLE spr_position
(
  id                SERIAL       NOT NULL
    CONSTRAINT spr_position_pkey
    PRIMARY KEY,
  name              VARCHAR(256) NOT NULL,
  sort_order        INTEGER DEFAULT 0,
  position_group_id INTEGER,
  position_group    VARCHAR(100)
);
CREATE UNIQUE INDEX spr_position_name_uindex
  ON spr_position (name);
COMMENT ON TABLE spr_position IS 'должности';

-- куб Структура ЗП по должности

CREATE TABLE fact_position_salary
(
  position_id  INTEGER
    CONSTRAINT fact_position_salary_spr_position_id_fk
    REFERENCES spr_position,
  calendar_id  INTEGER
    CONSTRAINT fact_position_salary_spr_date_date_id_fk
    REFERENCES spr_date,
  id           BIGSERIAL NOT NULL
    CONSTRAINT fact_position_salary_pkey
    PRIMARY KEY,
  salary       NUMERIC(11, 2),
  undefined_id INTEGER DEFAULT '-2147483647' :: INTEGER,
  accrual_id   INTEGER
    CONSTRAINT fact_position_salary_spr_accrual_id_fk
    REFERENCES spr_accrual
);
COMMENT ON TABLE fact_position_salary IS 'зп по должности';

-- вспомогательная для храниения массива positions из выдачи GenericIndicators
CREATE TABLE facts_generic_positions
(
  id                BIGSERIAL   NOT NULL
    CONSTRAINT facts_generic_positions_pkey
    PRIMARY KEY,
  inn               VARCHAR(10) NOT NULL,
  fulltimepositions INTEGER,
  position_id       INTEGER
    CONSTRAINT facts_generic_positions_spr_position_id_fk
    REFERENCES spr_position,
  department_id     INTEGER
    CONSTRAINT facts_generic_positions_spr_department_id_fk
    REFERENCES spr_department,
  data_area         INTEGER,
  last_updated      TIMESTAMP DEFAULT now(),
  period_end        DATE
);

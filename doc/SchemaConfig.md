в \saiku-core\saiku-service\src\main\java\org\saiku\database\Database.java (управляет источниками данных)
- Отключил загрузку демо-данных в методе init
- Добавил метод loadFoms, создающий новый источник данных и его вызов в init

в \saiku-core\saiku-web\src\main\java\org\saiku\web\rest\resources\AdminResource.java (контроллер для работы со схемами)
- В методе getSavedSchema убрал добавление заголовка content-disposition, метод использовался для скачивания файла схемы

Добавил \saiku-ui\js\saiku\views\SchemaConfig.js и подключил его в \saiku-ui\index.html и \saiku-ui\pom.xml

в \saiku-ui\js\saiku\models\SessionWorkspace.js заменил создание SplashScreen (стартовая страница saiku) на SchemaConfig

Saiku использует для работы с данными mondrian 4, и его язык описания схем
[Документация тут:](http://mondrian.pentaho.com/head/documentation/schema.php)

В общем виде схема выглядит так:

заголовок (все тэги чувствительны к регистру!!!)
```xml
<Schema name="foms" metamodelVersion="4.0"> 
```
Здесь описываются используемые таблицы, запросы, представления и т.п.
```xml
  <PhysicalSchema> 
    <Table schema="dbo" name="DctRegion"/>
    <Query alias="OMS_MO_TYPE_2017">
      <ExpressionView>
        <SQL dialect="mssql"><![CDATA[
select "KEY", VERSION, cast (NAME as varchar) as NAME, DICT_KEY, ORD, PARENT_KEY, INDATE, OUTDATE from dbo.dict_elems('OMS_MO_TYPE_2017')
          ]]></SQL>
      </ExpressionView>
    </Query>
  </PhysicalSchema>
```
Дальше описываем измерения. Измерения можно описывать прямо на месте использования, 
а можно здесь описать все и в дальнейшем ссылаться на них, см. ниже, называется shared dimensions
```xml
  <Dimension table="DctRegion" key="terr_key" name="terr"> <!-- key - это имя ключевого атрибута, описанного внутри -->
    <Attributes>                                           
      <Attribute name="terr_key" column="Name">            <!-- вот этого атрибута -->
        <Key>
          <Column name="Id"/>                              <!-- а в нем уже ключевое поле -->
        </Key>
        <Name>
          <Column name="Name"/>                            <!-- и поле с именем. -->
        </Name>                                            <!-- Так же есть тэги для полей сортировки и т.д, см. документацию -->
      </Attribute>
    </Attributes>
  </Dimension>
```
это собственно куб. один. Внутри можно описать группы показателей. виртуальные кубы в четвертом мондриане отменили
```xml
  <Cube name="cube1" visible="true" cache="true" enabled="true">
```
Cсылки на измерения, описанные ранее. Можно указывать ссылки только на некоторые измерения.
```xml
    <Dimensions> 
      <Dimension source="terr"/>
      <Dimension source="mo"/>
      <Dimension source="prof"/>
      <Dimension source="vedpri"/>
      <Dimension source="dt"/>
      <Dimension source="gr"/>
    </Dimensions>
```
 группы измерений, как бы подкубы в мультикубе
```xml
    <MeasureGroups>
      <MeasureGroup name="mg2" table="MV_EO_FEU_FACT" schema="dbo">          <!-- группа показателей по одной таблице фактов -->
        <DimensionLinks>                                                     <!-- здесь используемые измерения и соответствующие ключевые поля -->
          <ForeignKeyLink dimension="terr" foreignKeyColumn="terr_key"/>     <!-- количество ссылок должно совпадать с количеством -->
          <ForeignKeyLink dimension="mo" foreignKeyColumn="mo_key"/>         <!-- описанных измерений в <Dimensions> -->
          <ForeignKeyLink dimension="vedpri" foreignKeyColumn="vedpri_key"/>
          <ForeignKeyLink dimension="prof" foreignKeyColumn="prof_key"/>
          <ForeignKeyLink dimension="gr" foreignKeyColumn="gr_key"/>
          <ForeignKeyLink dimension="dt" foreignKeyColumn="dt"/>
        </DimensionLinks>
        <Measures>
          <Measure name="fact_vol" column="fact_vol" aggregator="sum" visible="true"/> <!-- здесь показатели -->
          <Measure name="fact_sum" column="fact_sum" aggregator="sum" visible="true"/>
          <Measure name="srok" column="srok" aggregator="distinct-count" visible="true"/>
          <Measure name="cnt" column="cnt" aggregator="distinct-count" visible="true"/>
        </Measures>
      </MeasureGroup>
      <MeasureGroup name="mg1" table="MV_EO_MO_KEYS" schema="dbo">  - еще одна группа
```
	...
```xml	
      </MeasureGroup>
    </MeasureGroups>
  </Cube>

</Schema>`
```

Конфигуратор схемы сейчас получает умолчательную схему (default.xml) в которой должны быть описаны все возможные измерения
и группы показателей. Затем чистит секцию <Cube> и заполняет ее заново в соответствии с выбранными показателями.
В секции <Dimensions> и <DimensionLinks> заполняет измерения, которые присутытвуют во всех (в каждом) кубах содержащих
выбранные показатели. Затем сохраняет сгенерированную схему как foms.xml

foms.xml подключен в классе Database в строке подключения

создал два ресурса \saiku-core\saiku-service\src\main\resources\foms.xml и default.xml


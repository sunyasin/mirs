# Разворачивание приложения OLAP на чистой системе 
>>Ilyushchenko, Alexandr V.
>>Высылаю инструкцию по запуску приложения.


1. Установка базового ПО : установить JDK 1.8, Tomcat 8.5, Libre Office 5.4

2. Копирование скриптов для экспорта в открытые форматы: скопировать папку unoconv в C:\unoconv

3. Копирование плагинов для фронтенда: скопировать папку plugins в C:\plugins
(путь к ней - в saiku-bean.properties)

4. Установка Java параметров для приложения:

-Duser.region=RU

-Dfile.encoding=UTF-8

-Duser.language=ru

-Duser.country=RU

5. Установка региональных настроек: в Windows переидти в региональные настройки, вкладка Administrative, пункт Change system locale, в выпадающем списке выбрать Russian

6. Файл settings.xml во вложении, но нужно под себя подгонять всё.

7. Изначально проект собирал из окошка мавена, почему то конфиг для сборки написать не получилось. Не забудь поставить галку Skip tests.


Проблемы: 
- бывает на Ubuntу ругается на конфликт log4j библиотек. Решение - удалить log4j-over-slf4j 
- если ругается на циклические зависимости в классах с именем *ASN1* - нужно удалить bcprov-jdk15-1.45.jar
- если ClassNotFound с Level1 в имени, то в saiku-queryxxx.jar нужно удалить папку mondrian (конфликт с ней)

Сейчас пока все это не исправлено в сборке я делаю так:
- переименовываю архив приложения в saiku.war 
- захожу в web-inf/lib и удаляю из него log4j-over-slf4j и bcprov-jdk15-1.45.jar
- удаляю webapps/saiku
- запускаю tomcat
- после развертывания saiku, копирую из /home/mirs-admin/1/saiku-queryxxx.jar в web-inf/lib
- перезапускаю томкат



# 1
## 2
### 3
222
> формат **edit** в _эту_ цитату

[установите Git](http://git-scm.com/downloads) 
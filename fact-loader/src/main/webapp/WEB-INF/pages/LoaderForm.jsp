<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        .error {
            color: #ff0000;
        }

        .errorblock {
            color: #000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }

    </style>
</head>

<body>
<h2>MIRS fact loader</h2>
<p>Please, select what to load and press Load button</p>

<form:form method="GET" action="/loader/api/log" commandName="log-data">
    <form:errors path="*" cssClass="errorblock" element="div"/>
    <table>
        <tr>
            <td>End date (default is today)? :</td>
            <td><form:input path="logDate"/></td>
            <td><form:errors path="logDate" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Module name:</td>
            <td>
                <form:select path="moduleName">
                    <form:option value="" label="--- Select ---"/>
                    <form:options items="${moduleList}"/>
                </form:select>
            </td>
            <td><form:errors path="moduleName" cssClass="error"/></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit"/></td>
        </tr>
    </table>
</form:form>

<form:form method="POST" action="/loader/api/load" commandName="loader-data">

    <form:errors path="*" cssClass="errorblock" element="div"/>

    <table>
        <tr>
            <td>End date (default is today)? :</td>
            <td><form:input path="endPeriod"/></td>
            <td><form:errors path="endPeriod" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load Acounting data? :</td>
            <td><form:checkbox path="doLoadAccounting"/></td>
            <td><form:errors path="doLoadAccounting" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load Generic indicators ? :</td>
            <td><form:checkbox path="doLoadGenericIndicators"/></td>
            <td><form:errors path="doLoadGenericIndicators" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load HRM indicators (NON-CORP, BIG!!!)? :</td>
            <td><form:checkbox path="doLoadHrmIndicators"/></td>
            <td><form:errors path="doLoadHrmIndicators" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load HRM indicators (CORP, small)? :</td>
            <td><form:checkbox path="doLoadHrmIndicatorsCorp"/></td>
            <td><form:errors path="doLoadHrmIndicatorsCorp" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load HRM indicators with DATES(may be longtime!)? :</td>
            <td><form:checkbox path="doLoadHrmIndicatorsAndWorkTime"/></td>
            <td><form:errors path="doLoadHrmIndicatorsAndWorkTime" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Load Daily indicators :</td>
            <td><form:checkbox path="doLoadDaily"/></td>
            <td><form:errors path="doLoadDaily" cssClass="error"/></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit"/></td>
        </tr>
    </table>
</form:form>

</body>
</html>
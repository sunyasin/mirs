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

<form:form method="POST" action="/load" commandName="loader-data">

    <form:errors path="*" cssClass="errorblock" element="div"/>

    <table>
        <tr>
            <td>Дата конца периода (по умолчанию - сегодня)? :</td>
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
            <td colspan="3"><input type="submit"/></td>
        </tr>
    </table>
</form:form>

</body>
</html>
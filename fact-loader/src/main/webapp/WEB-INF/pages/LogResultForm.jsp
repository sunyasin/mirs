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
            <td>End date in YYYYMMDD (default is today)? :</td>
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

Log:
<table border="1">
    <tr>
        <td>Date</td>
        <td>module</td>
        <td>Type</td>
        <td>Message</td>
    </tr>
    <c:forEach var="listValue" items="${row_list}">
        <tr valign="top">
            <td>${listValue.dateTime}</td>
            <td>${listValue.module}</td>
            <td>${listValue.msgType}</td>
            <td>${listValue.msg}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
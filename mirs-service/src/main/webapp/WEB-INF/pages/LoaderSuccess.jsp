<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>result</h2>

<table>

    <tr>
        <td>Favourite Web Frameworks :</td>
        <td>
            <c:forEach items="${customer.favFramework}" var="current">
                [<c:out value="${current}"/>]
            </c:forEach>
        </td>
    </tr>


</table>

</body>
</html>
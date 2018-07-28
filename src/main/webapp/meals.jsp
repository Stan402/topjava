<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table cellspacing="10">
    <tr>
        <th>Description</th>
        <th>Date/time</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
    <c:forEach var="m" items="${meals}">
        <tr style="color: ${m.exceed ? 'red' : 'green'}">
            <td>${m.description}</td>
            <td>
                <fmt:parseDate value="${ m.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>${m.calories}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
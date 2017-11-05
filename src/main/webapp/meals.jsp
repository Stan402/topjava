<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal list</title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <td>ID</td>
        <td>Date & Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Actions</td>
    </tr>
    </thead>
    <tbody>
    <%--@elvariable id="meals" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"--%>
    <%--@elvariable id="formatter" type="java.time.format.DateTimeFormatter"--%>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.exceed ? 'red':'green'}">
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${meal.dateTime.format(formatter)}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td>
                <a href="meals?action=update&id=<c:out value="${meal.id}"/>">Update</a>
                |
                <a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br><br>
<h3><a href="meals?action=add">Add meal</a></h3>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<h2>${meal.id==0?"Add meal":"Edit meal"}</h2>

<form method="post" action="meals" name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    Description: <input type="text" name="description" value="${meal.description}"/>
    <br/><br/>
    DateTime: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/>
    <br/><br/>
    Calories: <input type="text" name="calories" value="${meal.calories}"/>
    <br/><br/>
    <input type="submit" value="Submit">
</form>

</body>
</html>

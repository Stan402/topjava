<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.Meal"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${meal.id > 0? 'Update Meal' : 'Add Meal'}</h2>

<form method="post">
    <input type="hidden" name="id" value="${meal.id}">

    <table>
        <tbody>
        <tr>
            <td><label>DateTime:</label></td>
            <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
        </tr>
        <tr>
            <td><label>Description:</label></td>
            <td><input type="text" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td><label>Calories:</label></td>
            <td><input type="number" name="calories" value="${meal.calories}"/>
        </tr>
        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </tbody>
    </table>
</form>
<br><br>
<h3><a href="meals">Cancel</a></h3>

</body>
</html>

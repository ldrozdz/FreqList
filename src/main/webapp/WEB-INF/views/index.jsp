<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Frequency list generator</title>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>

<div id="input">
    <form id="form" action="." method="post" enctype="multipart/form-data">
            <p><input type="file" name="file" size="45" id="file"/><br/></p>
            <p><input type="checkbox" id="lowercase" name="lowercase" value="ci">Case-insensitive</input><br/></p>
            <p><input type="submit"/></p>
    </form>
</div>
<div id="output">
    <table style="border: 1px solid black">
    <c:forEach var="entry" items="${it}">
        <tr>
            <td><c:out value="${entry.key}"/></td>
            <td><c:out value="${entry.value}"/></td>
        </tr>
    </c:forEach>
    </table>
</div>
</body>
</html>
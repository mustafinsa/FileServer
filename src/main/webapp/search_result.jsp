<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search result</title>
</head>
<body>

<a href="index.html" >Main page</a>

<h2>Search results:</h2>
<table>
    <tr>
        <th>Id</th>
        <th>file name</th>
    </tr>

    <c:forEach var="pair" items="${requestScope.search_result}" varStatus="index">
        <tr>
            <td>${pair.key}</td>
            <td>${pair.value}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

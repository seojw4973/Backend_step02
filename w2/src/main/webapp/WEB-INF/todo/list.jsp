<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bitcamp1
  Date: 2024-04-11
  Time: 오전 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo List</title>
</head>
<body>
<h1>Todo List</h1>
<h2>${appName}</h2>
<h2>${loginInfo}</h2>
<h3>${loginInfo.mname}</h3>

    <ul>
        <c:forEach items="${dtoList}" var="dto">
            <li>
                <span><a href="/todo/read?tno=${dto.tno}">${dto.tno}</a> </span>
                <span>${dto.title}</span>
                <span>${dto.dueDate}</span>
                <span>${dto.finished? "DONE" : "NOT YET"}</span>
            </li>
        </c:forEach>
    </ul>
<form action="/logout" method="post">
    <button>로그아웃</button>
</form>
</body>
</html>

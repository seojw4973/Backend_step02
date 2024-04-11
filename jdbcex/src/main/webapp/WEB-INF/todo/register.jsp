<%--
  Created by IntelliJ IDEA.
  User: bitcamp1
  Date: 2024-04-11
  Time: 오전 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/todo/register" method="post">
    <div>
        <input type="text" name="title" placeholder="INSERT TITLE">
    </div>
    <div>
        <input type="date" name="dueDate">
    </div>
    <div>
        <button type="submit">등록</button>
        <button type="reset">초기화</button>
    </div>



</form>

</body>
</html>

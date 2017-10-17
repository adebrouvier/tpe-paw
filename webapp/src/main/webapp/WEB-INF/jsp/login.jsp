<%--
  Created by IntelliJ IDEA.
  User: amoragues
  Date: 16/10/17
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:url value="/login" var="loginUrl" />
<form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
    <div>
        <label for="username">Username: </label>
        <input id="username" name="j_username" type="text"/>
    </div>
    <div>
        <label for="password">password: </label>
        <input id="password" type="password" name="j_password" type="text"/>
    </div>
    <div>
        <label><input name="j_rememberme" type="checkbox"/><spring:message code="login.remember_me"/> </label>
    </div>
    <div>
    <input type="submit" value="Login!"/>
    </div>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title>Login</title>
</head>
<body>
    <c:import var="navbar" url="header.jsp"/>
    ${navbar}
    <c:url value="/login" var="loginUrl" />
    <div class="container">
        <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
            <div>
                <label for="username">Username: </label>
                <input id="username" name="j_username" type="text"/>
            </div>
            <div>
                <label for="password">Password: </label>
                <input id="password" type="password" name="j_password"/>
            </div>
            <div>
                <label><input name="j_rememberme" type="checkbox"/><spring:message code="login.remember_me"/> </label>
            </div>
            <div>
            <input type="submit" value="Login!"/>
            </div>
        </form>
    </div>
</body>
</html>

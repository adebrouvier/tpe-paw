<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
    <title><spring:message code="index.title"/></title>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="center">
<h2><spring:message code="register.title"></spring:message></h2>
</div>
    <div class="container">

<div class="center">
    <c:url value="/registerUser" var="PostPath"/>
    <form:form modelAttribute="userForm" action="${PostPath}" method="post">
        <div>
            <form:label path="username">Username</form:label>
            <form:input type="text" path="username"/>
            <form:errors path="username" cssClass="formError" element="p"/>
        </div>
        <div>
            <form:label path="password">Password</form:label>
            <form:input type="password" path="password"/>
            <form:errors path="password" cssClass="formError" element="p"/>
        </div>
        <div>
            <form:label path="repeatPassword">Repeat Password</form:label>
            <form:input type="password" path="repeatPassword"/>
            <form:errors path="username" cssClass="formError" element="p"/>
        </div>
        <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="tournament.create.submit"/></button>
    </form:form>
    </div>
</div>
</body>
</html>

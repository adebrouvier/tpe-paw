<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title><spring:message code="index.title"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
    <div class="container">
        <div class="row">
            <div class="col s6 offset-s3 m6 offset-m3 card-panel">
                <div class="center"><h2><spring:message code="register.title"/></h2></div>
                <c:url value="/registerUser" var="PostPath"/>
                <form:form modelAttribute="registerForm" action="${PostPath}" method="post">
                    <div class="input-field">
                        <i class="material-icons prefix">account_circle</i>
                        <form:label path="username"><spring:message code="register.username"/></form:label>
                        <form:input type="text" path="username"/>
                        <form:errors path="username" cssClass="form-error" element="p"/>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <form:label path="password"><spring:message code="register.password"/></form:label>
                        <form:input type="password" path="password"/>
                        <form:errors path="password" cssClass="form-error" element="p"/>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <form:label path="repeatPassword"><spring:message code="register.repeat"/></form:label>
                        <form:input type="password" path="repeatPassword"/>
                        <form:errors path="repeatPassword" cssClass="form-error" element="p"/>
                    </div>
                    <div class="row center">
                        <div class="input-field col s12">
                            <button class="btn btn-large light-blue darken-4" type="submit"><spring:message code="register.title"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>

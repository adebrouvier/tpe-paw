<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title><spring:message code="login.title"/> - <spring:message code="header.name"/></title>
</head>
<body>
    <c:url value="/login" var="loginUrl" />
    <div class="container">
        <div class="row">
            <div class="col s6 offset-s3 m6 offset-m3 card-panel">
                <div class="center"><a href="<c:url value="/"/>"><h1><spring:message code="header.name"/></h1></a></div>
                <form class="login-form" action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                    <c:if test="${param.error != null}" >
                        <div class="row center form-error">
                            <h6><spring:message code="login.error"/></h6>
                        </div>
                    </c:if>
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">account_circle</i>
                            <label for="username"><spring:message code="login.username"/>: </label>
                            <input id="username" name="j_username" type="text" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">lock</i>
                            <label for="password"><spring:message code="login.password"/>: </label>
                            <input id="password" type="password" name="j_password" autocomplete="off"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 center">
                            <input type="checkbox" id="remember-me" class="filled-in checkbox-versus" name="j_rememberme"/>
                            <label for="remember-me"><spring:message code="login.remember_me"/></label>
                        </div>
                    </div>
                    <div class="row center">
                        <div class="input-field col s12">
                            <button class="btn-large light-blue darken-4" type="submit"><spring:message code="login.login"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="center">
            <a class="btn light-blue darken-4" href="<c:url value="/register"/>"><spring:message code="login.create"/></a>
        </div>
    </div>
</body>
</html>

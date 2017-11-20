<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    <title><spring:message code="404.title"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<div class="container center">
    <h4><spring:message code="404"/></h4>
    <img src="/resources/img/error-404.png" style="width: 250px; height: 150px">
    <p>
        <spring:message code="404.message"/></p>
    <a href="<c:url value = "/"/>" class="btn waves-effect waves-light light-blue darken-4"><spring:message code="404.return"/></a>
</div>
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>
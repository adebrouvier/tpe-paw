<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="set" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
    href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <title><spring:message code="ranking.title"/></title>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="container center">
    <c:url value="/create/ranking" var="postPath"/>
    <form:form modelAttribute="rankingForm" action="${postPath}" method="post">
    <h2><spring:message code="ranking.create.title"/></h2>
    <div class="row">
        <div class="col offset-s3 s6">
            <div class="input-field">
                <form:label path="rankingName"><spring:message code="ranking.create.name"/>: </form:label>
                <spring:message code="ranking.create.name.placeholder" var="rankingNamePlaceholder"/>
                <form:input placeholder="${rankingNamePlaceholder}" type="text" data-length="20" path="rankingName"
                            autocomplete="off"/>
                <form:errors path="rankingName" cssClass="form-error" element="p"/>
            </div>
            <div class="input-field">
                <form:label path="game"><spring:message code="ranking.create.game"/>: </form:label>
                <spring:message code="ranking.create.game.placeholder" var="rankingGamePlaceholder"/>
                <form:input placeholder="${rankingGamePlaceholder}" type="text" data-length="20" path="game"
                            autocomplete="off"/>
                <form:errors path="game" cssClass="form-error" element="p"/>
            </div>
        </div>
    </div>
        <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="ranking.create.submit"/></button>
    </form:form>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>

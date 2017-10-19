<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament.css"/>" />
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.11.1/typeahead.bundle.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/tournament.js"/>"></script>
    <title><spring:message code="tournament.title"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="container center">
    <h3><spring:message code="tournament.create.title"/></h3>
    <div class="row">
        <div class="col offset-s3 s6">
            <c:url value="/create/tournament" var="postPath"/>
            <form:form modelAttribute="tournamentForm" action="${postPath}" method="post">
                <div class="input-field">
                    <form:label path="tournamentName"><spring:message code="tournament.create.name"/>: </form:label>
                    <spring:message code="tournament.create.name.placeholder" var="tournamentNamePlaceholder"/>
                    <form:input placeholder="${tournamentNamePlaceholder}" type="text" data-length="40" path="tournamentName" autocomplete="off"/>
                    <form:errors path="tournamentName" cssClass="form-error" element="p"/>
                </div>
                <div class="input-field">
                    <%--<form:label path="game"><spring:message code="tournament.create.game"/>: </form:label> --%>
                    <spring:message code="tournament.create.game.placeholder" var="gamePlaceholder"/>
                    <form:input placeholder="${gamePlaceholder}" type="text" class="typeahead" path="game" autocomplete="off"/>
                    <form:errors path="game" cssClass="form-error" element="p"/>
                </div>
                <div class="row">
                    <div>
                        <spring:message code="tournament.create.seeding"/>
                    </div>
                    <div class="randomize-checkbox">
                        <form:checkbox path="randomizeSeed" cssClass="filled-in checkbox-versus" id="seed-checkbox"/>
                        <form:label path="randomizeSeed" for="seed-checkbox"><spring:message code="tournament.create.checkbox.seeding"/></form:label>
                    </div>
                </div>

                <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="tournament.create.submit"/></button>
            </form:form>
        </div>
    </div>
</div>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
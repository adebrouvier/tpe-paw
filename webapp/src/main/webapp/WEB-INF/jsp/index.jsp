<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <title><spring:message code="index.title"/></title>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="container center">
    <h2><spring:message code="tournament.create.title"/></h2>
    <div class="row">
        <div class="col offset-s3 s6">
            <c:url value="/create" var="postPath"/>
            <form:form modelAttribute="tournamentForm" action="${postPath}" method="post">
                <div class="input-field">
                    <form:label path="tournamentName"><spring:message code="tournament.create.name"/>: </form:label>
                    <spring:message code="tournament.create.name.placeholder" var="tournamentNamePlaceholder"/>
                    <form:input placeholder="${tournamentNamePlaceholder}" type="text" data-length="20" path="tournamentName" autocomplete="off"/>
                    <form:errors path="tournamentName" cssClass="form-error" element="p"/>
                </div>
                <div class="input-field">
                    <form:label path="players"><spring:message code="tournament.create.players"/>:</form:label>
                    <spring:message code="tournament.create.players.placeholder" var="playersPlaceholder"/>
                    <form:textarea placeholder="${playersPlaceholder}" cssClass="materialize-textarea" type="text"
                                   path="players"/>
                    <form:errors path="players" cssClass="form-error" element="p"/>
                </div>
                <div>
                    <p><spring:message code="tournament.create.seeding"/></p>
                </div>
                <div class="row">
                    <div>
                        <p>
                            <form:checkbox path="randomizeSeed" cssClass="filled-in" id="seed-checkbox"/>
                            <form:label path="randomizeSeed" for="seed-checkbox"><spring:message code="tournament.create.checkbox.seeding"/></form:label>
                        </p>
                    </div>
                </div>
                    <button class="btn btn-primary" type="submit"><spring:message code="tournament.create.submit"/></button>
            </form:form>
        </div>
    </div>
</div>
<div class="container">
    <div class="collection">
        <ul>
            <li class="collection-item"><h4><spring:message code="index.tournaments"/></h4></li>
        <c:forEach var="tournament" items="${tournaments}">
            <li class="collection-item">
                <a href="<c:url value = "/tournament/${tournament.id}"/>">${tournament.name}</a>
                <span>Players: ${tournament.size}</span>
            </li>
        </c:forEach>
        </ul>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>

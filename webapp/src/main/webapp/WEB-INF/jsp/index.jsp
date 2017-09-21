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
                <form:input placeholder="Tournament Name" type="text" data-length="20" path="tournamentName"/>
                <form:errors path="tournamentName" cssClass="form-error" element="p"/>
            </div>
            <div class="input-field">
                <form:label path="players"><spring:message code="tournament.create.players"/>:</form:label>
                <form:textarea placeholder="Insert one player per line" cssClass="materialize-textarea" type="text"
                               path="players"/>
                <form:errors path="players" cssClass="form-error" element="p"/>
            </div>
            <div>
                <p><spring:message code="tournament.create.seeding"/></p>
            </div>
            <div class="row">
                <div>
                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box"/>
                        <label for="filled-in-box"><spring:message code="tournament.create.checkbox.seeding"/></label>
                    </p>
                </div>
            </div>
            <button class="btn btn-primary" type="submit"><spring:message code="tournament.create.submit"/></button>
        </div>
        </form:form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>

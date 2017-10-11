<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<div class="container center">
    <div>
        <c:url value="/searchtournament" var="getPath"/>
        <form:form modelAttribute="searchForm" action="${getPath}" method="get">
            <div class="input-field">
                <div id="autocomplete-search" data-search="<c:out value="${tournamentNames}"/>"></div>
                <i class="material-icons prefix">search</i>
                <form:input type="text" cssClass="autocomplete autocomplete-search-input" placeholdertype="text" path="query" autocomplete="off"/>
                <form:label path="query">Search </form:label>
                <form:errors path="query" cssClass="form-error" element="p"/>
            </div>
        </form:form>
    </div>
    <h3><spring:message code="tournament.create.title"/></h3>
    <div class="row">
        <div class="col offset-s3 s6">
            <c:url value="/create" var="postPath"/>
            <form:form modelAttribute="tournamentForm" action="${postPath}" method="post">
                <div class="input-field">
                    <form:label path="tournamentName"><spring:message code="tournament.create.name"/>: </form:label>
                    <spring:message code="tournament.create.name.placeholder" var="tournamentNamePlaceholder"/>
                    <form:input placeholder="${tournamentNamePlaceholder}" type="text" data-length="40" path="tournamentName" autocomplete="off"/>
                    <form:errors path="tournamentName" cssClass="form-error" element="p"/>
                </div>
                <div class="input-field">
                    <form:label path="players"><spring:message code="tournament.create.players"/>:</form:label>
                    <spring:message code="tournament.create.players.placeholder" var="playersPlaceholder"/>
                    <form:textarea placeholder="${playersPlaceholder}" cssClass="materialize-textarea" type="text"
                                   path="players"/>
                    <form:errors path="players" cssClass="form-error" element="p"/>
                </div>
                <div class="input-field">
                    <div id="autocomplete-games" data-games="<c:out value="${games}"> </c:out>"></div>
                    <form:label path="game"><spring:message code="tournament.create.game"/>: </form:label>
                    <spring:message code="tournament.create.game.placeholder" var="gamePlaceholder"/>
                    <form:input placeholder="${gamePlaceholder}" type="text" class="autocomplete" path="game" autocomplete="off"/>
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
                </div>
            </form:form>
        </div>
    </div>
</div>
<div class="featured-tournaments container">
    <h4 class="center"><spring:message code="index.tournaments"/></h4>
    <table class="highlight centered">
        <thead>
        <tr>
            <th><spring:message code="index.tournaments.table.name"/></th>
            <th><spring:message code="index.tournaments.table.players"/></th>
            <th><spring:message code="index.tournaments.table.matches"/></th>
            <th><spring:message code="index.tournaments.table.page"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tournament" items="${tournaments}">
            <tr>
                <td>${tournament.name}</td>
                <td>${tournament.size}</td>
                <td>${tournament.numberOfMatches}</td>
                <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:import var="footer" url="footer.jsp"/>
${footer}


</body>
</html>
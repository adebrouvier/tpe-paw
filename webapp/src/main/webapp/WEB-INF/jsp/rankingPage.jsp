<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
    href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.11.1/typeahead.bundle.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/rankingPage.js"/>"></script>
    <title><c:out value="${ranking.name}"/> - <spring:message code="ranking.title"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="container center col 6">
    <div class="row">
        <div class="col offset-s3 s6">
            <h2><spring:message code="ranking.greeting" arguments="${ranking.name}"/></h2>
            <h3 id="ranking-game" data-game="<c:out value="${ranking.gameId}"/>"><c:out value="${ranking.game.name}"/></h3>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col s6">
            <h4 class="center"><spring:message code="ranking.table.title"/></h4>
            <table class="highlight centered">
                <thead>
                <tr>
                    <th><spring:message code="ranking.table.users"/></th>
                    <th><spring:message code="ranking.table.points"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${ranking.users}">
                    <tr>
                        <td><c:out value="${user.userName}"/></td>
                        <td>${user.points}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="center col s6">
            <div class="row">
                <h4><spring:message code="rankingPage.tournaments"/></h4>
                <table>
                    <thead>
                        <tr>
                            <th><spring:message code="rankingPage.tournaments.table.name"/></th>
                            <th><spring:message code="rankingPage.tournaments.table.points"/></th>
                        </tr>
                    </thead>
                    <tbody>
                <c:forEach var="tournament" items="${ranking.tournaments}">
                    <tr>
                        <td><c:out value="${tournament.name}"/></td>
                        <td>${tournament.awardedPoints}</td>
                        <c:if test="${ranking.userId == loggedUser.id}">
                        <c:url value="/ranking/${ranking.id}/delete/${tournament.tournamentId}" var="addTournamentPath"/>"
                        <form:form action="${addTournamentPath}" method="post">
                        <td><button class="btn" type="submit"><i class="material-icons">delete</i></button></td>
                        </form:form>
                        </c:if>
                    </tr>
                </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${loggedUser.id == ranking.userId}">
            <div class="row">
                <c:url value="/ranking/${ranking.id}/addTournament" var="postPath"/>
                <form:form modelAttribute="rankingPageForm" action="${postPath}" method="post">
                    <div class="col s6">
                        <div class="input-field">
                            <form:label class="active" path="tournamentName"><spring:message code="ranking.tournaments"/>: </form:label>
                            <spring:message code="ranking.tournaments.placeholder" var="rankingTournamentsPlaceholder"/>
                            <form:input cssClass="typeahead" path="tournamentName" type="text" placeholder="${rankingTournamentsPlaceholder}" autocomplete="off"/>
                            <form:errors path="tournamentName" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="col s6">
                        <div class="input-field">
                            <form:label path="points"><spring:message code="ranking.awardedPoints"/>: </form:label>
                            <spring:message code="ranking.awardedPoints.placeholder" var="awardedPointsPlaceholder"/>
                            <form:input path="points" type="number" placeholder="${awardedPointsPlaceholder}"/>
                            <form:errors path="points" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="ranking.tournament.add"/></button>
            </form:form>
            </div>
            </c:if>
        </div>
    </div>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>

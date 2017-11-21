<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
    href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/ranking.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>    <script type="text/javascript" src="<c:url value="/resources/js/rankingPage.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${ranking.name}"/> - <spring:message code="ranking.title"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="container">
    <div class="row">
        <c:import var="rankingInfo" url="ranking-info.jsp"/>
        ${rankingInfo}
        <span id="ranking-game" data-game="<c:out value="${ranking.game.id}"/>"></span>
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
                <c:if test="${not empty ranking.userScores}">
                    <c:forEach var="userScore" items="${ranking.userScores}">
                        <tr>
                            <td><c:out value="${userScore.user.name}"/></td>
                            <td>${userScore.points}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty ranking.userScores}">
                    <tr><td><spring:message code="ranking.table.empty"/></td></tr>
                </c:if>
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
                    <c:choose>
                        <c:when test="${not empty ranking.tournaments}">
                            <c:forEach var="tournamentPoints" items="${ranking.tournaments}">
                                <tr class="tournament-container">
                                    <td><c:out value="${tournamentPoints.tournament.name}"/></td>
                                    <td>${tournamentPoints.awardedPoints}</td>
                                    <c:if test="${ranking.user.id == loggedUser.id}">
                                    <c:url value="/delete/ranking/${ranking.id}/${tournamentPoints.tournament.id}" var="deletePath"/>
                                    <form:form action="${deletePath}" method="post">
                                    <td><button class="btn transparent delete-btn" type="submit"><i class="material-icons">delete</i></button></td>
                                    </form:form>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td><spring:message code="rankingPage.tournaments.table.empty"/></td></tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
            <c:if test="${loggedUser.id == ranking.user.id}">
            <div class="row">
                <c:url value="/update/ranking/${ranking.id}/addtournament" var="postPath"/>
                <form:form modelAttribute="rankingPageForm" action="${postPath}" method="post">
                    <div class="col s6">
                        <div class="input-field">
                            <form:label class="active" path="tournamentName"><spring:message code="ranking.tournaments"/>: </form:label>
                            <spring:message code="ranking.tournaments.placeholder" var="rankingTournamentsPlaceholder"/>
                            <form:input id="tournament-search" cssClass="typeahead" path="tournamentName" type="text" placeholder="${rankingTournamentsPlaceholder}" autocomplete="off"/>
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

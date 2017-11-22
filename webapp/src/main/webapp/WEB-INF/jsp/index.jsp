<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><spring:message code="index.title"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="section no-pad-bot" id="index-banner">
    <div class="container">
        <br/>
        <h1 class="header center"><spring:message code="index.header.title"/></h1>
        <div class="row center">
            <h5 class="header col s12 light"><spring:message code="index.header.subtitle"/></h5>
        </div>
        <div class="row center">
            <a href="<c:url value = "/tournament"/>" class="btn-large waves-effect waves-light light-blue darken-4"><spring:message code="index.header.tournament.button"/></a>
            <a href="<c:url value = "/ranking"/>" class="btn-large waves-effect waves-light light-blue darken-4"><spring:message code="index.header.ranking.button"/></a>
        </div>
        <br><br>

    </div>
</div>
<div class="featured container">
    <div class="row">
        <div class="col s4">
            <h5><spring:message code="index.topChampions.title"/></h5>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="index.topChampions.table.pos"/></th>
                    <th><spring:message code="index.topChampions.table.user"/></th>
                    <th><spring:message code="index.topChampions.table.wins"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="winner" items="${topUsers}" varStatus="position">
                    <tr>
                        <c:set var="rank" value="${position.index+1}"/>
                        <td>
                            <c:choose>
                                <c:when test="${rank == 1}">
                                    <img class="podium-trophy" src="<c:url value="/resources/img/gold-trophy.png"/>" /></c:when>
                                <c:when test="${rank == 2}">
                                    <img class="podium-trophy" src="<c:url value="/resources/img/silver-trophy.png"/>" />                                </c:when>
                                <c:when test="${rank == 3}">
                                    <img class="podium-trophy" src="<c:url value="/resources/img/bronze-trophy.png"/>" />                                </c:when>
                                <c:otherwise>
                                    <span><c:out value="${rank}"/></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="<c:url value="/user/${winner.user.id}"/>"><c:out value="${winner.user.name}"/></a></td>
                        <td><c:out value="${winner.wins}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col s4">
            <h5><spring:message code="index.mostFollowed.title"/></h5>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="index.mostFollowed.table.user"/></th>
                    <th><spring:message code="index.mostFollowed.table.follows"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="popularUser" items="${mostFollowed}" varStatus="position">
                    <tr>
                        <td><a href="<c:url value="/user/${popularUser.user.id}"/>"><c:out value="${popularUser.user.name}"/></a></td>
                        <td><c:out value="${popularUser.followers}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col s4">
            <h5><spring:message code="index.popularRankings.title"/></h5>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="index.popularRankings.table.ranking"/></th>
                    <th><spring:message code="index.popularRankings.table.users"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="popularRanking" items="${popularRankings}" varStatus="position">
                    <tr>
                        <td><a href="<c:url value="/ranking/${popularRanking.ranking.id}"/>"><c:out value="${popularRanking.ranking.name}"/></a></td>
                        <td><c:out value="${popularRanking.rankedUsers}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <h4 class="center"><spring:message code="index.tournaments"/></h4>
    <table class="striped centered">
        <thead>
        <tr>
            <th><spring:message code="index.tournaments.table.name"/></th>
            <th><spring:message code="index.tournaments.table.game"/></th>
            <th><spring:message code="index.tournaments.table.players"/></th>
            <th><spring:message code="index.tournaments.table.matches"/></th>
            <th><spring:message code="index.tournaments.table.page"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tournament" items="${tournaments}">
            <tr>
                <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><c:out value="${tournament.name}"/></a></td>
                <td><c:out value="${tournament.game.name}"/></td>
                <td>${fn:length(tournament.players)}</td>
                <td>${fn:length(tournament.matches)}</td>
                <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h4 class="center"><spring:message code="index.rankings"/></h4>
    <table class="striped centered">
        <thead>
        <tr>
            <th><spring:message code="index.rankings.table.name"/></th>
            <th><spring:message code="index.rankings.table.game"/></th>
            <th><spring:message code="index.rankings.table.page"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ranking" items="${rankings}">
            <tr>
                <td><a href="<c:url value = "/ranking/${ranking.id}"/>"><c:out value="${ranking.name}"/></a></td>
                <td><c:out value="${ranking.game.name}"/></td>
                <td><a href="<c:url value = "/ranking/${ranking.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
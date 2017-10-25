<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament-page.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title><c:out value="${tournament.name}"/> - <spring:message code="tournament.standings"/> - <spring:message code="header.name"/></title>
</head>
<body>
    <main>
    <c:import var="navbar" url="navbar.jsp"/>
    ${navbar}
    <div class="image-container" >
        <div class="image-effect" style="background: linear-gradient(rgba(38, 42, 53, 0.25) 65%, rgb(38, 42, 53) 100%), url('<c:out value="${game.urlImage}"></c:out>') center center"></div>
    </div>
    <div class="container">
        <div class="row">
            <c:import var="tournamentInfo" url="tournament-info.jsp"/>
            ${tournamentInfo}
        </div>
        <div class="row card">
            <div class="col s12">
                <div class="row tournament-tabs">
                    <div class="col s12">
                        <ul class="tabs">
                            <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}"/>"><spring:message code="tournament.bracket"/></a></li>
                            <li class="tab col s4"><a class="active" href="#"><spring:message code="tournament.standings"/></a></li>
                            <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/players"/>">Players</a></li>
                        </ul>
                    </div>
                </div>
                <c:if test="${standings.size() == 0}">
                    <h5 class="center" style="margin-top: 30px;margin-bottom: 100px;"><spring:message code="tournament.info.noPlayers"/> </h5>
                </c:if>
                <c:if test="${(standings.size() != 0)}">
                <div class="row standings">
                    <table class="centered striped">
                        <thead>
                        <tr>
                            <th><spring:message code="tournament.standings.table.rank"/></th>
                            <th><spring:message code="tournament.standings.table.player"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="rank" items="${standings}">
                            <tr>
                                <c:choose>
                                    <c:when test="${rank.position == 0}">
                                        <td >-</td>
                                    </c:when>
                                    <c:when test="${rank.position != 0}">
                                        <td>${rank.position}&deg;</td>
                                    </c:when>
                                </c:choose>
                                <td>${rank.playerName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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

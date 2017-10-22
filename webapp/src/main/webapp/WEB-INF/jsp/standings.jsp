<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
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
    <div class="container">
        <div class="row">
            <div class="col s12">
                <div class="tournament-info card">
                    <div class="card-content">
                        <span class="card-title center"><c:out value="${tournament.name}"/></span>
                        <table>
                            <thead>
                            <tr>
                                <td><spring:message code="tournament.info.players"/></td>
                                <td><spring:message code="tournament.info.matches"/></td>
                                <td><spring:message code="tournament.info.game"/></td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${tournament.size}</td>
                                <td>${tournament.numberOfMatches}</td>
                                <td>${game.name}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
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
                <div class="row standings">
                    <table>
                        <thead>
                        <tr>
                            <th><spring:message code="tournament.standings.table.rank"/></th>
                            <th><spring:message code="tournament.standings.table.player"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="rank" items="${standings}">
                            <tr>
                                <td>${rank.position}&deg;</td>
                                <td>${rank.playerName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    </main>
    <c:import var="footer" url="footer.jsp"/>
    ${footer}
</body>
</html>

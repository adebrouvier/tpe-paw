<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
    href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/tournament-page.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament-page.css"/>"/>
    <title><c:out value="${tournament.name}"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="tournament-info card">
                <div class="card-content">
                    <span class="card-title center"><c:out value="${tournament.name}"/></span>
                    <table>
                        <thead>
                            <tr>
                                <th><spring:message code="tournament.info.players"/></th>
                                <th><spring:message code="tournament.info.matches"/></th>
                                <th><spring:message code="tournament.info.game"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${tournament.size}</td>
                                <td>${tournament.numberOfMatches}</td>
                                <td><c:if test="${game.name == ''}">
                                    <spring:message code="tournament.undefined.game"/>
                                </c:if>
                                    <c:if test="${game.name != null}">
                                        <c:out value="${game.name}"/>
                                    </c:if>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </div>
                </div>
            </div>
        </div>
    <div class="row">
        <div class="col s12">
            <ul class="tabs">
                <li class="tab col s4"><a class="active" href="#"><spring:message code="tournament.bracket"/></a></li>
                <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/standings"/>">Standings</a></li>
                <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/players"/>">Players</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="center">
    <c:url value="/tournament/${tournament.id}/end" var="endPath"/>
    <form:form action="${endPath}" method="post">
        <c:if test="${tournament.status == 'STARTED'}">
            <button class="btn light-blue darken-4 waves-effect waves-light" type="submit">
                <spring:message code="tournament.finish"/>
                <i class="material-icons right "></i>
            </button>
        </c:if>
        <c:if test="${tournament.status == 'FINISHED'}">
            <button class="btn light-blue darken-4 waves-effect waves-light disabled" type="submit">
                <spring:message code="tournament.finished"/>
                <i class="material-icons right "></i>
            </button>
        </c:if>
    </form:form>
</div>
<div class="container">
    <h4><spring:message code="tournament.bracket"/></h4>
    <c:if test="${tournament.status == 'NEW'}">
    <h6><spring:message code="tournament.bracket.empty"/></h6>
    </c:if>
    <div class="tournament-container">
        <div class="row">
            <c:set var="roundSize" value="${tournament.players.size()/2}"/>
            <c:set var="matchCount" value="1"/>
            <c:set var="margin" value="0"/>
            <c:set var="padding" value="16"/>
            <c:forEach var="match" items="${tournament.matches}">
                <c:choose>
                    <c:when test="${matchCount == 1}">
                        <div class="col">
                        <div class="bracket-container" style="margin-top: ${margin}">
                    </c:when>
                    <c:when test="${matchCount != 1}">
                        <div class="bracket-container " style="padding-top: ${padding}">
                    </c:when>
                </c:choose>
                    <span class="match-id-container">
                        <span class="match-id">
                            <c:out value="${match.id}"/>
                        </span>
                        <span class="triangle"></span>
                    </span>
                        <a class="modal-trigger" href="#modal-${match.id}">
                        <div class="bracket">
					        <div class="players-name">
		                        <div class="home-player-name">
                                    <c:out value="${match.homePlayer.name}"> </c:out>
		                        </div>
		                        <div class="away-player-name">
                                    <c:out value="${match.awayPlayer.name}"> </c:out>
                                </div>
                            </div>
                            <div class="players-score">
                                <div class="home-player-score">
                                    <c:out value="${match.homePlayerScore}"> </c:out>
                                </div>
                                <div class="away-player-score">
                                    <c:out value="${match.awayPlayerScore}"> </c:out>
                                </div>
		                    </div>
                        </div>
                    </a>

                </div>


                <div id="modal-${match.id}" class="modal match-modal">

                        <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                        <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                            <div class="modal-content">
                                <h5><spring:message code="tournament.modal.title"/></h5>
                                <table class="modal-table centered">
                                    <tbody>
                                        <tr class="modal-top-player">
                                            <td class="modal-player-name">
                                                <c:choose>
                                                    <c:when test="${match.homePlayer.id != null}">
                                                        <div><c:out value="${match.homePlayer.name}"/></div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div><spring:message code="tournament.modal.tbd"/></div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="modal-player-score">
                                                <div class="input-field inline">
                                                    <form:input id="home-result-${match.id}" min="0" type="number" cssClass="score-input" path="homeResult" value="${match.homePlayerScore}"/>
                                                    <form:errors path="homeResult" cssClass="formError" element="p"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="modal-player-name">
                                                <c:choose>
                                                    <c:when test="${match.awayPlayer.id != null}">
                                                        <div><c:out value="${match.awayPlayer.name}"/></div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div><spring:message code="tournament.modal.tbd"/></div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="modal-player-score">
                                                <div class="input-field inline">
                                                    <form:input min="0" id="away-result-${match.id}" type="number" cssClass="score-input" path="awayResult" value="${match.awayPlayerScore}"/>
                                                    <form:errors path="awayResult" cssClass="formError" element="p"/>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                                <div class="modal-footer">
                                <c:if test="${match.awayPlayerId == -1 || match.awayPlayerId == 0 || match.homePlayerId == 0 || tournament.status == 'FINISHED'}">
                                    <button class="btn waves-effect waves-light disabled" type="submit">
                                        <spring:message code="tournament.update"/>
                                        <i class="material-icons right ">update</i>
                                    </button>
                                </c:if>
                                <c:if test="${match.awayPlayerId != -1 && match.awayPlayerId != 0 && match.homePlayerId != 0 && tournament.status == 'STARTED'}">
                                    <button class="btn light-blue darken-4 waves-effect waves-light" type="submit">
                                        <spring:message code="tournament.update"/>
                                        <i class="material-icons right">update</i>
                                    </button>
                                </c:if>
                            </div>
                        </form:form>
                </div>
                <c:if test="${matchCount == roundSize}">
                    </div>
                    <c:set var="matchCount" value="0"/>
                    <c:set var="roundSize" value="${roundSize/2}"/>
                    <c:set var="margin" value="${(padding+44)/2+margin}"/>
                    <c:set var="padding" value="${2*margin+16}"/>
                </c:if>
                <c:set var="matchCount" value="${matchCount+1}"/>
            </c:forEach>
        </div>
    </div>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
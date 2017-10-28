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
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament-page.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${tournament.name}"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
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
                        <li class="tab col s4"><a class="active" href="#"><spring:message code="tournament.bracket"/></a></li>
                        <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/standings"/>"><spring:message code="tournament.standings"/></a></li>
                        <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/players"/>"><spring:message code="tournament.players"/></a></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <c:if test="${loggedUser.id == tournament.userId}">
                    <div class="center">

                        <c:url value="/update/tournament/${tournament.id}/end" var="endPath"/>
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
                    </c:if>
                    <h4><spring:message code="tournament.bracket"/></h4>
                    <c:if test="${tournament.status == 'NEW'}">
                        <h6><spring:message code="tournament.bracket.empty"/></h6>
                    </c:if>
                    <c:set var="roundSize" value="${tournament.players.size()/2}"/>
                    <div class="tournament-container" style="height:<c:out value="${roundSize*60+44}"/>px;overflow-y: hidden;overflow-x: auto;" >
                            <c:set var="matchCount" value="1"/>
                            <c:set var="margin" value="0"/>
                            <c:set var="padding" value="15"/>
                            <c:set var="leftPosition" value="0"/>
                            <c:set var="roundCount" value="1"/>
                            <c:forEach var="match" items="${tournament.matches}">
                            <c:choose>
                                <c:when test="${matchCount == 1}">
                                    <div class="tournament-col" style="left:<c:out value="${leftPosition}"/>;">
                                        <c:choose>
                                            <c:when test="${roundSize == 1}">
                                                <div class="round"><spring:message code="tournament.info.final"/></div>
                                            </c:when>
                                            <c:when test="${roundSize == 2}">
                                                <div class="round"><spring:message code="tournament.info.semifinal"/></div>
                                            </c:when>
                                            <c:when test="${roundSize != 1 && roundSize != 2}">
                                                <div class="round"><spring:message code="tournament.info.round"/> <c:out value="${roundCount}"/></div>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${roundCount != 1}">
                                                <div class="bracket-container" style="margin-top: ${margin+20}">
                                            </c:when>
                                            <c:when test="${roundCount == 1}">
                                                <div class="bracket-container" style="margin-top: ${margin}">
                                            </c:when>
                                        </c:choose>
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
                                        <c:if test="${roundSize != 1}">
                                            <c:choose>
                                                <c:when test="${matchCount % 2 == 0}">
                                                    <div class="match-right-branch"></div>
                                                </c:when>
                                                <c:when test="${matchCount % 2 != 0}">
                                                    <c:choose>
                                                        <c:when test="${matchCount == 1}">
                                                            <div class="match-left-branch" style="top:21px;height: <c:out value="${padding + 23}"/>px;"></div>
                                                            <div class="match-next-branch" style="top:<c:out value="${padding/2+43}"/>px"></div>
                                                        </c:when>
                                                        <c:when test="${matchCount != 1}">
                                                            <div class="match-left-branch" style="top:<c:out value="${padding + 21}"/>px;height: <c:out value="${padding + 23}"/>px;"></div>
                                                            <div class="match-next-branch" style="top:<c:out value="${padding/2+padding+43}"/>px"></div>
                                                        </c:when>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose>
                                        </c:if>
                                    </div>

                                    <c:choose>
                                        <c:when test="${loggedUser.id == tournament.userId && (match.awayPlayerId != -1 && match.awayPlayerId != 0 && match.homePlayerId != 0 && tournament.status == 'STARTED')}">
                                            <div id="modal-${match.id}" class="modal match-modal">

                                                <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                                                <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                                                    <div class="modal-content">
                                                        <h5><spring:message code="tournament.modal.title"/></h5>
                                                        <table class="modal-table centered">
                                                            <tbody>
                                                            <tr class="modal-top-player">
                                                                <td class="modal-player-name">
                                                                    <div><c:out value="${match.homePlayer.name}"/></div>
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
                                                                    <div><c:out value="${match.awayPlayer.name}"/></div>
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
                                                       <button class="btn light-blue darken-4 waves-effect waves-light" type="submit">
                                                            <spring:message code="tournament.update"/>
                                                            <i class="material-icons right">update</i>
                                                       </button>
                                                    </div>
                                                </form:form>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div id="modal-${match.id}" class="modal match-modal">
                                                <div class="modal-content">
                                                    <h5><spring:message code="tournament.modal.title.otherUser"/></h5>
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
                                                                    <c:out value="${match.homePlayerScore}"/>
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
                                                                    <c:out value="${match.awayPlayerScore}"/>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${matchCount == roundSize}">
                                </div>
                                <c:set var="matchCount" value="0"/>
                                <c:set var="roundSize" value="${roundSize/2}"/>
                                <c:set var="margin" value="${(padding+44)/2+margin}"/>
                                <c:set var="padding" value="${2*margin+15}"/>
                                <c:set var="leftPosition" value="${leftPosition+250}"/>
                                <c:set var="roundCount" value="${roundCount+1}"/>
                                </c:if>
                                <c:set var="matchCount" value="${matchCount+1}"/>
                                </c:forEach>
                                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament-page.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/players.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/players.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${tournament.name}"/> - <spring:message code="tournament.standings"/> - <spring:message code="header.name"/></title>
</head>
<body data-tournamentId="<c:out value="${tournament.id}"/>">
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="image-container" >
    <div class="image-effect" style="background: linear-gradient(rgba(38, 42, 53, 0.25) 65%, rgb(38, 42, 53) 100%), url('<c:out value="${tournament.game.gameUrlImage.urlImage}"/>') center center"></div>
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
                        <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}"/>"><spring:message code="tournament.bracket"/></a></li>
                        <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}/standings"/>"><spring:message code="tournament.standings"/></a></li>
                        <li class="tab col s3"><a class="active" href="#"><spring:message code="tournament.players"/></a></li>
                        <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}/comments"/>"><spring:message code="tournament.comments"/></a></li>
                    </ul>
                </div>
            </div>
            <c:choose>
                <c:when test="${tournament.status == 'NEW' && tournament.creator.id == loggedUser.id}">
                    <div class="row">
                        <div class="players">
                            <div class="col s12">
                                <c:url value="/update/tournament/${tournament.id}/players" var="postPath"/>
                                <form:form id="addPlayer" modelAttribute="playerForm" action="${postPath}" method="post">
                                    <h5><spring:message code="tournament.player.add.title"/></h5>
                                    <div class="row" style=" margin-left: 10px;">
                                        <div class="input-field col s3">
                                            <form:label path="player"><spring:message code="tournament.player"/>: </form:label>
                                            <spring:message code="tournament.player.placeholder" var="playerPlaceholder"/>
                                            <form:input placeholder="${playerPlaceholder}" type="text" path="player" autocomplete="off"/>
                                            <form:errors path="player" cssClass="form-error" element="p"/>
                                        </div>
                                        <div class="input-field col s3">
                                            <form:label path="username"><spring:message code="tournament.player.username"/>: </form:label>
                                            <spring:message code="tournament.player.username.placeholder" var="usernamePlaceholder"/>
                                            <form:input placeholder="${usernamePlaceholder}" type="text" path="username" autocomplete="off"/>
                                            <form:errors path="username" cssClass="form-error" element="p"/>
                                        </div>
                                        <div class="col s2">
                                            <button id="addButton" class="btn btn-primary light-blue darken-4" style="margin-top: 20px" type="submit"><spring:message code="tournament.player.add"/></button>
                                        </div>
                                    </div>
                                </form:form>
                                <div class="row center">
                                    <c:if test="${tournament.status == 'NEW' && tournament.players.size() >= 2}">
                                        <form action="<c:url value="/update/tournament/${tournament.id}/generate"/>" method="post">
                                            <button type="submit" class="btn btn-primary light-blue darken-4"><spring:message code="tournament.players.generate"/></button>
                                        </form>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class="col s12">
                                        <h4>Players</h4>
                                        <ul id="sortable">
                                            <c:set var="seed" value="1"/>
                                            <c:forEach var="player" items="${tournament.players}">
                                                <c:if test="${player.id != -1}">
                                                    <li>
                                                        <div class="player-container">
                                                            <span class="player-seed">
                                                                <c:out value="${player.seed}"/>
                                                            </span>
                                                            <span class="move">
                                                                <i class="tiny material-icons">unfold_more</i>
                                                            </span>
                                                            <span class="player-name">
                                                                <c:out value="${player.name}"/>
                                                            </span>
                                                            <c:if test="${player.user.name != null}">
                                                            <span class="user-name">- <c:out value="${player.user.name}"/></span>
                                                            </c:if>
                                                            <form style="width: 0;height: 0; margin: 0; padding: 0; display: inline" id="<c:out value="${seed}"/>" action="<c:url value="/remove/player/${tournament.id}/${player.id}"/>" method="POST">
                                                                <a href="#" onclick="$('#<c:out value="${seed}"/>').submit()" >
                                                                    <span class="player-remove">
                                                                        <i class="tiny material-icons">delete</i>
                                                                    </span>
                                                                </a>
                                                            </form>
                                                        </div>
                                                    </li>
                                                    <c:set var="seed" value="${seed+1}"/>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col s12">
                                        <h5>Awaiting for confirmation</h5>
                                        <ul>
                                        <c:choose>
                                            <c:when test="${fn:length(inscriptions) > 0}">
                                                <c:forEach var="inscription" items="${inscriptions}">
                                                    <li>
                                                        <div class="row">
                                                            <div class="col s1">
                                                                <c:out value="${inscription.user.name}"/>
                                                            </div>
                                                            <div class="col s1">
                                                                <form action="<c:url value="/accept/user/${inscription.user.id}/tournament/${tournament.id}"/>" method="post">
                                                                    <button type="submit" class="btn btn-primary green"><i class="material-icons">check</i></button>
                                                                </form>
                                                            </div>
                                                            <div class="col s1">
                                                                <form action="<c:url value="/reject/user/${inscription.user.id}/tournament/${tournament.id}"/>" method="post">
                                                                    <button type="submit" class="btn btn-primary red"><i class="material-icons">close</i></button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </c:when>
                                            <c:when test="${empty inscriptions}">
                                                <spring:message code="tournament.players.noInscriptions"/>
                                            </c:when>
                                        </c:choose>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${empty tournament.players && tournament.creator.id != loggedUser.id}">
                    <h5 class="center" style="margin-top: 30px;margin-bottom: 100px;"><spring:message code="tournament.info.noPlayers"/></h5>
                </c:when>
                <c:when test="${tournament.status != 'NEW' || (tournament.creator.id != loggedUser.id && tournament.players.size() != 0)}">
                    <div class="row">
                        <div class="players">
                            <div class="col s12">
                                <div class="row" >
                                    <table class="striped centered">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="tournament.info.players.seed"/></th>
                                            <th><spring:message code="tournament.info.players.player"/></th>
                                            <th><spring:message code="tournament.info.players.user"/></th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:set var="seed" value="1"/>
                                        <c:forEach var="player" items="${tournament.players}">
                                            <c:if test="${player.id != -1}">
                                                <tr>
                                                    <td class="player-seed">
                                                        <c:out value="${player.seed}"/>
                                                    </td>
                                                    <td class="player-name">
                                                        <c:out value="${player.name}"/>
                                                    </td>
                                                    <td>
                                                        <c:out value="${player.user.name}"/>
                                                    </td>
                                                </tr>
                                                <c:set var="seed" value="${seed+1}"/>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>


        </div>
    </div>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>

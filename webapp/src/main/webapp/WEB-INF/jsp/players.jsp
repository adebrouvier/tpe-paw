<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <div class="row">
        <div class="col s12">
            <ul class="tabs">
                <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}"/>"><spring:message code="tournament.bracket"/></a></li>
                <li class="tab col s4"><a target="_self" href="<c:url value="/tournament/${tournament.id}/standings"/>"><spring:message code="tournament.standings"/></a></li>
                <li class="tab col s4"><a class="active" href="#">Players</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="players">
    <div class="container">
        <div class="row">
            <h6>Participants</h6>
            <ul>
            <c:forEach var="player" items="${players}">
                <li><c:out value="${player.name}"/></li>
            </c:forEach>
            </ul>
        </div>
        <div class="row">
            <div class="col s12 card-panel">
                <c:url value="/tournament/${tournament.id}/players" var="postPath"/>
                <form:form modelAttribute="playerForm" action="${postPath}" method="post">
                    <h6><spring:message code="tournament.player.add.title"/></h6>
                    <div class="row">
                        <div class="input-field col s5">
                            <form:label path="player"><spring:message code="tournament.player"/>: </form:label>
                            <spring:message code="tournament.player.placeholder" var="playerPlaceholder"/>
                            <form:input placeholder="${playerPlaceholder}" type="text" path="player" autocomplete="off"/>
                            <form:errors path="player" cssClass="form-error" element="p"/>
                        </div>
                        <div class="input-field col s5">
                            <form:label path="username"><spring:message code="tournament.player.username"/>: </form:label>
                            <spring:message code="tournament.player.username.placeholder" var="usernamePlaceholder"/>
                            <form:input placeholder="${usernamePlaceholder}" type="text" path="username" autocomplete="off"/>
                            <form:errors path="username" cssClass="form-error" element="p"/>
                        </div>
                        <div class="col s2">
                            <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="tournament.player.add"/></button>
                        </div>
                    </div>
                </form:form>
                <div class="row center">
                    <form action="<c:url value="/tournament/${tournament.id}/generate"/>" method="post">
                        <a type="submit" class="btn btn-primary light-blue darken-4"><spring:message code="tournament.players.generate"/></a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

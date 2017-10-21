<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <title><c:out value="${ranking.name}"/> - <spring:message code="ranking.title"/></title>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<div class="container center col 6">
    <div class="row">
        <div class="col offset-s3 s6">
            <h2><spring:message code="ranking.greeting" arguments="${ranking.name}"/></h2>
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
                        <td>${user.userName}</td>
                        <td>${user.points}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="center col s6">
            <h4>
                Tournaments
            </h4>
            <c:forEach var="tournament" items="${ranking.tournaments}">
                <ol>
                    <td>${tournament.name}</td>
                    <td>${tournament.awardedPoints}</td>
                    <td><a href="<c:url value="/ranking/${ranking.id}/delete/${tournament.tournamentId}"/>"><i class="material-icons">delete</i></a></td>
                </ol>
            </c:forEach>
        </div>
    </div>
</div>

<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>

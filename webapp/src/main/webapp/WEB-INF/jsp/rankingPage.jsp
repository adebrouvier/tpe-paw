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
            <h4>
                Tournaments
            </h4>
            <c:forEach var="tournament" items="${ranking.tournaments}">
                <ul>
                    <li>${tournament.name}</li>
                    <li>${tournament.awardedPoints}</li>
                    <li><a href="<c:url value="/ranking/${ranking.id}/delete/${tournament.tournamentId}"/>"><i class="material-icons">delete</i></a></li>
                </ul>
            </c:forEach>
            <c:url value="/ranking/${ranking.id}/addPlayers" var="postPath"/>
            <form:form modelAttribute="rankingPageForm" action="${postPath}" method="post">
                <div class="divider"></div>
                <div id="ranking-tournaments">
                    <c:forEach items="${rankingPageForm.tournaments}" varStatus="i">
                        <c:set var="index" value="${i.index}"/>
                        <div class="input-field">
                            <form:label path="tournaments[${index}].name"><spring:message code="ranking.tournaments"/>: </form:label>
                            <spring:message code="ranking.tournaments.placeholder" var="rankingTournamentsPlaceholder"/>
                            <form:input path="tournaments[${index}].name" type="text" placeholder="${rankingTournamentsPlaceholder}"/>
                            <form:errors path="tournaments[${index}].name" cssClass="form-error" element="p"/>
                        </div>
                        <div class="input-field">
                            <form:label path="tournaments[${index}].name"><spring:message code="ranking.awardedPoints"/>: </form:label>
                            <spring:message code="ranking.awardedPoints.placeholder" var="awardedPointsPlaceholder"/>
                            <form:input path="tournaments[${index}].points" type="number" placeholder="${awardedPointsPlaceholder}"/>
                            <form:errors path="tournaments[${index}].points" cssClass="form-error" element="p"/>
                        </div>
                    </c:forEach>
                    <form:errors path="tournaments" cssClass="form-error" element="p"/>
                </div>
                <a id="tournament-adder" class="btn btn-primary light-blue darken-4"><spring:message code="ranking.tournament.add"/></a>
                <br/>
                <br/>
                <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="ranking.create.submit"/></button>
            </form:form>
        </div>
    </div>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>


</body>
</html>


<script>

    $(document).ready(
        function() {
            <c:set var="listIndex" value="0"/>
            <c:if test="${rankingPageForm.tournaments.size() > 0}">
            <c:set var="listIndex" value="${rankingPageForm.tournaments.size()}"/>
            </c:if>
            var i = <c:out value="${listIndex}"/>;
            $("#tournament-adder").click(function () {
                $("#ranking-tournaments").append(
                    "<div class=\"input-field\">" +
                    "<input type=\"text\" placeholder=\"${rankingTournamentsPlaceholder}\" name=\"tournaments[" + i + "].name\"/>" +
                    "<input type=\"number\" placeholder=\"${awardedPointsPlaceholder}\" name=\"tournaments[" + i + "].points\"/>" +
                    "</div>"

                );
                i = i + 1;
            });

        });

</script>

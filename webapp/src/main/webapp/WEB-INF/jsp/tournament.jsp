<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="/resources/css/tournament.css"/>" />
        <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" />
        <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
        <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    </head>
    <body>
        <c:import var="navbar" url="header.jsp"/>
        ${navbar}
        <div class="container">
            <h2><spring:message code="tournament.greeting" arguments="${tournament.name}"/></h2>
            <h2><spring:message code="tournament.id" arguments="${tournament.id}"/></h2>
            <ul>
                <table class="highlight">
                    <thead>
                    <tr>
                        <th><spring:message code="table.players"/></th>
                        <th><spring:message code="table.id"/></th>
                    </tr>
                    </thead>
                        <c:forEach var="player" items="${tournament.players}">
                    <tbody>
                    <tr>
                        <td><c:out value="${player.name}"/></td>
                        <td><c:out value="${player.id}"/></td>
                    </tbody>
                        </c:forEach>
                </table>
            </ul>
            <ul>
                <c:forEach var="match" items="${tournament.matches}">
                    <c:if test="${match.homePlayerId != 0 && match.awayPlayerId != 0}">
                        <li><c:out value="${match}" /></li>
                        <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                        <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                            <div>
                                <form:label path="homeResult">Home: </form:label>
                                <form:input type="number" path="homeResult" value="${match.homePlayerScore}"/>
                                <form:errors path="homeResult" cssClass="formError" element="p"/>
                            </div>
                            <div>
                                <form:label path="awayResult">Away: </form:label>
                                <form:input type="number" path="awayResult" value="${match.awayPlayerScore}"/>
                                <form:errors path="awayResult" cssClass="formError" element="p"/>
                            </div>
                            <div>
                                <button class="btn waves-effect waves-light" type="submit" name="action">Update
                                    <i class="material-icons right">update</i>
                                </button>
                            </div>
                        </form:form>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    </body>
</html>
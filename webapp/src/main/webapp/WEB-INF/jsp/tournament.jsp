<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="/resources/css/tournament.css"/>" />
        <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    </head>
    <body>
        <nav>
            <div class="nav-wrapper">
                <a href="<c:url value="/"/>" class="brand-logo center">Logo</a>
            </div>
        </nav>
        <div class="container">
            <h2><spring:message code="tournament.greeting" arguments="${tournament.name}"/></h2>
            <h2><spring:message code="tournament.id" arguments="${tournament.id}"/></h2>
            <ul>
                <c:forEach var="player" items="${tournament.players}">
                    <li><c:out value="${player}" /></li>
                </c:forEach>
            </ul>
            <ul>
                <c:forEach var="match" items="${tournament.matches}">
                    <li><c:out value="${match}" /></li>
                    <c:if test="${match.homePlayerId != 0 && match.awayPlayerId != 0}">
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
                                <input type="submit" value="Update"/>
                            </div>
                        </form:form>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="resources/css/tournament.css"/>" />
    </head>
    <body>
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
                <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                    <div>
                        <form:label path="homeResult">Home: </form:label>
                        <form:input type="number" path="homeResult"/>
                        <form:errors path="homeResult" cssClass="formError" element="p"/>
                    </div>
                    <div>
                        <form:label path="awayResult">Away: </form:label>
                        <form:input type="number" path="awayResult" />
                        <form:errors path="awayResult" cssClass="formError" element="p"/>
                    </div>
                    <div>
                        <input type="submit" value="Update"/>
                    </div>
                </form:form>
            </c:forEach>
        </ul>
    </body>
</html>
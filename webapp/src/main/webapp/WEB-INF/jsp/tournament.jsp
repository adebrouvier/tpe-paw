<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="/resources/css/tournament.css"/>" />
        <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" />
        <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
        <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
        <title>${tournament.name}</title>
    </head>
    <body>
        <c:import var="navbar" url="header.jsp"/>
        ${navbar}
        <div class="container">
            <h2><spring:message code="tournament.greeting" arguments="${tournament.name}"/></h2>
            <div class="tournament-container">
                <div class="row valign-wrapper">
                    <c:set var="roundSize" value="${tournament.players.size()/2}"/>
                    <c:set var="aux" value="1"/>
                    <c:forEach var="match" items="${tournament.matches}">
                        <c:if test="${aux == 1}">
                            <div class="col l3 valign center-align" >
                            <ul>
                        </c:if>
                        <li>
                            <div class="card">
                                <div class="card-content">
                                    <span class="card-title"><spring:message code="match.id" arguments="${match.id}"/></span>
                                    <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                                    <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                                        <div class="input-field inline">
                                            <form:label path="homeResult"><c:out value="${match.homePlayer.name}" />: </form:label>
                                            <form:input type="number" path="homeResult" value="${match.homePlayerScore}"/>
                                            <form:errors path="homeResult" cssClass="formError" element="p"/>
                                        </div>
                                        <div class="input-field inline">
                                            <form:label path="awayResult"><c:out value="${match.awayPlayer.name}" />: </form:label>
                                            <form:input type="number" path="awayResult" value="${match.awayPlayerScore}"/>
                                            <form:errors path="awayResult" cssClass="formError" element="p"/>
                                        </div>
                                        <div>
                                            <button class="btn waves-effect waves-light" type="submit">
                                                <spring:message code="tournament.update"/>
                                                <i class="material-icons right">update</i>
                                            </button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </li>
                            <c:if test="${aux == roundSize}">
                                </ul>
                                </div>
                                <c:set var="aux" value="0"/>
                                <c:set var="roundSize" value="${roundSize/2}"/>
                            </c:if>
                        <c:set var="aux" value="${aux+1}"/>
                    </c:forEach>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    </body>
</html>
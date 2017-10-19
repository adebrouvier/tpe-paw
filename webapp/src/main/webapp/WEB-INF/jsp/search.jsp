<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/search.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title><spring:message code="search.title"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<div class="container">
    <h3><spring:message code="search.header"/></h3>
    <c:set var="nOfResults" value="${tournamentResults.size()}"/>
    <c:choose>
        <c:when test="${nOfResults == 0}">
            <h5><spring:message code="search.notfound"/></h5>
        </c:when>
        <c:when test="${nOfResults == 1}">
            <h5><spring:message code="search.onefound"/></h5>
        </c:when>
        <c:when test="${nOfResults > 1}">
            <h5><spring:message code="search.found" arguments="${nOfResults}"/></h5>
        </c:when>
    </c:choose>
    <div class="results">
        <table>
            <thead>
                <tr>
                    <th><spring:message code="search.table.name"/></th>
                    <th><spring:message code="search.table.game"/></th>
                    <th><spring:message code="search.table.players"/></th>
                    <th><spring:message code="search.table.page"/></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="tournament" items="${tournamentResults}">
                <tr>
                    <td><c:out value="${tournament.name}"/></td>
                    <td>Game</td>
                    <td><c:out value="${tournament.size}"/></td>
                    <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>

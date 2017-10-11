<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/search.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <title>Search - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}

    <div class="container">
        <h3>Tournament search results</h3>
        <c:set var="nOfResults" value="${tournamentResults.size()}"/>
        <c:choose>
            <c:when test="${nOfResults == 0}">
                <h5>No results found.</h5>
            </c:when>
            <c:when test="${nOfResults == 1}">
                <h5>1 result found.</h5>
            </c:when>
            <c:when test="${nOfResults > 1}">
                <h5>${nOfResults} results found.</h5>
            </c:when>
        </c:choose>
        <div class="results">
            <table>
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Game</td>
                        <td>Players</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="tournament" items="${tournamentResults}">
                    <tr>
                        <td><c:out value="${tournament.name}"/></td>
                        <td>Game</td>
                        <td>15</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>

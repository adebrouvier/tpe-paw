<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><spring:message code="search.title"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
<div class="container">
    <div class="row">
        <c:url value="/searchParse" var="getPath"/>
        <form:form modelAttribute="searchForm" action="${getPath}" method="get">
            <div class="col s12">
                <i id="search-icon" class="material-icons">search</i>
                <div class="input-field">
                    <spring:message code="index.search.placeholder" var="indexSearchPlaceholder"/>
                    <form:input cssClass="typeahead" type="text" placeholder="${indexSearchPlaceholder}" path="query" autocomplete="off"/>
                    <form:errors path="query" cssClass="form-error" element="p"/>
                    <input type="submit" style="visibility: hidden"/>
                </div>
            </div>
        </form:form>
    </div>
    <h3><spring:message code="search.header"/></h3>
    <c:set var="nOfResults" value="${tournamentResults.size() + rankingResults.size()}"/>
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
    <c:if test="${nOfResults > 0}">
    <div class="results">
        <div class="card">
            <div class="card-content">
                <h5 class="card-title"><spring:message code="search.tournaments"/> </h5>
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
                            <td><c:out value="${tournament.game.name}"/></td>
                            <td><c:out value="${tournament.size}"/></td>
                            <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${tournamentResults == null}">
                        <tr>
                            <td><spring:message code="search.notfound" /></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card">
            <div class="card-content">
                <h5 class="card-title"><spring:message code="search.rankings"/></h5>
                <table id="ranking-results">
                    <thead>
                    <tr>
                        <th><spring:message code="search.table.name"/></th>
                        <th><spring:message code="search.table.page"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ranking" items="${rankingResults}">
                        <tr>
                            <td><c:out value="${ranking.name}"/></td>
                            <td><a href="<c:url value = "/ranking/${ranking.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${rankingResults.size() == 0 || rankingResults == null}">
                        <tr>
                            <td><spring:message code="search.notfound" /></td>
                            <td></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </c:if>
</div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>

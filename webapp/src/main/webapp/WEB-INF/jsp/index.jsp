<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
    <title><spring:message code="index.title"/></title>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="section no-pad-bot" id="index-banner">
    <div class="container">
        <div>
            <c:url value="/searchtournament" var="getPath"/>
            <form:form modelAttribute="searchForm" action="${getPath}" method="get">
                <div class="input-field">
                    <div id="autocomplete-search" data-search="<c:out value="${tournamentNames}"/>"></div>
                    <i class="material-icons prefix">search</i>
                    <spring:message code="index.search.placeholder" var="indexSearchPlaceholder"/>
                    <form:input placeholder="${indexSearchPlaceholder}" type="text" cssClass="autocomplete autocomplete-search-input" placeholdertype="text" path="query" autocomplete="off"/>
                    <form:label path="query"><spring:message code="index.search"/></form:label>
                    <form:errors path="query" cssClass="form-error" element="p"/>
                </div>
            </form:form>
        </div>
        <h1 class="header center"><spring:message code="index.header.title"/></h1>
        <div class="row center">
            <h5 class="header col s12 light"><spring:message code="index.header.subtitle"/></h5>
        </div>
        <div class="row center">
            <a href="<c:url value = "/tournament"/>" class="btn-large waves-effect waves-light light-blue darken-4"><spring:message code="index.header.tournament.button"/></a>
            <a href="<c:url value = "/ranking"/>" class="btn-large waves-effect waves-light light-blue darken-4"><spring:message code="index.header.ranking.button"/></a>
        </div>
        <br><br>

    </div>
</div>
<div class="featured-tournaments container">
    <h4 class="center"><spring:message code="index.tournaments"/></h4>
    <table class="highlight centered">
        <thead>
        <tr>
            <th><spring:message code="index.tournaments.table.name"/></th>
            <th><spring:message code="index.tournaments.table.players"/></th>
            <th><spring:message code="index.tournaments.table.matches"/></th>
            <th><spring:message code="index.tournaments.table.page"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tournament" items="${tournaments}">
            <tr>
                <td>${tournament.name}</td>
                <td>${tournament.size}</td>
                <td>${tournament.numberOfMatches}</td>
                <td><a href="<c:url value = "/tournament/${tournament.id}"/>"><i class="material-icons black-text">info_outline</i></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: flacu
  Date: 08/10/17
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <title><spring:message code="ranking.title"/></title>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="container center col 6">
    <c:url value="/createRanking" var="postPath"/>
    <form:form modelAttribute="rankingForm" action="${postPath}" method="post">
    <h2><spring:message code="ranking.create.title"/></h2>
    <div class="row">
        <div class="col offset-s3 s6">
            <div class="input-field">
                <form:label path="rankingName"><spring:message code="ranking.create.name"/>: </form:label>
                <spring:message code="ranking.create.name.placeholder" var="rankingNamePlaceholder"/>
                <form:input placeholder="${rankingNamePlaceholder}" type="text" data-length="20" path="rankingName"
                            autocomplete="off"/>
                <form:errors path="rankingName" cssClass="form-error" element="p"/>
            </div>
            <div class="input-field">
                <form:label path="tournamentId"><spring:message code="ranking.create.tournamentId"/>: </form:label>
                <spring:message code="ranking.create.tournamentId.placeholder" var="rankingTournamentIdPlaceholder"/>
                <form:input placeholder="${rankingTournamentIdPlaceholder}" type="text" data-length="20"
                            path="tournamentId"
                            autocomplete="off"/>
                <form:errors path="tournamentId" cssClass="form-error" element="p"/>
            </div>
            <div class="input-field">
                <form:label path="awardedPoints"><spring:message code="ranking.create.awardedPoints"/>: </form:label>
                <spring:message code="ranking.create.awardedPoints.placeholder" var="rankingAwardedPointsPlaceholder"/>
                <form:input placeholder="${rankingAwardedPointsPlaceholder}" type="text" data-length="20"
                            path="awardedPoints"
                            autocomplete="off"/>
                <form:errors path="awardedPoints" cssClass="form-error" element="p"/>
            </div>
            <button class="btn btn-primary" type="submit"><spring:message code="ranking.create.submit"/></button>
            </form:form>
        </div>
    </div>


</div>

<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>

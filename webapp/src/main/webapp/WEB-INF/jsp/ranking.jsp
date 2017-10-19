<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="set" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
    href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/ranking.css"/>"/>
    <title><spring:message code="ranking.title"/></title>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<div class="container center">
    <c:url value="/create/ranking" var="postPath"/>
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
                <spring:message code="ranking.create.tournaments.placeholder" var="rankingTournamentsPlaceholder"/>
                <spring:message code="ranking.create.awardedPoints.placeholder" var="awardedPointsPlaceholder"/>
            </div>
            <div id="ranking-tournaments">
                <c:forEach items="${rankingForm.tournaments}" varStatus="i">
                    <set:set var="index" value="${i.index}"/>
                    <div class="input-field">
                        <form:label path="tournaments[${index}].name"><spring:message code="ranking.create.tournaments"/>: </form:label>
                        <form:input path="tournaments[${index}].name" type="text" placeholder="${rankingTournamentsPlaceholder}"/>
                        <form:errors path="tournaments[${index}].name" cssClass="form-error" element="p"/>
                    </div>
                    <div class="input-field">
                        <form:label path="tournaments[${index}].name"><spring:message code="ranking.create.awardedPoints"/>: </form:label>
                        <form:input path="tournaments[${index}].points" type="number" placeholder="${awardedPointsPlaceholder}"/>
                        <form:errors path="tournaments[${index}].points" cssClass="form-error" element="p"/>
                    </div>
                </c:forEach>
                <form:errors path="tournaments" cssClass="form-error" element="p"/>
            </div>
        </div>
            <div class="row center">
                <div class="col offset-s5">
                    <a id="tournament-adder" class="btn btn-primary light-blue darken-4"><spring:message code="ranking.tournament.add"/></a>
                    <br/>
                    <br/>
                    <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message code="ranking.create.submit"/></button>
                </div>
            </div>

            </form:form>
    </div>
</div>

    <script>

        $(document).ready(

            function() {

                <c:set var="listIndex" value="0"/>
                <c:if test="${rankingForm.tournaments.size() > 0}">
                    <c:set var="listIndex" value="${rankingForm.tournaments.size()}"/>
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

</body>
</html>

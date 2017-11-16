<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament-page.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${tournament.name}"/> - <spring:message code="tournament.standings"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <div class="image-container" >
        <div class="image-effect" style="background: linear-gradient(rgba(38, 42, 53, 0.25) 65%, rgb(38, 42, 53) 100%), url('<c:out value="${tournament.game.gameUrlImage.urlImage}"/>') center center"></div>
    </div>
    <div class="container">
        <div class="row">
            <c:import var="tournamentInfo" url="tournament-info.jsp"/>
            ${tournamentInfo}
        </div>
        <div class="row card">
            <div class="col s12">
                <div class="row tournament-tabs">
                    <div class="col s12">
                        <ul class="tabs">
                            <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}"/>"><spring:message code="tournament.bracket"/></a></li>
                            <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}/standings"/>"><spring:message code="tournament.standings"/></a></li>
                            <li class="tab col s3"><a target="_self" href="<c:url value="/tournament/${tournament.id}/players"/>"><spring:message code="tournament.players"/></a></li>
                            <li class="tab col s3"><a class="active" href="#"><spring:message code="tournament.comments"/></a></li>
                        </ul>
                    </div>
                </div>
                <div>
                    <c:forEach var="comment" items="${tournament.comments}">
                        <div class="card">
                            <div class="card-content">
                                <p>${comment.comment}</p>
                                <hr>
                                <span><a href="<c:url value="/user/${comment.creator.id}"/>">${comment.creator.name}</a> - ${comment.date.toLocaleString()}</span>
                            </div>
                        </div>
                    </c:forEach>
                    <c:url value="/comment/tournament/${tournament.id}/" var="endPath"/>
                    <form:form modelAttribute="commentForm" action="${endPath}" method="post">
                        <div class="input-field">
                            <form:textarea cssClass="materialize-textarea" type="text" path="comment"/>
                            <form:errors path="comment" cssClass="form-error" element="p"/>
                        </div>
                        <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message
                                code="tournament.comment"/></button>
                    </form:form>

                </div>
            </div>
        </div>
    </div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
</body>
</html>
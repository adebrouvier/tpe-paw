<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet"
          href="<c:url value="/resources/css/notifications.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/user.css"/>">
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/notification.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${user.name}"> </c:out> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <div class="container" id="content-box">
        <div class="row" id="content-wrapper">
            <c:forEach var="notification" items="${notifications}">
                    <div class="col offset-s2 s8">
                        <div class="card">
                            <div class="card-content">
                                <c:if test="${notification.type eq 'PARTICIPATES_IN_TOURNAMENT'}">
                                    <div class="row valign-wrapper" style="margin-bottom: 0">
                                        <div class="col s1">
                                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-add.jpg"/>"></div>
                                        <div class="col s11">
                                            <span><b><a href="/user/<c:out value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.addUser"/> <b><a href="/tournament/<c:out value="${notification.decodeDescription.get(2)}/players"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${notification.type eq 'FIRST_PLACE'}">
                                    <div class="row valign-wrapper" style="margin-bottom: 0">
                                        <div class="col s1">
                                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-first.jpg"/>"></div>
                                        <div class="col s11">
                                            <span><b><a href="/user/<c:out value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.firstPlace"/> <b><a href="/tournament/<c:out value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${notification.type eq 'SECOND_PLACE'}">
                                    <div class="row valign-wrapper" style="margin-bottom: 0">
                                        <div class="col s1">
                                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-second.jpg"/>"></div>
                                        <div class="col s11">
                                            <span><b><a href="/user/<c:out value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.secondPlace"/> <b><a href="/tournament/<c:out value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${notification.type eq 'THIRD_PLACE'}">
                                    <div class="row valign-wrapper" style="margin-bottom: 0">
                                        <div class="col s1">
                                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-third.jpg"/>"></div>
                                        <div class="col s11">
                                            <span><b><a href="/user/<c:out value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.thirdPlace"/> <b><a href="/tournament/<c:out value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="card-action">
                                <span class="grey-text"><c:out value="${notification.date.toLocaleString()}"/></span>
                            </div>
                        </div>
                    </div>
            </c:forEach>
        </div>

    </div>
</main>
</body>
</html>
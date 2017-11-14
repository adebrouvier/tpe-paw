<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/user.css"/>">
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${user.name}"> </c:out> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <c:forEach var="notification" items="${notifications}">
        <div>

            <p>
            <c:out value="${notification.user.name}"/>
            </p>

            <p>
            <c:out value="${notification.isRead}"/>
            </p>

            <p>
            <c:out value="${notification.date}"/>
            </p>

            <p>
            <c:out value="${notification.type}"/>
            </p>


            <p>
            <c:out value="${notification.notificationId}"/>
            </p>
            <p>
                <c:out value="${notification.decodeDescription.get(0)}${notification.decodeDescription.get(1)}${notification.decodeDescription.get(2)}${notification.decodeDescription.get(3)}"/>
            </p>
        </div>
    </c:forEach>
</main>
</body>
</html>
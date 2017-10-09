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

    <div class="row">
        <div class="col offset-s3 s6">
            <h2><spring:message code="ranking.greeting" arguments="${ranking.name}"/></h2>
        </div>
    </div>
</div>
<div class="container">
    <h4 class="center"><spring:message code="ranking.table.title"/></h4>
    <table class="highlight centered">
        <thead>
        <tr>
            <th><spring:message code="ranking.table.players"/></th>
            <th><spring:message code="ranking.table.points"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="player" items="${ranking.players}">
            <tr>
                <td>${player.name}</td>
                <td>${player.points}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>

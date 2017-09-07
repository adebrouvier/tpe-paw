<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="resources/css/tournament.css"/>" />
    </head>
    <body>
        <h2><spring:message code="tournament.greeting" arguments="${tournament.name}"/></h2>
        <h2><spring:message code="tournament.id" arguments="${tournament.id}"/></h2>
    </body>
</html>
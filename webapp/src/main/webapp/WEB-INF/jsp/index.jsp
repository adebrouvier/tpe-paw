<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value="resources/css/index.css"/>" />
    </head>
    <body>
    <h2>Create a Tournament</h2>
    <c:url value="/create" var="postPath"/>
    <form:form modelAttribute="tournamentForm" action="${postPath}" method="post">
        <div>
            <form:label path="tournamentName">Name: </form:label>
            <form:input type="text" path="tournamentName"/>
            <form:errors path="tournamentName" cssClass="formError" element="p"/>
        </div>
        <div>
            <form:label path="players">Players: </form:label>
            <form:textarea type="text" path="players" />
            <form:errors path="players" cssClass="formError" element="p"/>
        </div>
        <div>
            <input type="submit" value="Create!"/>
        </div>
    </form:form>
    </body>
</html>
</html>
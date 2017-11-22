<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach var="ranking" items="${rankings}">
    <div class="col s8 m8 l8 right card card-style">
        <div class="card-content">
            <div class="card-title">
                <c:out value="${ranking.name}"/> - <c:out value="${ranking.game.name}"/>
            </div>
        </div>
        <div class="card-action">
            <a href="<c:url value="/ranking/${ranking.id}#ranking-tournaments"/>"><spring:message code="ranking.tournaments"/></a>
            <a href="<c:url value="/ranking/${ranking.id}#ranking-players"/>"><spring:message code="ranking.players"/></a>
        </div>
    </div>
</c:forEach>
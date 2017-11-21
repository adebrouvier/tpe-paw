<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach var="tournament" items="${tournaments}">
    <div class="col s8 m8 l8 right card card-style">
        <div class="card-content">
            <div class="card-title">
                <c:out value="${tournament.name}"/> - <c:out value="${tournament.game.name}"/>
            </div>
            <p>
                <c:forEach var="player" items="${tournament.players}">
                    <c:if test="${player.user != null && player.user.id == user.id && player.standing != null}">
                        <b><spring:message code="user.endPosition"/> #<c:out value="${player.standing}"/></b>
                    </c:if>
                    <c:if test="${player.user != null && player.user.id == user.id && player.standing == null}">
                        <b><spring:message code="user.tournamentDontStart"/></b>
                    </c:if>
                </c:forEach>
            </p>
        </div>
        <div class="card-action">
            <a href="<c:url value="/tournament/${tournament.id}"/>"><spring:message code="tournament.bracket"/></a>
            <a href="<c:url value="/tournament/${tournament.id}/standings"/>"><spring:message code="tournament.standings"/></a>
        </div>
    </div>
</c:forEach>

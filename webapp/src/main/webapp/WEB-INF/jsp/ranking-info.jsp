<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col s12">
    <div class="card">
        <div class="card-content">
            <span class="card-title center"><c:out value="${ranking.name}"/></span>
            <table>
                <thead>
                <tr>
                    <th><i class="tiny material-icons">games</i> <spring:message code="ranking.info.game"/></th>
                    <th>Players</th>
                    <th>Tournaments</th>
                    <th><spring:message code="ranking.info.creator"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><c:out value="${ranking.game.name}"/></td>
                    <td><c:out value="${fn:length(ranking.userScores)}"/></td>
                    <td><c:out value="${fn:length(ranking.tournaments)}"/></td>
                    <td><a href="<c:url value="/user/${ranking.user.id}"/>"><c:out value="${ranking.user.name}"/></a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
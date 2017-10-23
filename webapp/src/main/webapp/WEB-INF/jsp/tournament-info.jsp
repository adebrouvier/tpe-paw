<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col s12">
    <div class="tournament-info card">
        <div class="card-content">
            <span class="card-title center"><c:out value="${tournament.name}"/></span>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="tournament.info.players"/></th>
                    <th><spring:message code="tournament.info.matches"/></th>
                    <th><i class="tiny material-icons">games</i> <spring:message code="tournament.info.game"/></th>
                    <th><spring:message code="tournament.info.creator"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${tournament.size}</td>
                    <td>${tournament.numberOfMatches}</td>
                    <td><c:if test="${game.name == ''}">
                        <spring:message code="tournament.undefined.game"/>
                    </c:if>
                        <c:if test="${game.name != null}">
                            <c:out value="${game.name}"/>
                        </c:if>
                    </td>
                    <td>${creator.name}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
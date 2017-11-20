<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:forEach var="notification" items="${notifications}">
    <div class="col offset-s2 s8">
        <div class="card">
            <div class="card-content">
                <c:if test="${notification.type eq 'PARTICIPATES_IN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s2">
                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-add.jpg"/>"></div>
                        <div class="col s10">
                            <span><b><a href="/user/<c:out value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.addUser"/> <b><a href="/tournament/<c:out value="${notification.decodeDescription.get(2)}/players"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
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

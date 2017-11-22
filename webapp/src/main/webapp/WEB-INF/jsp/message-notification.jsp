<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:forEach var="notification" items="${notifications}">
    <div class="col offset-s2 s8">
        <div class="card">
            <div class="card-content">
                <c:if test="${notification.type eq 'PARTICIPATES_IN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:out value="/resources/img/trophy-add.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.addUser"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>/players"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'FIRST_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-first.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.firstPlace"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'SECOND_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-second.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.secondPlace"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'THIRD_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-third.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.thirdPlace"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'ACCEPT_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-third.jpg"/>"></div>
                        <div class="col s11">
                            <span><spring:message code="notification.acceptJoinTournament"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(0)}"/>/players"><c:out value="${notification.decodeDescription.get(1)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REJECT_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-third.jpg"/>"></div>
                        <div class="col s11">
                            <span><spring:message code="notification.rejectJoinTournamentPartOne"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(0)}"/>/players"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.rejectJoinTournamentPartTwo"/></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REQUEST_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-third.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.requestJoinTournament"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>/players"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REPLY_TOURNAMENT_COMMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/trophy-third.jpg"/>"></div>
                        <div class="col s11">
                            <span><b><a href="/user/<c:url value="${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.ReplyTournamentComment"/> <b><a href="/tournament/<c:url value="${notification.decodeDescription.get(2)}"/>/comments#<c:url value="${notification.decodeDescription.get(4)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
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

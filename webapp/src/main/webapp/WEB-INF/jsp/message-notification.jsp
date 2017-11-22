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
                            <img class="notification-icon" src="<c:url value="/resources/img/not-add-trophy.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.addUser"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}/players"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'FIRST_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-gold-trophy.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.firstPlace"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'SECOND_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-silver-trophy.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.secondPlace"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'THIRD_PLACE'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-bronze-trophy.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.thirdPlace"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'ACCEPT_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-accept.png"/>"></div>
                        <div class="col s11">
                            <span><spring:message code="notification.acceptJoinTournament"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(0)}/players"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REJECT_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-reject.png"/>"></div>
                        <div class="col s11">
                            <span><spring:message code="notification.rejectJoinTournamentPartOne"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(0)}/players"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.rejectJoinTournamentPartTwo"/></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REQUEST_JOIN_TOURNAMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-request.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.requestJoinTournament"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}/players"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'REPLY_TOURNAMENT_COMMENT'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-replay.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.ReplyTournamentComment"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}/comments#comment-${notification.decodeDescription.get(4)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b></span>
                        </div>
                    </div>
                </c:if>
                <c:if test="${notification.type eq 'ADD_TOURNAMENT_TO_RANKING'}">
                    <div class="row valign-wrapper" style="margin-bottom: 0">
                        <div class="col s1">
                            <img class="notification-icon" src="<c:url value="/resources/img/not-add-trophy.png"/>"></div>
                        <div class="col s11">
                            <span><b><a href="<c:url value="/user/${notification.decodeDescription.get(0)}"/>"><c:out value="${notification.decodeDescription.get(1)}"/></a></b> <spring:message code="notification.AddTournamentToRankingPartOne"/> <b><a href="<c:url value="/tournament/${notification.decodeDescription.get(2)}"/>"><c:out value="${notification.decodeDescription.get(3)}"/></a></b> <spring:message code="notification.AddTournamentToRankingPartTwo"/> <b><a href="<c:url value="/ranking/${notification.decodeDescription.get(4)}"/>"><c:out value="${notification.decodeDescription.get(5)}"/></a></b></span>
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

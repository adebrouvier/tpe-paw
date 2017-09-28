<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/tournament.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/tournament.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <title>${tournament.name}</title>
</head>
<body>
<c:import var="navbar" url="header.jsp"/>
${navbar}
<div class="center">
    <h2><spring:message code="tournament.greeting" arguments="${tournament.name}"/></h2>
</div>
<div>
    <div class="tournament-container">
        <div class="row">
            <c:set var="roundSize" value="${tournament.players.size()/2}"/>
            <c:set var="matchCount" value="1"/>
            <c:set var="margin" value="0"/>
            <c:set var="padding" value="16"/>
            <c:forEach var="match" items="${tournament.matches}">
                <c:if test="${matchCount == 1}">
                    <div class="col">
                    <div class="bracket-container" style="margin-top: ${margin}">
                </c:if>
                <c:if test="${matchCount != 1}">
                    <div class="bracket-container " style="padding-top: ${padding}">
                </c:if>

                    <span class="match-id-container">
                        <span class="match-id">
                            <c:out value="${match.id}"/>
                        </span>
                        <span class="triangle"></span>
                    </span>
                    <a class="modal-trigger" href="#modal-${match.id}">
                        <div class="bracket">
					        <span class="players-name">
		                        <div class="local-player-name">
                                    <c:out value="${match.homePlayer.name}"> </c:out>
		                        </div>
		                        <div class="away-player-name">
                                    <c:out value="${match.awayPlayer.name}"> </c:out>
                                </div>
                            </span>
                            <span class="players-score">
                                <div class="local-player-score">
                                    <c:out value="${match.homePlayerScore}"> </c:out>
                                </div>
                                <div class="away-player-score">
                                    <c:out value="${match.awayPlayerScore}"> </c:out>
                                </div>
		                    </span>
                        </div>
                    </a>

                </div>

                <div id="modal-${match.id}" class="modal" style="width: 25% !important;">

                        <c:url value="/update/${tournament.id}/${match.id}" var="postPath"/>
                        <form:form modelAttribute="matchForm" action="${postPath}" method="post">
                            <div class="modal-content">
                            <div class="input-field inline">
                                <form:label path="homeResult"><c:out value="${match.homePlayer.name}"/>:
                                </form:label>
                                <form:input min="0" type="number" path="homeResult" value="${match.homePlayerScore}"/>
                                <form:errors path="homeResult" cssClass="formError" element="p"/>
                            </div>
                            <div class="input-field inline">
                                <form:label path="awayResult"><c:out value="${match.awayPlayer.name}"/>:
                                </form:label>
                                <form:input min="0" type="number" path="awayResult" value="${match.awayPlayerScore}"/>
                                <form:errors path="awayResult" cssClass="formError" element="p"/>
                            </div>
                            </div>
                                <div class="modal-footer">
                                <c:if test="${match.awayPlayerId == -1 || match.awayPlayerId == 0 || match.homePlayerId == 0}">
                                    <button class="btn waves-effect waves-light disabled" type="submit">
                                        <spring:message code="tournament.update"/>
                                        <i class="material-icons right ">update</i>
                                    </button>
                                </c:if>
                                <c:if test="${match.awayPlayerId != -1 && match.awayPlayerId != 0 && match.homePlayerId != 0}">
                                    <button class="btn waves-effect waves-light" type="submit">
                                        <spring:message code="tournament.update"/>
                                        <i class="material-icons right">update</i>
                                    </button>
                                </c:if>

                            </div>
                        </form:form>



                </div>


                <c:if test="${matchCount == roundSize}">
                    </div>
                    <c:set var="matchCount" value="0"/>
                    <c:set var="roundSize" value="${roundSize/2}"/>
                    <c:set var="margin" value="${(padding+44)/2+margin}"/>
                    <c:set var="padding" value="${2*margin+16}"/>
                </c:if>
                <c:set var="matchCount" value="${matchCount+1}"/>
            </c:forEach>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript">contextPath = '<%=request.getContextPath()%>';</script>

    <title><spring:message code="match.edit.title"/> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <div class="container">
        <div class="row">
            <div class="col offset-s3 offset-m3 offset-l3 s6 m6 l6 card">
                <h5 class="center"><spring:message code="match.edit"/></h5>
                <c:url value="/update/match/${match.tournament.id}/${match.id}" var="postPath"/>
                <form:form modelAttribute="matchDataForm" action="${postPath}" method="post">
                    <div class="row">
                        <div class="input-field inline col s8">
                            <form:label path="homePlayerCharacter">${match.homePlayer.name}<spring:message
                                    code="match.character"/>: </form:label>
                            <form:input min="0" id="home-character-${match.id}" type="text" path="homePlayerCharacter"
                                        value="${match.homePlayerCharacter}"/>
                            <form:errors path="homePlayerCharacter" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="row">
                        <div class="input-field inline col s8">
                            <form:label path="awayPlayerCharacter">${match.awayPlayer.name}<spring:message
                                    code="match.character"/>: </form:label>
                            <form:input min="0" id="away-character-${match.id}" type="text" path="awayPlayerCharacter"
                                        value="${match.awayPlayerCharacter}"/>
                            <form:errors path="awayPlayerCharacter" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="row">
                        <div class="input-field inline col s8">
                            <i class="material-icons prefix">location_on</i>
                            <form:label path="map"><spring:message code="match.map"/>: </form:label>
                            <form:input min="0" id="map-${match.id}" type="text" path="map" value="${match.map}"/>
                            <form:errors path="map" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="row">
                        <div class="input-field inline col s8">
                            <i class="material-icons prefix">ondemand_video</i>
                            <form:label path="vodLink"><spring:message code="match.vodLink"/>: </form:label>
                            <form:input min="0" id="VOD-${match.id}" type="text" path="vodLink"
                                        value="${match.videoOnDemandLink}"/>
                            <form:errors path="vodLink" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 center">
                            <button class="btn btn-primary light-blue darken-4" type="submit"><spring:message
                                    code="match.edit.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</main>
<c:import var="footer" url="footer.jsp"/>
${footer}
<script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
</body>
</html>
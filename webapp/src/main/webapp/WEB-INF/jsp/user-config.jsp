<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>">
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/user.css"/>">
    <script type="text/javascript" src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/corejs-typeahead/1.2.1/typeahead.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>"/>
    <script type="text/javascript" src="<c:url value="/resources/js/navbar.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/game-autocomplete.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/user-config.js"/>"></script>
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>

    <title><c:out value="${user.name}"> </c:out> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <div class="container">

        <div class="row">
            <div class="col offset-s3 offset-m3 offset-l3 s6 m6 l6 card" style="padding: 0">
                <c:url value="/update/${user.id}" var="postPath"/>
                <form:form modelAttribute="userUpdateForm" action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="section" >
                        <div class="user-section-center" style="padding-bottom: 20px">
                            <img id="user-preview" src="/profile-image/${user.id}" class="user-img" />
                        </div>
                        <div class="user-section-center">
                            <form:label cssClass="btn btn-primary light-blue darken-4 fileContainer" for="file" path="image">add image
                                <form:input placeholder="pepeps" class="file-input" onchange="readURL(this);" autocomplete="off" type="file" path="image" accept="image/*"/>
                            </form:label>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="section" style="margin: 10px 40px">
                        <div class="input-field">
                            <div id="description-text-area" style="display: none" data-textarea="<c:out value="${user.description}"/>"></div>
                            <form:label class="active" path="description">Description:</form:label>
                            <form:textarea cssClass="materialize-textarea" id="description-textarea" path="description"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="section" style="margin: 10px 40px">
                        <div class="input-field">
                            <form:label class="active" path="game"><spring:message code="tournament.create.game"/>:</form:label>
                            <form:input id="game-search" type="text" cssClass="typeahead" path="game"
                                        autocomplete="off" value="${gameName}"/>
                            <form:errors path="game" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="section" style="margin: 10px 40px">
                        <div class="input-field">
                            <form:label class="active" path="game">Twitch:</form:label>
                            <form:input id="game-search" type="text" cssClass="typeahead" path="game"
                                        autocomplete="off" value="${gameName}"/>
                            <form:errors path="game" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="section" style="margin: 10px 40px">
                        <div class="input-field">
                            <form:label class="active" path="game">Twitter:</form:label>
                            <form:input id="game-search" type="text" cssClass="typeahead" path="game"
                                        autocomplete="off" value="${gameName}"/>
                            <form:errors path="game" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="section" style="margin: 10px 40px">
                        <div class="input-field">
                            <form:label class="active" path="game">Facebook:</form:label>
                            <form:input id="game-search" type="text" cssClass="typeahead" path="game"
                                        autocomplete="off" value="${gameName}"/>
                            <form:errors path="game" cssClass="form-error" element="p"/>
                        </div>
                    </div>
                    <div class=right style="padding-top: 50px;padding-bottom: 20px;padding-right: 20px">
                        <form:button class="btn btn-primary light-blue darken-4" type="submit">save</form:button>
                    </div>
                </form:form>
            </div>
        </div>

    </div>

</main>

</body>
</html>

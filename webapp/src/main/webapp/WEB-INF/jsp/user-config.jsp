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
            <div class="col offset-s4 offset-m4 offset-l4 s4 m4 l4 card">
                <c:url value="/updateImage/${user.id}" var="postPath"/>
                <form:form modelAttribute="userImageForm" action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="user-section-center" style="padding-bottom: 20px">
                        <img id="user-preview" src="/profile-image/${user.id}" class="user-img" />
                    </div>
                    <div class="user-section-center">
                        <form:label cssClass="btn btn-primary light-blue darken-4 fileContainer" for="file" path="image">add image
                            <form:input placeholder="pepeps" class="file-input" onchange="readURL(this);" autocomplete="off" type="file" path="image" accept="image/*"/>
                        </form:label>
                    </div>
                    <div class=right style="padding-top: 50px;padding-bottom: 20px">
                        <form:button class="btn btn-primary light-blue darken-4" type="submit">update</form:button>
                    </div>
                </form:form>
            </div>
        </div>

    </div>

</main>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <script type="text/javascript">contextPath='<%=request.getContextPath()%>';</script>
    <title><c:out value="${user.name}"> </c:out> - <spring:message code="header.name"/></title>
</head>
<body>
<c:import var="navbar" url="navbar.jsp"/>
${navbar}
<main>
    <div class="container">
        <div class="row s12 m12 l12">
            <c:import var="userInfo" url="user-info.jsp"/>
            ${userInfo}
            <div class="col s8 m8 l8 card">
                <div class="row tournament-tabs">
                    <div class="col s12">
                        <ul class="tabs">
                            <li class="tab col s3"><a class="active" href="#">participates</a></li>
                            <li class="tab col s3"><a target="_self" href="<c:url value="/user/${user.id}/creates"/>">creates</a></li>
                            <li class="tab col s3"><a target="_self" href="<c:url value="/user/${user.id}/followers"/>">followers</a></li>
                            <li class="tab col s3"><a target="_self" href="<c:url value="/user/${user.id}/followed"/>">followed</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col s8 m8 l8 card card-style">
                <div class="card-content">
                    <div class="card-title">
                        super tournament - mario bross
                    </div>
                    <p>
                        #position 5
                    </p>
                </div>
                <div class="card-action">
                    <a href="#">Go Standings</a>
                    <a href="#">Go brackets</a>
                </div>
            </div>
            <div class="col s8 m8 l8 card card-style">
                <div class="card-content">
                    <div class="card-title">
                        super tournament - mario bross
                    </div>
                    <p>
                        #position 5
                    </p>
                </div>
                <div class="card-action">
                    <a href="#">Go Standings</a>
                    <a href="#">Go brackets</a>
                </div>
            </div>
        </div>
    </div>



</main>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav>
    <div class="nav-wrapper">
        <c:url value="/search" var="getPath"/>
        <form class="left" action="${getPath}" method="get">

            <div class="input-field" style="color: #333333">
                <i id="search-icon" class="material-icons">search</i>
                <spring:message code="index.search.placeholder" var="indexSearchPlaceholder"/>
                <input id="search" cssClass="typeahead" type="search" placeholder="${indexSearchPlaceholder}" name="query" autocomplete="off"/>
                <input type="submit" style="visibility: hidden"/>
            </div>
        </form>
        <a href="<c:url value="/"/>" class="brand-logo center"><spring:message code="header.name"/></a>
        <security:authorize access="isAnonymous()">
            <ul id="nav-mobile" class="right hide-on-med-and-down">
                <li><a href="<c:url value="/login"/>"><spring:message code="navbar.login"/></a></li>
                <li><a href="<c:url value="/register"/>"><spring:message code="navbar.signup"/></a></li>
            </ul>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            <ul id="nav-mobile" class="right">
                <li class="nav-user"><security:authentication property="principal.username" /></li>
                <li><a href="<c:url value="/logout"/>"><spring:message code="navbar.logout"/></a></li>
            </ul>
        </security:authorize>
    </div>
</nav>
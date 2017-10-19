<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<nav>
    <div class="nav-wrapper">
        <a href="<c:url value="/"/>" class="brand-logo center"><spring:message code="header.name"/></a>
        <security:authorize access="isAnonymous()">
            <ul id="nav-mobile" class="right hide-on-med-and-down">
                <li><a href="<c:url value="/login"/>"><spring:message code="navbar.signin"/></a></li>
                <li><a href="<c:url value="/register"/>"><spring:message code="navbar.signup"/></a></li>
            </ul>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            <ul id="nav-mobile" class="right">
                <li>Hola!</li>
            </ul>
        </security:authorize>
    </div>
</nav>
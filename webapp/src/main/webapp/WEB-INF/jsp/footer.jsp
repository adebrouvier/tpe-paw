<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<footer class="page-footer light-blue darken-2">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text"><spring:message code="footer.title"/></h5>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            <spring:message code="footer.info"/>
        </div>
    </div>
</footer>
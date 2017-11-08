<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="col s4 m4 l4">
    <div class="card">
        <c:if test="${loggedUser.id == user.id}">
            <a class="btn right light-blue darken-4" style="padding: 0 10px" href="/user/${user.id}/settings"><i class="small material-icons">edit</i></a>
        </c:if>
        <div class="section">
            <div style="padding-top: 40px">
                <img class="user-img user-section-center" src="/profile-image/<c:out value='${user.id}'/>"/>
                <span class="user-section-center">
                    <h5><c:out value="${user.name}"/></h5>
                </span>
                <button class="btn user-section-center light-blue darken-4"> follow</button>
            </div>
        </div>
        <div class="divider"></div>
        <div class="section ">
            <h6 class="user-section"><i class="tiny material-icons section-icons">info_outline</i><b>Perdonal information </b></h6>
            <p class="user-section-content">bla bla bla bla</p>
        </div>
        <div class="divider"></div>
        <div class="section ">
            <h6 class="user-section"><i class="tiny material-icons section-icons">games</i><b>Game Interestings </b></h6>
            <p class="user-section-content">bla bla bla bla</p>
        </div>
    </div>
</div>

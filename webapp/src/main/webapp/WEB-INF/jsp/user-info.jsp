<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col s4 m4 l4">
    <div class="card">
        <c:if test="${loggedUser.id == user.id}">
            <a class="btn right light-blue darken-4" style="padding: 0 10px" href="<c:url value="/user/${user.id}/settings"/>"><i class="small material-icons">edit</i></a>
        </c:if>
        <div class="section">
            <div style="padding-top: 40px">
                <div class="user-img-container user-section-center">
                    <img class="user-img user-section-center" src="<c:url value="/profile-image/${user.id}"/>"/>
                </div>
                <div class="user-section-center">
                    <h5><c:out value="${user.name}"/></h5>
                </div>
                <c:if test="${loggedUser != null && loggedUser.id != user.id}">
                    <c:if test="${isFollow}">
                        <form action="<c:url value='/user/${user.id}/unfollow'/>" method="post">
                            <button type="submit" class="user-section-center btn btn-primary light-blue darken-4"><spring:message code="user.unfollow"/></button>
                        </form>
                    </c:if>
                    <c:if test="${!isFollow}">
                        <form action="<c:url value='/user/${user.id}/follow'/>" method="post">
                            <button type="submit" class="user-section-center btn btn-primary light-blue darken-4"><spring:message code="user.follow"/></button>
                        </form>
                    </c:if>
                </c:if>
            </div>
        </div>
        <c:if test="${user.description != null || user.twitchUrl != null || user.twitterUrl != null || user.youtubeUrl != null}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><i class="tiny material-icons section-icons">info_outline</i><b><spring:message code="user.personalInfomation"/></b></h6>
                <c:if test="${user.description != null}">
                    <p class="user-section-content"><c:out value="${user.description}"/></p></br>
                </c:if>
                <c:if test="${user.twitchUrl != null}">
                    <a href="<c:url value="${user.twitchUrl}"/>" >
                        <div class="row valign-wrapper user-section-content">
                            <div class="col s1" style="padding: 0">
                                <img src="<c:url value="/resources/img/twitch-icon.png"/>" class="user-social-icon">
                            </div>
                            <div class="col s11" style="padding: 0">
                                <span>
                                    Twitch
                                </span>
                            </div>
                        </div>
                    </a>
                </c:if>
                <c:if test="${user.twitterUrl != null}">
                    <a href="<c:url value="${user.twitterUrl}"/>" >
                        <div class="row valign-wrapper user-section-content">
                            <div class="col s1" style="padding: 0">
                                <img src="<c:url value="/resources/img/twitter-icon.png"/>" class="user-social-icon">
                            </div>
                            <div class="col s11" style="padding: 0">
                                <span>
                                    Twitter
                                </span>
                            </div>
                        </div>
                    </a>
                </c:if>
                <c:if test="${user.youtubeUrl != null}">
                    <a href="<c:url value="${user.youtubeUrl}"/>" >
                        <div class="row valign-wrapper user-section-content">
                            <div class="col s1" style="padding: 0">
                                <img src="<c:url value="/resources/img/youtube-icon.png"/>" class="user-social-youtube-icon">
                            </div>
                            <div class="col s11" style="padding: 0">
                                <span>
                                    Youtube
                                </span>
                            </div>
                        </div>
                    </a>
                </c:if>
            </div>
        </c:if>
        <c:if test="${user.description == null && loggedUser.id == user.id}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><b><spring:message code="user.noDescription"/></b></h6>
            </div>
        </c:if>
        <c:if test="${favoriteGame != null && favoriteGames.size() != 0}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><i class="tiny material-icons section-icons">games</i><b><spring:message code="user.favoriteGame"/></b></h6>
                <c:forEach var="games" items="${favoritesGames}">
                    <p class="user-section-content"><c:out value="${games.game.name}"/></p>
                </c:forEach>
            </div>
        </c:if>

    </div>
</div>

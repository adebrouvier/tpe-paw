<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col s4 m4 l4">
    <div class="card">
        <c:if test="${loggedUser.id == user.id}">
            <a class="btn right light-blue darken-4" style="padding: 0 10px" href="/user/${user.id}/settings"><i class="small material-icons">edit</i></a>
        </c:if>
        <div class="section">
            <div style="padding-top: 40px">
                <div class="user-img-container user-section-center">
                    <img class="user-img user-section-center" src="/profile-image/<c:out value='${user.id}'/>"/>
                </div>
                <span class="user-section-center">
                    <h5><c:out value="${user.name}"/></h5>
                </span>
                <c:if test="${loggedUser != null && loggedUser.id != user.id}">
                    <c:if test="${isFollow}">
                        <a class="btn user-section-center light-blue darken-4" href="/user/<c:out value='${user.id}'/>/unfollow" >unfollow</a>
                    </c:if>
                    <c:if test="${!isFollow}">
                        <a class="btn user-section-center light-blue darken-4" href="/user/<c:out value='${user.id}'/>/follow" >follow</a>
                    </c:if>
                </c:if>
            </div>
        </div>
        <c:if test="${user.description != null || user.twitchUrl != null || user.twitterUrl != null || user.youtubeUrl != null}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><i class="tiny material-icons section-icons">info_outline</i><b>Personal information</b></h6>
                <c:if test="${user.description != null}">
                    <p class="user-section-content"><c:out value="${user.description}"/></p></br>
                </c:if>
                <c:if test="${user.twitchUrl != null}">
                    <a class="user-section-content" href="<c:url value="${user.twitchUrl}"/>" >Twitch</a></br>
                </c:if>
                <c:if test="${user.twitterUrl != null}">
                    <a class="user-section-content" href="<c:url value="${user.twitterUrl}"/>">Twitter</a></br>
                </c:if>
                <c:if test="${user.youtubeUrl != null}">
                    <a class="user-section-content" href="<c:url value="${user.youtubeUrl}"/>">Youtube</a></br>
                </c:if>
            </div>
        </c:if>
        <c:if test="${user.description == null && loggedUser.id == user.id}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><b>mensaje de editar perfil</b></h6>
            </div>
        </c:if>
        <c:if test="${favoriteGames.size() != 0}">
            <div class="divider"></div>
            <div class="section ">
                <h6 class="user-section"><i class="tiny material-icons section-icons">games</i><b>Favorite Game</b></h6>
                <c:forEach var="games" items="${favoritesGames}">
                    <p class="user-section-content"><c:out value="${games.game.name}"/></p>
                </c:forEach>
            </div>
        </c:if>

    </div>
</div>

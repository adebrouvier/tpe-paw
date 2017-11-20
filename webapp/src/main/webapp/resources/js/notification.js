
page = 1;
contentLoadTriggered = false;

$(document).ready(function () {

    $(window).scroll( function () {
        scrollPage();
    });
});

function scrollPage () {

    if($(window).scrollTop() + window.innerHeight == $(document).height() && contentLoadTriggered == false) {
        contentLoadTriggered = true;
        $.ajax({
            method: "GET",
            url: contextPath + "/user/notifications/"+page
        }).done(function( msg ) {
            page = page + 1;
            $('#content-wrapper').append(msg);
            contentLoadTriggered = false;
        });
    }
}


page = 1;
contentLoadTriggered = false;

$(document).ready(function () {
    userId = $('#main-container').attr('data-userid');
    $(window).scroll(function () {
        scrollPage();
    });

    $('form').submit(function () {
        $('button[type=submit]').prop('disabled', true);
    });
});

function scrollPage() {

    if ($(window).scrollTop() + window.innerHeight == $(document).height() && contentLoadTriggered == false) {
        contentLoadTriggered = true;
        $.ajax({
            method: "GET",
            url: contextPath + "/user/" + userId + "/" + page
        }).done(function (msg) {
            page = page + 1;
            $('#content-wrapper').append(msg);
            contentLoadTriggered = false;
        });
    }
}
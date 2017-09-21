$(document).ready(function() {
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    })
});

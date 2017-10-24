$(document).ready(function(){

    // Initialize random seed checkbox
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });

    //Initialize modal
    $('.modal').modal();

});
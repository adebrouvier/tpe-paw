$(document).ready(function(){

    // Initialize random seed checkbox
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });

    //Initialize modal
    $('.modal').modal();

    var list = document.getElementById('autocomplete-games');
    var games = JSON.parse(list.dataset.games);
    $('input.autocomplete').autocomplete({

        data: games,
        limit: 3,
        minLength: 1
    });

});
$(document).ready(function() {
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });
    var list = document.getElementById('autocomplete-games');
    var games = JSON.parse(list.dataset.games);
    $('input.autocomplete').autocomplete({

        data: games,
        limit: 3,
        minLength: 1
    });

    var tournamentList = document.getElementById('autocomplete-search');
    var tournaments = JSON.parse(tournamentList.dataset.search);
    $('.autocomplete-search-input').autocomplete({
        data: tournaments,
        limit: 3,
        minLength: 1
    });
});
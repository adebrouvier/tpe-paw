$(document).ready(function() {
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });

    var tournamentList = document.getElementById('autocomplete-search');
    var tournaments = JSON.parse(tournamentList.dataset.search);
    $('input.autocomplete').autocomplete({

        data: tournaments,
        limit: 3,
        minLength: 1
    });
});
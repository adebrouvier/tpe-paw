$(document).ready(function() {
    var tournamentList = document.getElementById('autocomplete-search');
    var tournaments = JSON.parse(tournamentList.dataset.search);
    $('.autocomplete-search-input').autocomplete({
        data: tournaments,
        limit: 3,
        minLength: 1
    });
});
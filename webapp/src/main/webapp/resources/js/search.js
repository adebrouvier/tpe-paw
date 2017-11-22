$(document).ready(function () {

    var games = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: contextPath + '/games_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('#game-filter').typeahead(null,
        {
            name: 'games',
            source: games,
            limit: 5
        }
    );

});


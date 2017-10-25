$(document).ready(function() {

    var tournaments = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/paw-2017b-2/tournament_autocomplete?query=%QUERY&game=%GAME',
            replace: function(url, query) {
                var gameId = $("#ranking-game").data("game");
                url = url.replace("%QUERY", query);
                return url.replace('%GAME', gameId);
            }
        }

    });

    $('.input-field .typeahead').typeahead(null,
        {
            name: 'tournaments',
            source: tournaments,
            limit: 5
        }
    )

    $(".twitter-typeahead")
        .css("float", "none")
        .css("width", "100%");
});
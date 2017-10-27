$(document).ready(function(){

    var games = new Bloodhound({
        datumTokenizer: function(results){
            return Bloodhound.tokenizers.obj.whitespace(results.title)
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'https://player.me/api/v1/search?q=%QUERY&types=games&sort=popular&order=desc&_limit=5',
            prepare: function(query, settings) {
                settings.dataType = "jsonp";
                settings.url = settings.url.replace('%QUERY', query);
                return settings;
            },
            filter: function (response) {
                return response.results;
            }
        }
    });

    var sports = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: contextPath + '/games_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('#game-search').typeahead(null, {
        name: 'sports',
        source: sports,
        templates: {
            header: '<h6 style="font-weight: bold;">Sports</h6>'
        }
    }, {
        name: 'games',
        display: 'title',
        source: games,
        templates: {
            header: '<h6 <h6 style="font-weight: bold;">Games</h6>'
        },
        limit: Infinity
    });

});
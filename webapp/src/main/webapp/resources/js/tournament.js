$(document).ready(function(){

    // Initialize random seed checkbox
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });

    //Initialize modal
    $('.modal').modal();



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

    $('.input-field .typeahead').typeahead(null, {
        name: 'games',
        display: 'title',
        source: games,
        templates: {
            empty: [
                '<div class="empty-message"> no results found </div>'
            ]
        },
        limit: Infinity
    });

});
$(document).ready(function(){

    // Initialize random seed checkbox
    $('input[type=checkbox]').each(function() {
        $(this).after($("label[for='seed-checkbox']"));
    });

    //Initialize modal
    $('.modal').modal();

    var games = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/games_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('.input-field .typeahead').typeahead(null, {
        name: 'games',
        source: games
    });

});
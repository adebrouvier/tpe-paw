$(document).ready(function() {

    var tournaments = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/search_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('.input-field .typeahead').typeahead(null, {
        name: 'tournaments',
        source: tournaments
    }).on('typeahead:selected', function(e, data) {
        $("#searchForm").submit();
    });
});
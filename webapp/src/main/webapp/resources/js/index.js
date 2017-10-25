$(document).ready(function() {

    var tournaments = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/paw-2017b-2/tournament_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    var rankings = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/paw-2017b-2/ranking_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('.input-field .typeahead').typeahead(null,
        {
            name: 'tournaments',
            source: tournaments,
            limit: 5,
            templates: {
                header: '<h6 class="category-name"><b>Tournament</b></h6>'
            }
        },
        {
            name: 'rankings',
            source: rankings,
            limit: 5,
            templates: {
                header: '<h6 class="category-name"><b>Ranking</b></h6>'
            }
        }
    ).on('typeahead:selected', function(e, data) {
        $("#searchForm").submit();
    });
});
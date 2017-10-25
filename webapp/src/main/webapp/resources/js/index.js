$(document).ready(function() {

    var tournaments = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: contextPath +'/tournament_autocomplete?query=%QUERY',
            wildcard: '%QUERY'
        }
    });

    var rankings = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: contextPath +'/ranking_autocomplete?query=%QUERY',
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
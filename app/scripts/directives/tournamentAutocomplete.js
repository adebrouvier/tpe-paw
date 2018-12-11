'use strict';
define(['tpePaw'], function(tpePaw) {

  tpePaw.directive('tournamentAutocomplete', function($filter) {
    return {
      restrict: 'A',
      link: function(scope, elem, attrs) {

        var apiUrl = 'http://localhost:8080';

        var tournaments = new Bloodhound({
          datumTokenizer: Bloodhound.tokenizers.obj.whitespace,
          queryTokenizer: Bloodhound.tokenizers.whitespace,
          remote: {
            url: apiUrl + '/tournaments/autocomplete?q=%QUERY&game=%GAME',
            prepare: function (query, settings) {
              settings.url = settings.url.replace("%QUERY", query);
              settings.url = settings.url.replace("%GAME", attrs.game);
              return settings;
            },
            transform: function(response) {
              return response.results;
            }
          }
        });

        $(elem).typeahead(null, {
          name: 'tournaments',
          source: tournaments,
          templates: {
            header: '<h6 class="category-name">' + $filter('translate')('TOURNAMENT_AUTOCOMPLETE') + '</h6>'
          }
        });
      }
    };
  });
});

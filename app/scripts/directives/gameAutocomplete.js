'use strict';
define(['tpePaw'], function(tpePaw) {

	tpePaw.directive('gameAutocomplete', function($filter) {
		return {
			restrict: 'A',
			link: function(scope, elem, attrs) {

                var games = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('title'),
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    remote: {
                        url: 'https://player.me/api/v1/search?q=%QUERY&types=games&sort=popular&order=desc&_limit=5',
                        prepare: function (query, settings) {
                            settings.dataType = 'jsonp';
                            settings.url = settings.url.replace('%QUERY', query);
                            return settings;
                        },
                        filter: function (response) {
                            return response.results;
                        }
                    }
                });

                $(elem).typeahead(null, {
                    name: 'games',
                    display: 'title',
                    source: games,
                    templates: {
                        header: '<h6 class="category-name">' + $filter('translate')('SEARCH_BAR_GAME') + '</h6>'
                    }
                });
            }
		};
	});
});

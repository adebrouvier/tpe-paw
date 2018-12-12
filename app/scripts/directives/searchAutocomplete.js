'use strict';
define(['tpePaw'], function(tpePaw) {

	tpePaw.directive('searchAutocomplete', function($filter, apiUrl) {
		return {
			restrict: 'A',
			link: function(scope, elem, attrs){                

                var tournaments = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    remote: {
                        url: apiUrl + '/search/tournaments?q=%QUERY',
                        wildcard: '%QUERY'
                    }
                });

                var rankings = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    remote: {
                        url: apiUrl + '/search/rankings?q=%QUERY',
                        wildcard: '%QUERY'
                    }
                });

                $(elem).typeahead(null, 
                    {
                        name: 'tournaments',
                        display: 'name',
                        limit: 3,
                        source: tournaments,
                        templates: {
                            header: '<h6 class="category-name">' + $filter('translate')('SEARCH_TOURNAMENTS') + '</h6>'
                        }
                    },
                    {
                        name: 'rankings',
                        display: 'name',                        
                        limit: 3,
                        source: rankings,
                        templates: {
                            header: '<h6 class="category-name">' + $filter('translate')('SEARCH_RANKINGS') + '</h6>'
                        }
                    }            
                );
            }
		};
	});
});

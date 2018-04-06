'use strict';
define(['tpePaw', 'services/titleService', 'directives/searchAutocomplete'], function(tpePaw) {

	tpePaw.controller('SearchCtrl', function($scope, $filter, $http, $location, titleService) {
		titleService.setTitle($filter('translate')('SEARCH_TITLE') + ' - Versus');

		$scope.searchForm = {};
		$scope.tournaments = null;
		$scope.rankings = null;

		var searchObject = $location.search();
		$scope.searchForm.query = searchObject.q;

		getResults();

		function getResults(){

			if (searchObject.q != null && searchObject.q != ''){
				if (searchObject.type == 'Tournaments' || searchObject.type == null){
					$http.get('http://localhost:8080/search/tournaments', {
						params: {q: $scope.searchForm.query, game: searchObject.game}
					})
						.then(function successCallback(response) {							

							$scope.tournaments = response.data;
							$scope.showTournaments = true;							
							
						}, function errorCallback(response) {
							console.log('No results found');
					});
				}
				if (searchObject.type == 'Rankings'){
					$http.get('http://localhost:8080/search/rankings', {
						params: {q: $scope.searchForm.query, game: searchObject.game}
					})
						.then(function successCallback(response) {							

							$scope.rankings = response.data;							
							$scope.showRankings = true;
							
						}, function errorCallback(response) {
							console.log('No results found');
					});
				}
			}
		};
		
		/* Button search */
		$scope.search = function(){

			$location.search('q', $scope.searchForm.query);
			$location.search('game', $scope.searchForm.game);
			$location.url('/search?q=' + $scope.searchForm.query);
			getResults();
		};
	});
});
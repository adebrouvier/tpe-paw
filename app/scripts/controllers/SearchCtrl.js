'use strict';
define(['tpePaw', 'services/titleService', 'directives/searchAutocomplete', 'directives/gameAutocomplete', 'services/apiService'], function(tpePaw) {

	tpePaw.controller('SearchCtrl', function($scope, $filter, $location, titleService, apiService) {
		titleService.setTitle($filter('translate')('SEARCH_TITLE') + ' - Versus');

		$scope.searchForm = {};
		$scope.tournaments = null;
		$scope.rankings = null;

		var searchObject = $location.search();
		$scope.searchForm.q = searchObject.q;
		$scope.searchForm.game = searchObject.game;

		$scope.search = function() {

			if ($scope.searchForm.q != '' && $scope.searchForm.game != '') {

				$location.search('q', $scope.searchForm.q);
				$location.search('game', $scope.searchForm.game);

				if (searchObject.type == 'Tournaments' || searchObject.type == null) {
					apiService.get('/search/tournaments', {
						q: $scope.searchForm.q,
            game: $scope.searchForm.game
					})
						.then(function successCallback(response) {

							$scope.tournaments = response.data;
							$scope.showTournaments = true;
							
						}, function errorCallback(response) {
							console.log('No tournaments found');
					});
				}
				if (searchObject.type == 'Rankings') {
					apiService.get('/search/rankings', {
            q: $scope.searchForm.q,
            game: $scope.searchForm.game
          })
						.then(function successCallback(response) {

							$scope.rankings = response.data;
							$scope.showRankings = true;
							
						}, function errorCallback(response) {
							console.log('No rankings found');
					});
				}
			}
		};

		$scope.search();
	});
});

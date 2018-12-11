define(['tpePaw', 'services/titleService', 'services/apiService', 'services/authService', 'directives/rankingInfo', 'directives/tournamentAutocomplete', 'directives/validTournament'], function (tpePaw) {

  'use strict';
  tpePaw.controller('RankingTournamentsCtrl', function ($scope, $window, $location, $filter, $routeParams, titleService,
                                                        apiService, AuthService) {

    titleService.setTitle('Versus');

    $scope.rankingId = $routeParams.id;
    $scope.ranking = {};
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    apiService.getRanking($scope.rankingId)
      .then(function successCallback(response) {
        $scope.ranking = response.data;
        titleService.setTitle($scope.ranking.name + ' - Versus');
      }, function errorCallback(response) {
        $location.url('/404');
      });

    $scope.rankingPageForm = {};

    $scope.addTournament = function() {

      if ($scope.rankingPageForm.$valid) {

        apiService.addTournament($scope.rankingId, $scope.rankingPageForm)
          .then(function successCallback(response) {
            $scope.ranking.tournamentPoints = response.data;
          }, function errorCallback(response) {
            console.log('Add tournament error');
          });
      }
    };

    $scope.removeTournament = function(tournamentId) {

        apiService.removeTournament($scope.rankingId, tournamentId)
          .then(function successCallback(response) {
              $scope.ranking.tournamentPoints = response.data;
          }, function errorCallback(response) {
              console.log('Remove tournament error');
          });
    };

  });
});

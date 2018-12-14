define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService'], function (tpePaw) {

  'use strict';
  tpePaw.controller('TournamentMatchDetailsCtrl', function ($scope, $location, $routeParams, $filter, titleService, apiService, AuthService) {

    titleService.setTitle("Versus");

    $scope.tournamentId = $routeParams.id;
    $scope.matchId = $routeParams.matchId;
    $scope.tournament = {};
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

    $scope.matchDataForm = {};

    apiService.getTournament($scope.tournamentId)
      .then(function successCallback(response) {
        $scope.tournament = response.data;

        if ($scope.loggedUser !== $scope.tournament.creator.username) {
          $location.url('/403');
        }

        titleService.setTitle($scope.tournament.name + ' - Versus');
        var match = $filter('filter')($scope.tournament.matches, {'id': $scope.matchId});

        if (match.length === 1) {
          match = match[0];
        } else {
          $location.url('/404');
        }

        if (match.hasOwnProperty('homePlayerCharacter')) {
          $scope.matchDataForm.homePlayerCharacter = match.homePlayerCharacter;
        }

        if (match.hasOwnProperty('awayPlayerCharacter')) {
          $scope.matchDataForm.awayPlayerCharacter = match.awayPlayerCharacter;
        }

        if (match.hasOwnProperty('map')) {
          $scope.matchDataForm.map = match.map;
        }

        if (match.hasOwnProperty('vodLink')) {
          $scope.matchDataForm.vodLink = match.vodLink;
        }

      }, function errorCallback(response) {
        $location.url('/404');
      });

    $scope.processForm = function () {
      apiService.updateMatchData($scope.tournamentId, $scope.matchId, $scope.matchDataForm)
        .then(function successCallback(response) {
          $location.url('/tournament/' + $scope.tournamentId)
        }, function errorCallback(response) {
          console.log('Update match data ERROR');
        });
    };
  });
});
